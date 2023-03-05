package com.api.api_client_sdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.api.api_client_sdk.model.User;
import com.api.api_client_sdk.utils.SignUtils;

import java.util.HashMap;

/**
 * 调用第三方接口的客户端
 */
public class CApiClient {

    String accessKey;
    String secretKey;

    public CApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getNameByGet(String name) {
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);

        String result= HttpUtil.get("http://localhost:8123/api/name/", paramMap);
        System.out.println(result);
        return result;
    }

    public String getNameByPost(String name) {
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);

        String result= HttpUtil.post("http://localhost:8123/api/name/", paramMap);
        System.out.println(result);
        return result;
    }

    private HashMap<String,String> getHeaders(String body){
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("accessKey",accessKey);
        // 不能发送
//        hashMap.put("secretKey","asdasdasd");
        hashMap.put("nonce", RandomUtil.randomNumbers(3));
        hashMap.put("body",body);
        hashMap.put("timestamp",String.valueOf(System.currentTimeMillis() / 1000));
        hashMap.put("sign", SignUtils.genSign(body,secretKey));
        hashMap.put("content-type","application/json; charset=utf-8");
        return hashMap;
    }

    public String getUserNameByPost(User user) {
        String json = JSONUtil.toJsonStr(user);
        HashMap<String, String> headers = getHeaders(json);
        HttpResponse httpResponse = HttpRequest.post("http://localhost:8123/api/name/user")
                .addHeaders(headers)
                .body(json)
                .execute();
        String result2 = httpResponse.body();
        System.out.println(result2);
        return result2;
    }

}
