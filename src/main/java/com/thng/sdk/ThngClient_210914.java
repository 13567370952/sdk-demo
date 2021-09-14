package com.thng.sdk;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * this is a sample client to get api post data.
 * jdk is 1.8
 * @author yang Shen 906318518@qq.com
 * 2021-09-14
 */

public class ThngClient_210914 {
    public static final String appId =  "appId";
    public static final String secret =  "secret";
    public static final String apiUrl = "apiUrl";

    private static int CONNECTION_TIMEOUT = 60000;
    private static int SO_TIMEOUT = 0;

    //调用方法
    public static  String execute(String url, String data) {
        String str_base = base64(data);
        String at = new Date().getTime()+"";
        String token =  md5(secret + at + str_base);
        Map<String, String> header = new HashMap<String, String>();
        header.put("appid", appId);
        header.put("at", at);
        header.put("token", token);
        return postByHttp(apiUrl + url, header,str_base.getBytes(StandardCharsets.UTF_8));
    }

    //base64加密
    public static String base64(String str){
        try {
            return new String(java.util.Base64.getEncoder().encode(str.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    //md5加密
    public static String md5(String str){
        try {
            MessageDigest md5= MessageDigest.getInstance("MD5");
            md5.update(str.getBytes());
            return String.format("%032x",new BigInteger(1,md5.digest()));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    //post请求 目前以http为主
    public static String postByHttp(String url, Map<String, String> header,byte[] bytes) {
        DefaultHttpClient httpclient;
        if(url.startsWith("https")) {
            httpclient = (DefaultHttpClient)getInstance();
        } else {
            httpclient = new DefaultHttpClient();
        }
        return post(url, httpclient, header,bytes);
    }

    private static X509TrustManager tm = new X509TrustManager() {
        public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
        }
        public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
        }
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    };

    //获取HttpClient
    private static HttpClient getInstance() {
        HttpClient client = new DefaultHttpClient();
        SSLContext ctx = null;
        try {
            ctx = SSLContext.getInstance("TLS");
            ctx.init(null, new TrustManager[] { tm }, null);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        SSLSocketFactory ssf = new SSLSocketFactory(ctx);
        ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        ClientConnectionManager ccm = client.getConnectionManager();
        SchemeRegistry sr = ccm.getSchemeRegistry();
        sr.register(new Scheme("https", ssf, 443));
        client = new DefaultHttpClient(ccm, client.getParams());
        return client;
    }

    //post请求
    private static String post(String url, HttpClient httpclient, Map<String, String> headers, byte[] bytes) {

        String response = null;

        try {
            httpclient.getParams().setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, CONNECTION_TIMEOUT);
            httpclient.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, SO_TIMEOUT);

            HttpPost httpPost = new HttpPost(url);
            for(String key : headers.keySet()) {
                httpPost.addHeader(key, headers.get(key));
            }
            httpPost.setEntity(new ByteArrayEntity(bytes));

            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            response = httpclient.execute(httpPost, responseHandler);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
        return response;
    }

    public static void main(String[] args) {
        //请求地址  ps:具体地址以最新文档为准
//        String url = "/data/city_tower_sum_number";
        //请求数据json格式,空时传(注意) data="{}"
//        String data = "{\"uid\": 1}";
//      Examples Use This: ThngClient_210914.execute(url,data);

        String out = "";

        //当前储能情况
        out = ThngClient_210914.execute("/data/current_tower_single_power", "{}");
        System.out.println(out);

        //地市储能节电情况
//        out = ThngClient_210914.execute("/data/city_tower_sum_number", "{}");
//        System.out.println(out);

        //各地市储能情况
//        out = ThngClient_210914.execute("/data/city_tower_single_power", "{\"day\": 1}");
//        out = ThngClient_210914.execute("/data/city_tower_single_power", "{\"day\": 7}");
//        out = ThngClient_210914.execute("/data/city_tower_single_power", "{\"day\": 30}");
//        System.out.println(out);

        //近7天节电情况
//        out = ThngClient_210914.execute("/data/last_week_battery_economize", "{\"uid\": 1,\"day\": 1}");
//        System.out.println(out);

        //Add Other Example For Previous Examples

    }
}
