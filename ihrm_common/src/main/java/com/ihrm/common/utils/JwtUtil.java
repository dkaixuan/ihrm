package com.ihrm.common.utils;

import io.jsonwebtoken.*;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Data
@ConfigurationProperties("jwt.config")
public class JwtUtil {

    //签名私钥
    private String key;

    /**
     * 设置认证token
     *      id:登录用户id
     *      subject：登录用户名
     *
     */
    public String encode(String id,String name,Map<String,Object> param, String salt){
        if(salt!=null){
            key+=salt;
        }
        JwtBuilder jwtBuilder = Jwts.builder().setId(id).setSubject(name)
                .signWith(SignatureAlgorithm.HS256,key);

        for (Map.Entry<String, Object> entry : param.entrySet()) {
            jwtBuilder = jwtBuilder.claim(entry.getKey(),entry.getValue());
        }

//            jwtBuilder = jwtBuilder.setClaims(param);

        String token = jwtBuilder.compact();
        return token;

    }

    public Claims decode(String token,String salt){
        Claims claims=null;
        if (salt!=null){
            key+=salt;
        }
        try {
            claims= Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        } catch ( JwtException e) {
           return null;
        }
        return  claims;
    }
}
