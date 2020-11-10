package com.hhb.demotablib.ok;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @Author: tomcat
 * @Date: 2020/11/10
 * @Desc:
 */
public class OkhttpCall {

    public ServerMethod serverMethod;

    private ExecutorService service = Executors.newCachedThreadPool();

    public OkhttpCall(ServerMethod serverMethod) {
        this.serverMethod = serverMethod;
    }

    /**
     * 请求
     */
    public synchronized void enquene(final CallBack callback) {

        final String url = serverMethod.url;
        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();

                    connection.setRequestMethod("GET");

                    connection.setReadTimeout(5000);

                    connection.setConnectTimeout(5000);

                    connection.setDoInput(true);

                    connection.setDoOutput(true);

                    connection.connect();

                    if (connection.getResponseCode() == 200) {

                        if (callback != null) {
                            callback.onSuccess();
                        }
                    } else {
                        if (callback != null) {
                            callback.onError();
                        }
                    }
                } catch (Exception e) {

                }
            }
        });

    }


}
