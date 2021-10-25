package com.sso.login.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Calendar;
import java.util.Map;
import java.util.Date;

public class JWTUtils {

    private static  final String SEC="LQQ$*QXX&#NKX!!lahaLEIHeiyo";
    //private static  final Date expirationTime;
    /**生成令牌**/

    public static String getToken(Map<String,String> map){

        //设置token1分钟过期
        Calendar instance=Calendar.getInstance();
        instance.add(Calendar.SECOND,60);

        //创建JWTbuilder
        JWTCreator.Builder builder=JWT.create();
        //payload
        map.forEach((k,v)->{
            builder.withClaim(k,v);
        });
        //指定令牌过期时间
        String token=builder.withExpiresAt(instance.getTime()).sign(Algorithm.HMAC256(SEC));

        return token;
    }

    /**验证令牌**/

    /**
    public boolean isTokenExpired (Date expirationTime) {
        return expirationTime.before(new Date());
    }**/

    public static boolean verify(String token) {
        //如果有任何验证异常，此处都会抛出异常
        try{
            JWT.require (Algorithm. HMAC256 ( SEC )).build().verify(token);
            return true;
        }catch (Exception e){
            return  false;
        }

    }

    /**获取token信息*/
    public static Map getTokenInfo(String token){
        return JWT.require(Algorithm.HMAC256(SEC)).build().verify(token).getClaims();

    }
}
