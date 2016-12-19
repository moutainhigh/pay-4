package com.pay.wechat.util;

import java.util.UUID;
import java.util.Map;
import java.util.HashMap;
import java.util.Formatter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.UnsupportedEncodingException;  

import com.pay.wechat.model.AccessToken;
import com.pay.wechat.model.JsapiTicket;

class JsSDKsignUtil {
	
	//注入
	
	//签名校验
    public static Map<String, String> sign(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                  "&noncestr=" + nonce_str +
                  "&timestamp=" + timestamp +
                  "&url=" + url;
        System.out.println(string1);

        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);

        return ret;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
    
    private static String validJsapiTicket(){
    	String jsapiTicket = null ;

    	//数据库取出数据
    	
    	
    	return jsapiTicket ;
    }
    
    //main方法测试
    public static void main(String[] args) {
    	String appid = "wx5c2672970ee835e8";
		String appsecret = "8e0fe9e93eb73de40f1c8a4886da70cd";
		
        String jsapi_ticket = "";
        
        // 注意 URL 一定要动态获取，不能 hardcode
        String url = "http://example.com";
        
        AccessToken accessToken = WeiXinUtil.getAccessToken(appid, appsecret) ;
        JsapiTicket jsapiTicket = WeiXinUtil.get_jsapi_ticket(url, accessToken.getToken()) ; 
        jsapi_ticket = jsapiTicket.getTicket() ;
        
       
        Map<String, String> ret = sign(jsapi_ticket, url);
        for (Map.Entry entry : ret.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }
    };
}
