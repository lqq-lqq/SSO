package com.sso.login.com.sso.login.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

public class JWTUtils {

    private static  final String SEC="LQQ$*QXX&#NKX!!lahaLEIHeiyo";

    /**生成令牌**/

    public static String getToken(Map<String,String> map){

        //设置token5分钟过期
        Calendar instance=Calendar.getInstance();
        instance.add(Calendar.MINUTE,5);

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
    public static void verify(String token){
        JWT.require(Algorithm.HMAC256(SEC)).build().verify(token);
        //如果没有抛出异常则token验证通过
    }

    /**获取token信息*/
    public static DecodedJWT getTokenInfo(String token){
        return JWT.require(Algorithm.HMAC256(SEC)).build().verify(token);

    }
}
