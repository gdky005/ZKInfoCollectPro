package cc.zkteam.zkinfocollectpro.camera;

import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;

import java.io.IOException;
import java.util.List;

import cc.zkteam.zkinfocollectpro.utils.L;

/**
 * User:lizhangqu(513163535@qq.com)
 * Date:2015-09-05
 * Time: 10:56
 */
public class CameraManager {
    private static final String TAG = CameraManager.class.getName();
    private Camera camera;
    private Camera.Parameters parameters;
    private AutoFocusManager autoFocusManager;
    private int requestedCameraId = -1;

    private boolean initialized;
    private boolean previewing;

    /**
     * 打开摄像头
     *
     * @param cameraId 摄像头id
     * @return Camera
     */
    public Camera open(int cameraId) {
        int numCameras = Camera.getNumberOfCameras();
        if (numCameras == 0) {
            Log.e(TAG, "No cameras!");
            return null;
        }
        boolean explicitRequest = cameraId >= 0;
        if (!explicitRequest) {
            // Select a camera if no explicit camera requested
            int index = 0;
            while (index < numCameras) {
                Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
                Camera.getCameraInfo(index, cameraInfo);
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                    break;
                }
                index++;
            }
            cameraId = index;
        }
        Camera camera;
        if (cameraId < numCameras) {
            Log.e(TAG, "Opening camera #" + cameraId);
            camera = Camera.open(cameraId);
        } else {
            if (explicitRequest) {
                Log.e(TAG, "Requested camera does not exist: " + cameraId);
                camera = null;
            } else {
                Log.e(TAG, "No camera facing back; returning camera #0");
                camera = Camera.open(0);
            }
        }
        return camera;
    }

    /**
     * 打开camera
     *
     * @param holder SurfaceHolder
     * @throws IOException IOException
     */
    public synchronized void openDriver(SurfaceHolder holder)
            throws IOException {
        Log.e(TAG, "openDriver");
        Camera theCamera = camera;
        if (theCamera == null) {
            theCamera = open(requestedCameraId);
            if (theCamera == null) {
                throw new IOException();
            }
            camera = theCamera;
        }
        theCamera.setPreviewDisplay(holder);
        camera.setDisplayOrientation(90);
        if (!initialized) {
            initialized = true;
            parameters = camera.getParameters();
            parameters.setPictureFormat(ImageFormat.JPEG);
            parameters.setJpegQuality(100);

            setPreviewSizes();
            setFixPictureSize();

            theCamera.setParameters(parameters);
        }
    }

    /**
     * 2017/12/23  预览的图片尺寸，默认获取最大的尺寸。防止某些大小顺序是反的。
     */
    private void setPreviewSizes() {
        List<Camera.Size> previewSizes = parameters.getSupportedPreviewSizes();

        Camera.Size fixPreviewSize = null;
        if (previewSizes != null) {
            Camera.Size currentPreviewSize = previewSizes.get(0);
            if (currentPreviewSize.width > 0 && currentPreviewSize.height >0) {
                fixPreviewSize = currentPreviewSize;
            } else {
                fixPreviewSize = previewSizes.get(previewSizes.size() - 1);
            }

            for (Camera.Size previewSize : previewSizes) {
                Log.d(TAG, "系统默认的_previewSize: " + previewSize.width + ", " + previewSize.height);
            }
        }

        if (fixPreviewSize != null) {
            int width = fixPreviewSize.width;
            int height = fixPreviewSize.height;
            L.i("最合适 预览图片尺寸是：" + width + "x" + height);
            parameters.setPreviewSize(width, height);
        }
    }

    /**
     * 2017/12/23  图片的大小 宽度在1000以内最合适，图片过大可能造成时间长或者限制导致的失败。过小会造成身份证识别错误。
     */
    private void setFixPictureSize() {
        List<Camera.Size> pictureSizes = parameters.getSupportedPictureSizes();

        Camera.Size fixPictureSize = null;
        for (Camera.Size pictureSize : pictureSizes) {
            Log.d(TAG, "系统默认的_pictureSize: " + pictureSize.width + ", " + pictureSize.height);

            if (pictureSize.width < 1000) {

                if (fixPictureSize == null) {
                    fixPictureSize = pictureSize;
                }

                if (pictureSize.width > fixPictureSize.width) {
                    fixPictureSize = pictureSize;
                }
            }
        }

        if (fixPictureSize != null) {
            int width = fixPictureSize.width;
            int height = fixPictureSize.height;
//            L.i("最合适 生成图片尺寸是：" + width + "x" + height);
            parameters.setPictureSize(fixPictureSize.width, fixPictureSize.height);
        }
    }

    /**
     * camera是否打开
     *
     * @return camera是否打开
     */
    public synchronized boolean isOpen() {
        return camera != null;
    }

    /**
     * 关闭camera
     */
    public synchronized void closeDriver() {
        Log.e(TAG, "closeDriver");
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

    /**
     * 开始预览
     */
    public synchronized void startPreview() {
        Log.e(TAG, "startPreview");
        Camera theCamera = camera;
        try {
            if (theCamera != null && !previewing) {
                theCamera.startPreview();
                previewing = true;
                autoFocusManager = new AutoFocusManager(camera);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭预览
     */
    public synchronized void stopPreview() {
        Log.e(TAG, "stopPreview");
        if (autoFocusManager != null) {
            autoFocusManager.stop();
            autoFocusManager = null;
        }
        if (camera != null && previewing) {
            camera.stopPreview();
            previewing = false;
        }
    }

    /**
     * 打开闪光灯
     */
    public synchronized void openLight() {
        Log.e(TAG, "openLight");
        try {
            if (camera != null) {
                parameters = camera.getParameters();
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(parameters);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭闪光灯
     */
    public synchronized void offLight() {
        Log.e(TAG, "offLight");
        try {
            if (camera != null) {
                parameters = camera.getParameters();
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                camera.setParameters(parameters);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 拍照
     *
     * @param shutter ShutterCallback
     * @param raw     PictureCallback
     * @param jpeg    PictureCallback
     */
    public synchronized void takePicture(final Camera.ShutterCallback shutter, final Camera.PictureCallback raw,
                                         final Camera.PictureCallback jpeg) {
        try {
            camera.takePicture(shutter, raw, jpeg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
