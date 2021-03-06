package cc.zkteam.zkinfocollectpro.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cc.zkteam.zkinfocollectpro.R;
import cc.zkteam.zkinfocollectpro.ZKBase;
import cc.zkteam.zkinfocollectpro.base.BaseActivity;
import cc.zkteam.zkinfocollectpro.bd.ZKBDIDCardManager;
import cc.zkteam.zkinfocollectpro.bean.BDIdCardBean;
import cc.zkteam.zkinfocollectpro.camera.CameraManager;
import cc.zkteam.zkinfocollectpro.camera.PreviewBorderView;
import cc.zkteam.zkinfocollectpro.exception.ZKIdCardException;
import cc.zkteam.zkinfocollectpro.utils.L;
import cc.zkteam.zkinfocollectpro.view.ZKTitleView;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;


public class IDCardScanActivity extends BaseActivity implements SurfaceHolder.Callback {

    public static final String KEY_ID_CARD_INFO_BEAN = "key_id_card_info_bean";
    public static final int REQUEST_IMAGE = 1000;
    /**
     * 图片质量 默认 70%
     */
    public static final int PIC_QUALITY = 70;
    /**
     * 如果图片过大，图片宽度 600， 高度 自适应
     */
    public static final int PIC_WIDTH = 600;

    /**
     * 默认图片生成的名称
     */
    private static final String DEFAULT_NAME = "zk_temp_picture.jpg";


    @BindView(R.id.zk_title_view)
    ZKTitleView zkTitleView;
    @BindView(R.id.surfaceview)
    SurfaceView surfaceview;
    @BindView(R.id.borderview)
    PreviewBorderView borderview;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.waring_text)
    TextView textView2;
    @BindView(R.id.photo_album_iv)
    Button photoAlbumIv;
    @BindView(R.id.take_photo_iv)
    Button takePhotoIv;
    @BindView(R.id.light_iv)
    Button lightIv;
    @BindView(R.id.operate_rl)
    RelativeLayout operateRl;
    @BindView(R.id.progress_layout)
    RelativeLayout progressLayout;

    private CameraManager cameraManager;
    private boolean hasSurface;

    private String filePath;
    private String fileName;
    private boolean toggleLight;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_id_card_scan;
    }

    @Override
    protected void initViews() {
        zkTitleView.setLeftIVSrc(R.drawable.icon_back);
        zkTitleView.setRightIVSrc(R.drawable.ic_more);
        zkTitleView.setCenterTVText(R.string.activity_id_card_title_text);
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData() {
        filePath = ZKBase.getSDCardPath();
        fileName = DEFAULT_NAME;

        L.d("当前即将生成的本地图片路径是：" + filePath + "/" + fileName);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 初始化camera
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
            Thread.sleep(50);

            // 打开Camera硬件设备
            cameraManager.openDriver(surfaceHolder);
            // 创建一个handler来打开预览，并抛出一个运行时异常
            cameraManager.startPreview();
        } catch (Exception ioe) {
            L.e("initCamera: ", ioe);
        }
    }

    public Bitmap adjustPhotoRotation(Bitmap bm, final int orientationDegree) {
        Matrix m = new Matrix();
        m.setRotate(orientationDegree, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);

        try {
            return Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
        } catch (OutOfMemoryError ex) {
        }
        return null;
    }

    @Override
    protected void onPause() {
        // 停止camera，是否资源操作
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
            // 2017/12/16  这里需要注意，获取到图片后，需要先停止 相机的拍摄功能，否则会有报错日志。可根据实际情况调整
            cameraManager.stopPreview();

            // 根据拍照所得的数据创建位图
            final Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0,
                    data.length);
            Bitmap bitmap2 = adjustPhotoRotation(bitmap, 90);
            int height = bitmap2.getHeight();
            int width = bitmap2.getWidth();
            int scanWidth = width * 15 / 16;
            int scanHeight = (int) (scanWidth * 0.63f);

//            if (scanWidth > PIC_WIDTH) {
//                // 对图片尺寸进行处理下 宽度 600， 高度自适应，图片质量 70%
//                scanHeight = scanHeight / scanWidth * 600;
//                scanWidth = PIC_WIDTH;
//            }

            final Bitmap bitmap1 = Bitmap.createBitmap(bitmap2,
                    (width - scanWidth) / 2,
                    (height - scanHeight - PreviewBorderView.heightOffset) / 2,
                    scanWidth, scanHeight);

            File path = new File(filePath);
            FileUtils.createOrExistsFile(path);

            File file = new File(path, fileName);

            FileOutputStream outStream = null;
            try {
                // 打开指定文件对应的输出流
                outStream = new FileOutputStream(file);
                // 把位图输出到指定文件中
                bitmap1.compress(Bitmap.CompressFormat.JPEG,
                        PIC_QUALITY, outStream);
                outStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                uploadAndRecognize(file.getAbsolutePath());
            } catch (ZKIdCardException e) {
                e.printStackTrace();
                ToastUtils.showShort(e.getMessage());
            }
        }
    };

    private void uploadAndRecognize(final String filePath) throws ZKIdCardException {
        progressLayout.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(filePath)) {
            Luban.with(mContext)
                    .load(filePath)                     //传入要压缩的图片
                    .setCompressListener(new OnCompressListener() { //设置回调

                        @Override
                        public void onStart() {
                        }
                        @Override
                        public void onSuccess(File file) {
                            String galleyPicPath = file.getAbsolutePath();
                            L.d("压缩图片后地址是：" + galleyPicPath);

                            try {
                                getIdCardInfo(galleyPicPath);
                            } catch (ZKIdCardException e) {
                                e.printStackTrace();
                                ToastUtils.showShort(e.getMessage());
                            }
                        }
                        @Override
                        public void onError(Throwable e) {
                            //当压缩 出现问题时调用
                            try {
                                getIdCardInfo(filePath);
                            } catch (ZKIdCardException e1) {
                                e1.printStackTrace();
                                ToastUtils.showShort(e.getMessage());
                            }
                        }
                    }).launch();    //启动压缩
        }
    }

    private void getIdCardInfo(String localPicPath) throws ZKIdCardException {
        ZKBDIDCardManager.getInstance().getIdCardInfo(localPicPath, new Callback<BDIdCardBean>() {
            @Override
            public void onResponse(Call<BDIdCardBean> call, Response<BDIdCardBean> response) {
                L.d("onResponse() called with: call = [" + call + "], response = [" + response + "]");
                BDIdCardBean bdIdCardBean = response.body();
                if (bdIdCardBean != null) {
                    BDIdCardBean.WordsResultBean wordsResultBean = bdIdCardBean.getWords_result();

                    if (wordsResultBean != null) {
                        // 2017/12/15  在这里处理数据格式，并返回给主界面
                        progressLayout.setVisibility(View.GONE);
                        finishUi(wordsResultBean);
                        return;
                    }
                }
                scanFailWarning();
            }

            @Override
            public void onFailure(Call<BDIdCardBean> call, Throwable t) {
                L.e("onFailure() called with: call = [" + call + "], t = [" + t + "]");
                scanFailWarning();
            }
        });
    }

    private void scanFailWarning() {
        progressLayout.setVisibility(View.GONE);
        cameraManager.startPreview();

        ToastUtils.showShort("识别失败， 请重新扫描");
    }

    private void finishUi(BDIdCardBean.WordsResultBean wordsResultBean) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_ID_CARD_INFO_BEAN, wordsResultBean);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);

        // 2017/12/24  测试完成以后 结束界面
        IDCardScanActivity.this.finish();
    }

    @OnClick({R.id.common_title_iv_left, R.id.common_title_iv_right,
            R.id.take_photo_iv, R.id.light_iv, R.id.photo_album_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_title_iv_left:
                onBackPressed();
                break;
            case R.id.common_title_iv_right:
            case R.id.photo_album_iv:
                MultiImageSelector.create()
                        .showCamera(false)
                        .single() // single mode
                        .start(this, REQUEST_IMAGE);
                break;
            case R.id.take_photo_iv:
                cameraManager.takePicture(null, null, myjpegCallback);
                break;
            case R.id.light_iv:
                if (!toggleLight) {
                    toggleLight = true;
                    cameraManager.openLight();
                } else {
                    toggleLight = false;
                    cameraManager.offLight();
                }
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                // 获取返回的图片列表
                List<String> pics = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);

                if (pics != null && pics.size() > 0) {
                    String galleyPicPath = pics.get(0);
                    L.d("当前图片地址是：" + galleyPicPath);

                    try {
                        uploadAndRecognize(galleyPicPath);
                    } catch (ZKIdCardException e) {
                        e.printStackTrace();
                        ToastUtils.showShort(e.getMessage());
                    }
                }
            }
        }
    }

}
