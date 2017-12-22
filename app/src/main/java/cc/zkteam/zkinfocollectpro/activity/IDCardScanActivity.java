package cc.zkteam.zkinfocollectpro.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.OnClick;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.base.BaseActivity;
import cc.zkteam.zkinfocollectpro.bean.BDIdCardBean;
import cc.zkteam.zkinfocollectpro.bean.BDIdCardRequestBody;
import cc.zkteam.zkinfocollectpro.camera.CameraManager;
import cc.zkteam.zkinfocollectpro.camera.PreviewBorderView;
import cc.zkteam.zkinfocollectpro.managers.ZHConnectionManager;
import cc.zkteam.zkinfocollectpro.utils.baidu.Base64Util;
import cc.zkteam.zkinfocollectpro.utils.baidu.FileUtil;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class IDCardScanActivity extends BaseActivity implements SurfaceHolder.Callback {
    @BindView(R.id.title_bar_left_iv)
    ImageView titleBarLeftIv;
    @BindView(R.id.title_bar_text_tv)
    TextView titleBarTextTv;
    @BindView(R.id.title_bar_right_iv)
    ImageView titleBarRightIv;
    @BindView(R.id.toolbar_personal_info_collection)
    Toolbar toolbarPersonalInfoCollection;
    @BindView(R.id.surfaceview)
    SurfaceView surfaceview;
    @BindView(R.id.borderview)
    PreviewBorderView borderview;
    @BindView(R.id.take)
    Button take;
    @BindView(R.id.light)
    Button light;
    @BindView(R.id.linearlaout)
    LinearLayout linearlaout;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.re_scan)
    Button reScan;
    @BindView(R.id.view_scan_result)
    RelativeLayout viewScanResult;

    private CameraManager cameraManager;
    private boolean hasSurface;
    private Intent mIntent;
    private static final String DEFAULT_PATH = "/sdcard/";
    private static final String DEFAULT_NAME = "default.jpg";
    private static final String DEFAULT_TYPE = "default";
    private String filePath;
    private String fileName;
    private String type;
    private boolean toggleLight;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_id_card_scan;
    }

    @Override
    protected void initViews() {
        initLayoutParams();
    }

    @Override
    protected void initListener() {
        reScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getBaseContext(), IDCardScanActivity.class));
            }
        });

        take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraManager.takePicture(null, null, myjpegCallback);
            }
        });

        light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!toggleLight) {
                    toggleLight = true;
                    cameraManager.openLight();
                } else {
                    toggleLight = false;
                    cameraManager.offLight();
                }
            }
        });
    }

    @Override
    protected void initData() {
        mIntent = getIntent();
        filePath = mIntent.getStringExtra("path");
        fileName = mIntent.getStringExtra("name");
        type = mIntent.getStringExtra("type");
        if (filePath == null) {
            filePath = DEFAULT_PATH;
        }
        if (fileName == null) {
            fileName = DEFAULT_NAME;
        }
        if (type == null) {
            type = DEFAULT_TYPE;
        }
        Log.e("TAG", filePath + "/" + fileName + "_" + type);
    }

    /**
     * 重置surface宽高比例为3:4，不重置的话图形会拉伸变形
     */
    private void initLayoutParams() {


        //重置宽高，3:4
        int widthPixels = getScreenWidth(this);
        int heightPixels = getScreenHeight(this);

//        FrameLayout.LayoutParams surfaceviewParams = (FrameLayout.LayoutParams) surfaceview.getLayoutParams();
//        surfaceviewParams.width = heightPixels * 4 / 3;
//        surfaceviewParams.height = heightPixels;
//        surfaceview.setLayoutParams(surfaceviewParams);
//
//        FrameLayout.LayoutParams borderViewParams = (FrameLayout.LayoutParams) mPreviewBorderView.getLayoutParams();
//        borderViewParams.width = heightPixels * 4 / 3;
//        borderViewParams.height = heightPixels;
//        mPreviewBorderView.setLayoutParams(borderViewParams);
//
//        FrameLayout.LayoutParams linearLayoutParams = (FrameLayout.LayoutParams) mLinearLayout.getLayoutParams();
//        linearLayoutParams.width = widthPixels - heightPixels * 4 / 3;
//        linearLayoutParams.height = heightPixels;
//        mLinearLayout.setLayoutParams(linearLayoutParams);


        Log.e("TAG", "Screen width:" + heightPixels * 4 / 3);
        Log.e("TAG", "Screen height:" + heightPixels);

    }


    @Override
    protected void onResume() {
        super.onResume();
        /**
         * 初始化camera
         */
        cameraManager = new CameraManager();
        SurfaceHolder surfaceHolder = surfaceview.getHolder();

        if (hasSurface) {
            // activity在paused时但不会stopped,因此surface仍旧存在；
            // surfaceCreated()不会调用，因此在这里初始化camera
            initCamera(surfaceHolder);
        } else {
            // 重置callback，等待surfaceCreated()来初始化camera
            surfaceHolder.addCallback(this);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        initCamera(holder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    /**
     * 初始camera
     *
     * @param surfaceHolder SurfaceHolder
     */
    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            return;
        }
        try {

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 打开Camera硬件设备
            cameraManager.openDriver(surfaceHolder);
            // 创建一个handler来打开预览，并抛出一个运行时异常
            cameraManager.startPreview();
        } catch (Exception ioe) {
            Log.e(TAG, "initCamera: ", ioe);
        }
    }

    public Bitmap adjustPhotoRotation(Bitmap bm, final int orientationDegree) {
        Matrix m = new Matrix();
        m.setRotate(orientationDegree, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);

        try {
            Bitmap bm1 = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
            return bm1;
        } catch (OutOfMemoryError ex) {
        }
        return null;
    }

    @Override
    protected void onPause() {
        /**
         * 停止camera，是否资源操作
         */
        cameraManager.stopPreview();
        cameraManager.closeDriver();
        if (!hasSurface) {
            SurfaceHolder surfaceHolder = surfaceview.getHolder();
            surfaceHolder.removeCallback(this);
        }
        super.onPause();
    }


    /**
     * 拍照回调
     */
    Camera.PictureCallback myjpegCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(final byte[] data, Camera camera) {

            // TODO: 2017/12/16  这里需要注意，获取到图片后，需要先停止 相机的拍摄功能，否则会有报错日志。可根据实际情况调整
            cameraManager.stopPreview();

            // 根据拍照所得的数据创建位图
            final Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0,
                    data.length);
            Bitmap bitmap2 = adjustPhotoRotation(bitmap, 90);
            int height = bitmap2.getHeight();
            int width = bitmap2.getWidth();
            int scanWidth = width * 15 / 16;
            int scanHeight = (int) (scanWidth * 0.63f);
            final Bitmap bitmap1 = Bitmap.createBitmap(bitmap2, (width - scanWidth) / 2, (height - scanHeight) / 2, scanWidth, scanHeight);

            File path = new File(filePath);
            if (!path.exists()) {
                path.mkdirs();
            }
            File file = new File(path, type + "_" + fileName);

            FileOutputStream outStream = null;
            try {
                // 打开指定文件对应的输出流
                outStream = new FileOutputStream(file);
                // 把位图输出到指定文件中
                bitmap1.compress(Bitmap.CompressFormat.JPEG,
                        100, outStream);
                outStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            uploadAndRecognize(file.getAbsolutePath());


//
//            Intent intent = new Intent();
//            Bundle bundle = new Bundle();
//            bundle.putString("path", file.getAbsolutePath());
//            bundle.putString("type", type);
//            intent.putExtras(bundle);
//            setResult(RESULT_OK, intent);
//
//            IDCardScanActivity.this.finish();

        }
    };


    /**
     * 获得屏幕宽度，单位px
     *
     * @param context 上下文
     * @return 屏幕宽度
     */
    public int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }


    /**
     * 获得屏幕高度
     *
     * @param context 上下文
     * @return 屏幕除去通知栏的高度
     */
    public int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels - getStatusBarHeight(context);
    }

    /**
     * 获取通知栏高度
     *
     * @param context 上下文
     * @return 通知栏高度
     */
    public int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object obj = clazz.newInstance();
            Field field = clazz.getField("status_bar_height");
            int temp = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(temp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }

    private void uploadAndRecognize(final String filePath) {
        progress.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(filePath)) {

            getIdCardInfo(filePath);

//            final File file = new File(filePath);
//            //构造上传请求，类似web表单
//            RequestBody requestBody = new MultipartBuilder().type(MultipartBuilder.FORM)
//                    .addPart(Headers.of("Content-Disposition", "form-data; name=\"callbackurl\""), RequestBody.create(null, "/idcard/"))
//                    .addPart(Headers.of("Content-Disposition", "form-data; name=\"action\""), RequestBody.create(null, "idcard"))
//                    .addPart(Headers.of("Content-Disposition", "form-data; name=\"img\"; filename=\"idcardFront_user.jpg\""), RequestBody.create(MediaType.parse("image/jpeg"), file))
//                    .build();
//            //这个是ui线程回调，可直接操作UI
//            //进行包装，使其支持进度回调
//            final Request request = new Request.Builder()
//                    .header("Host", "ocr.ccyunmai.com")
//                    .header("Origin", "http://ocr.ccyunmai.com")
//                    .header("Referer", "http://ocr.ccyunmai.com/idcard/")
//                    .header("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2398.0 Safari/537.36")
//                    .url("http://ocr.ccyunmai.com/UploadImg.action")
//                    .post(requestBody)
//                    .build();
//            //开始请求
//            mOkHttpClient.newCall(request).enqueue(new Callback() {
//                @Override
//                public void onFailure(Request request, IOException e) {
//                    Log.e("TAG", "error");
//                }
//                @Override
//                public void onResponse(Response response) throws IOException {
//                    final String result=response.body().string();
//                    Document parse = Jsoup.parse(result);
//                    Elements select = parse.select("div.left fieldset");
//                    Log.e("TAG",select.text());
//                    Document parse1 = Jsoup.parse(select.text());
//                    final StringBuilder builder=new StringBuilder();
//                    String name=parse1.select("name").text();
//                    String cardno=parse1.select("cardno").text();
//                    String sex=parse1.select("sex").text();
//                    String folk=parse1.select("folk").text();
//                    String birthday=parse1.select("birthday").text();
//                    String address=parse1.select("address").text();
//                    String issue_authority=parse1.select("issue_authority").text();
//                    String valid_period=parse1.select("valid_period").text();
//                    builder.append("name:"+name)
//                            .append("\n")
//                            .append("cardno:" + cardno)
//                            .append("\n")
//                            .append("sex:" + sex)
//                            .append("\n")
//                            .append("folk:" + folk)
//                            .append("\n")
//                            .append("birthday:" + birthday)
//                            .append("\n")
//                            .append("address:" + address)
//                            .append("\n")
//                            .append("issue_authority:" + issue_authority)
//                            .append("\n")
//                            .append("valid_period:" + valid_period)
//                            .append("\n");
//                    Log.e("TAG", "name:" + name);
//                    Log.e("TAG","cardno:"+cardno);
//                    Log.e("TAG","sex:"+sex);
//                    Log.e("TAG","folk:"+folk);
//                    Log.e("TAG","birthday:"+birthday);
//                    Log.e("TAG","address:"+address);
//                    Log.e("TAG","issue_authority:"+issue_authority);
//                    Log.e("TAG","valid_period:"+valid_period);
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            mPreviewBorderView.setVisibility(View.GONE);
//                            viewScanResult.setVisibility(View.VISIBLE);
//                            tv.setText(builder.toString());
//                            img.setImageURI(Uri.fromFile(file));
//                            progress.setVisibility(View.GONE);
//                        }
//                    });
//                }
//            });
        }
    }


    private static final String TAG = "IDCardScanActivity";

    private void getIdCardInfo(String localPicPath) {
//        准备添加 身份证失败：http://ai.baidu.com/docs#/OCR-API/top
//        String localPicPath = Environment.getExternalStorageDirectory().getPath() + "/xiaotieie_id_card.png";

        try {
            byte[] imgData = FileUtil.readFileByBytes(localPicPath);
            String imgStr = Base64Util.encode(imgData);
            String base64EncodeImage = URLEncoder.encode(imgStr, "UTF-8");

            BDIdCardRequestBody requestBody = BDIdCardRequestBody.BDIDBardRequestBodyFactory
                    .getDefaultRequestBody(true, base64EncodeImage);
            RequestBody body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), requestBody.toString());

            ZHConnectionManager.getInstance().getZHApi().bdIDCard(body, MainActivity.accessToken).enqueue(new Callback<BDIdCardBean>() {
                @Override
                public void onResponse(Call<BDIdCardBean> call, Response<BDIdCardBean> response) {
                    Log.d(TAG, "onResponse() called with: call = [" + call + "], response = [" + response + "]");
                    BDIdCardBean bdIdCardBean = response.body();
                    if (bdIdCardBean != null) {
                        BDIdCardBean.WordsResultBean wordsResultBean = bdIdCardBean.getWords_result();

                        if (wordsResultBean != null) {
                            // TODO: 2017/12/15  在这里处理数据格式，并返回给主界面

                            StringBuilder sb = new StringBuilder();
                            sb.append(wordsResultBean.get住址().getWords());
                            sb.append("; ");
                            sb.append(wordsResultBean.get公民身份号码().getWords());
                            sb.append("; ");
                            sb.append(wordsResultBean.get出生().getWords());
                            sb.append("; ");
                            sb.append(wordsResultBean.get姓名().getWords());
                            sb.append("; ");
                            sb.append(wordsResultBean.get性别().getWords());
                            sb.append("; ");
                            sb.append(wordsResultBean.get民族().getWords());
                            sb.append("。");


                            borderview.setVisibility(View.GONE);
                            viewScanResult.setVisibility(View.VISIBLE);
                            tv.setText(sb.toString());
                            img.setImageURI(Uri.fromFile(new File(localPicPath)));
                            progress.setVisibility(View.GONE);

                        }
                    }
                }

                @Override
                public void onFailure(Call<BDIdCardBean> call, Throwable t) {

                    Log.d(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.title_bar_left_iv, R.id.title_bar_right_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_bar_left_iv:
                break;
            case R.id.title_bar_right_iv:
                break;
        }
    }
}
