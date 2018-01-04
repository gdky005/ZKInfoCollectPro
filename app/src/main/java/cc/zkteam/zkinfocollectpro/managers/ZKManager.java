package cc.zkteam.zkinfocollectpro.managers;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * ZKManager
 * Created by wangqing on 2018/1/4.
 */

public class ZKManager {
    private static ZKManager instance = null;

    private ZhiHuiBean zhiHuiBean;

    private ZKManager() {
    }

    public static ZKManager getInstance() {
        if (instance == null) {
            synchronized (ZKManager.class) {
                ZKManager temp = instance;
                if (temp == null) {
                    temp = new ZKManager();
                    instance = temp;
                }
            }
        }
        return instance;
    }

    public void refresh() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://zkteam.wilddogio.com/zhihui.json")
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        ResponseBody responseBody = response.body();

                        if (responseBody != null) {
                            String content = responseBody.string();
                            Gson gson = new Gson();
                            ZhiHuiBean bean = gson.fromJson(content, ZhiHuiBean.class);
                            zhiHuiBean = bean;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isOpenAllState() {
        return zhiHuiBean != null && zhiHuiBean.getState() != 0;
    }

    public boolean isAppState() {
        if (isOpenAllState()) {
            ZhiHuiBean.DataBean dataBean = zhiHuiBean.getData();
            if (dataBean != null) {
                int appState = dataBean.getApp();
                return appState !=0;
            }
        }
        return false;
    }

    public boolean isNew31State() {
        if (isOpenAllState()) {
            ZhiHuiBean.DataBean dataBean = zhiHuiBean.getData();
            if (dataBean != null) {
                int appState = dataBean.getNew31();
                return appState !=0;
            }
        }
        return false;
    }

    /**
     * 如果不为 null 或者 空，说明已经开启了，需要直接显示到界面上
     */
    public String getWarningText() {
        if (isOpenAllState()) {
            ZhiHuiBean.DataBean dataBean = zhiHuiBean.getData();
            if (dataBean != null) {
                ZhiHuiBean.DataBean.WarningBean warningBean = dataBean.getWarning();
                if (warningBean != null) {
                    int warningState = warningBean.getState();

                    if (1 == warningState) {
                        return warningBean.getText();
                    }
                }
            }
        }
        return null;
    }

    /**
     * 如果不为 null 或者 空，说明已经开启了，需要直接显示到界面上
     */
    public String getWatermarkText() {
        if (isOpenAllState()) {
            ZhiHuiBean.DataBean dataBean = zhiHuiBean.getData();
            if (dataBean != null) {
                ZhiHuiBean.DataBean.WatermarkBean watermarkBean = dataBean.getWatermark();
                if (watermarkBean != null) {
                    int watermarkState = watermarkBean.getState();
                    if (1 == watermarkState) {
                        return watermarkBean.getText();
                    }
                }
            }
        }
        return null;
    }

    public static class ZhiHuiBean {

        /**
         * data : {"app":0,"new31":0,"warning":{"state":0,"text":"付费完成可以享受完整功能"},"watermark":{"state":1,"text":"未付费预览版"}}
         * state : 1
         */

        private DataBean data;
        private int state;

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public static class DataBean {
            /**
             * app : 0
             * new31 : 0
             * warning : {"state":0,"text":"付费完成可以享受完整功能"}
             * watermark : {"state":1,"text":"未付费预览版"}
             */

            private int app;
            private int new31;
            private WarningBean warning;
            private WatermarkBean watermark;

            public int getApp() {
                return app;
            }

            public void setApp(int app) {
                this.app = app;
            }

            public int getNew31() {
                return new31;
            }

            public void setNew31(int new31) {
                this.new31 = new31;
            }

            public WarningBean getWarning() {
                return warning;
            }

            public void setWarning(WarningBean warning) {
                this.warning = warning;
            }

            public WatermarkBean getWatermark() {
                return watermark;
            }

            public void setWatermark(WatermarkBean watermark) {
                this.watermark = watermark;
            }

            public static class WarningBean {
                /**
                 * state : 0
                 * text : 付费完成可以享受完整功能
                 */

                private int state;
                private String text;

                public int getState() {
                    return state;
                }

                public void setState(int state) {
                    this.state = state;
                }

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }
            }

            public static class WatermarkBean {
                /**
                 * state : 1
                 * text : 未付费预览版
                 */

                private int state;
                private String text;

                public int getState() {
                    return state;
                }

                public void setState(int state) {
                    this.state = state;
                }

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }
            }
        }
    }


}
