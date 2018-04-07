package com.px.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by patrick on 05/03/2018.
 * create time : 10:25 PM
 */

public class JWTUtil {

    private static final String SECRET = "#hhjkhkh&jh2432@ndsf*_erkhwek234&ewhkjwehr^hfh234$2l3j4" +
            "o32urMiOiJ3aJpc3MiOiJ3aWF0ZWMiLCJzdWIiOiJsZWdhY3kiLCJuYmYiOjE1MjAyNjE5NTgsImJzdWIiOi" +
            "JsZWdhY3kiLCJuYmYiOjE1MjAyNjQ0MjksImV4cCI6MTUyMDM1MD2l3j4o3";

    public static String encode() throws UnsupportedEncodingException {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date newDate = calendar.getTime();
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        String token = JWT.create()
                .withIssuer("wiatec")
                .withSubject("legacy")
                .withNotBefore(date)
                .withExpiresAt(newDate)
                .sign(algorithm);
        return token;
    }

    public static void decode(String token){
        DecodedJWT jwt = JWT.decode(token);
        Date expiresAt = jwt.getExpiresAt();
        System.out.println(expiresAt);
    }

    public static void main (String [] args) throws UnsupportedEncodingException {
        String s = encode();
        System.out.println(s);
        decode(s);
    }

}
