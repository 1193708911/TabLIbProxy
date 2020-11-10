package com.hhb.demotablib.ok;

public interface IPluginAPi {
    public static final String method_login = "243";

    @Method(param = "userName", url = "https://www.baidu.com")
    public OkhttpCall sendLogin(String userName);
}
