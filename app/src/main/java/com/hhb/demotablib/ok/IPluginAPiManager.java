package com.hhb.demotablib.ok;

import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: tomcat
 * @Date: 2020/11/10
 * @Desc:
 */
public class IPluginAPiManager {

    private static final String TAG = IPluginAPiManager.class.getSimpleName();
    public static Map<Method, Object> mCacheServer = new HashMap<>();

    public static <T> T getModule(String method_login, Class<IPluginAPi> iPluginAPiClass) {

        if (TextUtils.equals(method_login, IPluginAPi.method_login)) {


        }

        return (T) Proxy.newProxyInstance(iPluginAPiClass.getClassLoader(), new Class[]{iPluginAPiClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                Log.e(TAG, "invoke: " + method.getName());


                //测试使用
                if (mCacheServer.containsKey(method)) {
                    return mCacheServer.get(method);
                }

                ServerMethod serverMethod = new ServerMethod(method);
                mCacheServer.put(method, serverMethod);


                return serverMethod.generateOkhttpCall();
            }
        });


    }
}
