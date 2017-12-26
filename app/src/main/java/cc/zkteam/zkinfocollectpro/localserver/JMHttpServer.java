package cc.zkteam.zkinfocollectpro.localserver;

import android.content.Context;

import java.io.File;
import java.util.List;

import cc.zkteam.zkinfocollectpro.utils.L;

/**
 * // TODO: 2017/3/7 V2 该文件需要校验！！！
 * <p>
 * 开启使用
 */
public class JMHttpServer {

    public int mPort = 0;
    public File mWWWRoot;
    private boolean mIsRunning = false;

    public JMHTTPDPooled mHttpServer;
    // server
    public JMHTTPDPooled offLineHttpServer;

    private static JMHttpServer instance = null;

    private JMHttpServer() {
    }

    public static JMHttpServer getInstance() {
        if (instance == null) {
            synchronized (JMHttpServer.class) {
                JMHttpServer temp = instance;
                if (temp == null) {
                    temp = new JMHttpServer();
                    instance = temp;
                }
            }
        }
        return instance;
    }

    /**
     * 这里可能需要考虑一个问题，如果连续启动两次，然后销毁连续两次， 注意区分情况，
     * 目前处理方法是，启动两次，就创建两个，互不干扰。
     *
     * @param context  Context
     * @param aPort    aPort
     * @param aWWWRoot aWWWRoot
     */
    public void startServer(Context context, int aPort, File aWWWRoot) {
        if (aWWWRoot == null)
            return;

        mPort = aPort;
        mWWWRoot = aWWWRoot;
        if (!mWWWRoot.exists()) {
            boolean b = mWWWRoot.mkdirs();
            L.i("创建 本地文件的状态是： " + b + ", 路径是：" + mWWWRoot.getAbsolutePath());
        }

        if (mHttpServer == null)
            mHttpServer = new JMHTTPDPooled(context, aWWWRoot);

        boolean startSuccess = mHttpServer.start(mPort);
        for (int i = 0; !startSuccess && i < 10; ++i) {
            startSuccess = mHttpServer.start(0);
            L.d("mHttpServer.start mPort(" + mPort + ") is fail, retry(..." + i + "...), after state:" + startSuccess);
        }

        if (startSuccess) {
            mPort = mHttpServer.getPort();
            mIsRunning = true;
            L.d("JMHttpServer startServer() is success, mPort:> " + mPort + " <, mIsRunning is true");
        } else {
            L.e("JMHttpServer startServer() is fail, not running!");
        }

    }

    public void stopServer() {
        if (mHttpServer != null) {
            mHttpServer.stop();
            L.d("JMHttpServer stopServer finish.");
        } else {
            L.d("mHttpServer is null, not mHttpServer.stop()");
        }
    }

    public int getPort() {
        return mPort;
    }

    public String getLocalHostUrl(Context context) {
        if (mHttpServer == null)
            return null;
        return mHttpServer.getServerName(context);
    }

    public void offlineData(Context context, List<String> whiteLists) {
        if (offLineHttpServer == null) {
            offLineHttpServer = new JMHTTPDPooled(context);
        }
        offLineHttpServer.offlineListData(whiteLists);
    }


    public void offlineData(Context context, String url) {
        if (offLineHttpServer == null) {
            offLineHttpServer = new JMHTTPDPooled(context);
        }
        offLineHttpServer.offlineData(url);
    }

    public File getRootDir() {
        return mWWWRoot;
    }

    public boolean isRunning() {
        return mIsRunning;
    }
}
