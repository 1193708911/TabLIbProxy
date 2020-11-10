package com.hhb.demotablib.ok;

import android.text.TextUtils;

import java.lang.reflect.Method;

/**
 * @Author: tomcat
 * @Date: 2020/11/10
 * @Desc:
 */
public class ServerMethod {


    public String url;

    public String param;


    public ServerMethod(Method method) throws Exception {
        parseAnnotation(method);

    }

    private void parseAnnotation(Method method) throws Exception {

        com.hhb.demotablib.ok.Method methodAnnotation = method.getAnnotation(com.hhb.demotablib.ok.Method.class);
        if (methodAnnotation != null) {
            url = methodAnnotation.url();
            if (TextUtils.isEmpty(url)) {
                throw new Exception("url  不允许为空");
            }
            param = methodAnnotation.param();


        }
    }

    public OkhttpCall generateOkhttpCall() {

        OkhttpCall okhttpCall = new OkhttpCall(this);
        return okhttpCall;
    }
}
