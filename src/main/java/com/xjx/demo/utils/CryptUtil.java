package com.xjx.demo.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.SecureRandom;

/**
 * 加密解密工具类(DES)
 */
public class CryptUtil {
    /**
     * 当前秘钥
     */
    private static Key CURRENT_KEY;
    /**
     * 默认秘钥
     */
    private static final String DEFAULT_KEY = "1fsdfnaiuehi12423511e&&*332342*)";
    /**
     * 加密模式
     */
    private static final String ENCODE_MODE = "DES";
    /**
     * 加密解密格式
     */
    private static final String CODE_FORMAT = "DES/ECB/PKCS5Padding";

    static {
        CURRENT_KEY = getCurrentKey(DEFAULT_KEY);
    }

    /**
     * 获取当前密钥
     * @param key
     * @return
     */
    private static Key getCurrentKey(String key){
        if(key == null){
            return CURRENT_KEY;
        }
        Key ans = null;
        KeyGenerator keyGenerator = null;
        try {
            keyGenerator = KeyGenerator.getInstance(ENCODE_MODE);
            // 防止随机种子生成的Key
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(key.getBytes("UTF-8"));
            keyGenerator.init(secureRandom);
            ans = keyGenerator.generateKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ans;
    }

    /**
     * 加密
     * @param msg
     * @return
     */
    public static String encode(String key, String msg){
         return Base64.encodeBase64URLSafeString(obtainEncode(key,msg.getBytes()));
    }

    /**
     * 底层加密方法
     * @param key
     * @param str
     * @return
     */
    private static byte[] obtainEncode(String key, byte[] str){
        byte[] ans = null;
        Cipher cipher;
        try {
            Key codeKey = getCurrentKey(key);
            cipher = Cipher.getInstance(CODE_FORMAT);
            cipher.init(Cipher.ENCRYPT_MODE,codeKey);
            ans = cipher.doFinal(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
            return ans;
    }

    /**
     * 解密
     * @param str
     * @return
     */
    public static String decode(String key, String str){
         return new String(obtainDecode(key,str.getBytes()));
    }

    private static byte[] obtainDecode(String key, byte[] str){
        byte[] ans = null;
        Cipher cipher;
        try {
            Key codeKey = getCurrentKey(key);
            cipher = Cipher.getInstance(CODE_FORMAT);
            cipher.init(Cipher.DECRYPT_MODE,codeKey);
            ans = cipher.doFinal();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ans;
    }
}
