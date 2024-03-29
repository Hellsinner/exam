package com.hellsinner.exam.utils;

import com.hellsinner.exam.model.enums.TokenState;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import net.minidev.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    /**
     * 公共秘钥－保存在服务端，客户端是不知道该秘钥的，防止被攻击。(signature)
     */
    private static final byte[] SECRET = "1234567890qwertyuiopasdfghjklzxcvbnm".getBytes();

    private static final JWSHeader HEADER = new JWSHeader(JWSAlgorithm.HS256,
            JOSEObjectType.JWT, null, null, null, null, null,
            null, null, null, null, null, null);

    /**
     * 生成token，该方法只在用户登录成功后调用
     * @param payload Map集合，可以存储用户id，token生成时间，token过期时间等自定义字段
     * @return token字符串,若失败则返回null
     */
    private static String createToken(Map<String, Object> payload) {
        String tokenString = null;
        // 创建一个JWS Object(第二部分)
        JWSObject jwsObject = new JWSObject(HEADER, new Payload(new JSONObject(payload)));
        try {
            // 将jwsObject进行HMAC签名，相当于加密(第三部分)
            jwsObject.sign(new MACSigner(SECRET));
            tokenString = jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException();
        }
        return tokenString;
    }

    /**
     * 校验token是否合法，返回Map集合,集合中主要包含    state状态码   data鉴权成功后从token中提取的数据
     * 该方法在过滤器中调用，每次请求API时都校验
     * @param token token
     * @return Map<String, Object>
     */
    public static Map<String, Object> validToken(String token) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            if (StringUtils.isEmpty(token)){
                resultMap.put("state", TokenState.EXPIRED);
                return resultMap;
            }
            JWSObject jwsObject = JWSObject.parse(token);
            // palload就是JWT构成的第二部分不过这里自定义的是私有声明(标准中注册的声明, 公共的声明)
            Payload payload = jwsObject.getPayload();
            JWSVerifier verifier = new MACVerifier(SECRET);
            if(jwsObject.verify(verifier)) {
                JSONObject jsonObject = payload.toJSONObject();
                // token检验成功（此时没有检验是否过期）
                resultMap.put("state", TokenState.VALID);
                // 若payload包含ext字段，则校验是否过期
                if(jsonObject.containsKey("ext")) {
                    long extTime = Long.valueOf(jsonObject.get("ext").toString());
                    long curTime = System.currentTimeMillis();
                    // 过期了
                    if(curTime > extTime) {
                        resultMap.clear();
                        resultMap.put("state", TokenState.EXPIRED);
                    }
                }
                resultMap.put("data", jsonObject);
            } else {
                // 检验失败
                resultMap.put("state", TokenState.INVALID);
            }
        } catch (Exception e) {
            // token格式不合法导致的异常
            resultMap.clear();
            resultMap.put("state", TokenState.INVALID);
        }
        return resultMap;
    }

    /**
     * JWT的组成：Header + payload + signature
     * Payload(载荷)的组成信息，私有声明(标准中注册的声明和公共的声明并未使用)
     * @param userId 用户id
     * @return token
     */
    public static String createPayLoad(Integer userId) {
        Map<String, Object> payload = new HashMap<>();
        Date date = new Date();
        // 用户id
        payload.put("id", userId);
        // 生成时间:当前
        payload.put("iat", date.getTime());
        // 过期时间10分钟(单位毫秒)
        payload.put("ext", date.getTime() + 1000*60*60*12*7);
        return JwtUtils.createToken(payload);
    }
}
