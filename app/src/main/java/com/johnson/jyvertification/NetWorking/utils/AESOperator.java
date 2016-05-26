package com.johnson.jyvertification.NetWorking.utils;

/**
 * Created by johnsmac on 4/22/16.
 */


import android.util.Log;

import com.johnson.jyvertification.NetWorking.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES 是一种可逆加密算法，对用户的敏感信息加密处理 对原始数据进行AES加密后，在进行Base64编码转化；
 */
public class AESOperator {

    private String TAG = AESOperator.class.getSimpleName();

    /**
     * 每次生成是不同的
     */
    public String generateKey() {

        KeyGenerator kg;
        String key = null;
        try {
            kg = KeyGenerator.getInstance("AES");
            kg.init(192);//要生成多少位，只需要修改这里即可128, 192或256
            SecretKey sk = kg.generateKey();
            byte[] bytes = sk.getEncoded();
            key = Base64Utils.encode(bytes).toString();
            Log.i(TAG, key);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return key;

    }


    private String ivParameter = "0000000000000000";//偏移量,可自行修改
    private static AESOperator instance = null;

    private AESOperator() {

    }

    public static AESOperator getInstance() {
        if (instance == null)
            instance = new AESOperator();
        return instance;
    }


    // 加密
    public String encrypt(String sSrc, String key) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            int blockSize = cipher.getBlockSize();//块的大小（以字节为单位）；如果底层算法不是 Cipher 块，则返回 0
            byte[] dataBytes = sSrc.getBytes("UTF-8");
            int plaintextLength = dataBytes.length;
            /**
             *AES加密 数据块分组长度 必须为128比特，
             * 密钥长度可以是128比特、192比特、256比特中的任意一个（如果数据块及密钥长度不足时，会补齐）
             * */
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(ivParameter.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);
            return Base64Utils.encode(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


//    public String encodeBytes(byte[] bytes) {
//        StringBuffer strBuf = new StringBuffer();
//
//        for (int i = 0; i < bytes.length; i++) {
//            strBuf.append((char) (((bytes[i] >> 4) & 0xF) + ((int) 'a')));
//            strBuf.append((char) (((bytes[i]) & 0xF) + ((int) 'a')));
//        }
//
//        return strBuf.toString();
//    }

//    public static void main(String[] args) throws Exception {
//        // 需要加密的字串
//        String cSrc = "[{\"request_no\":\"1001\",\"service_code\":\"FS0001\",\"contract_id\":\"100002\",\"order_id\":\"0\",\"phone_id\":\"13913996922\",\"plat_offer_id\":\"100094\",\"channel_id\":\"1\",\"activity_id\":\"100045\"}]";
//
//        // 加密
//        long lStart = System.currentTimeMillis();
//        String enString = AESOperator.getInstance().encrypt(cSrc);
//        System.out.println("加密后的字串是：" + enString);
//
//        long lUseTime = System.currentTimeMillis() - lStart;
//        System.out.println("加密耗时：" + lUseTime + "毫秒");
//        // 解密
//        lStart = System.currentTimeMillis();
//        String DeString = AESOperator.getInstance().decrypt(enString);
//        System.out.println("解密后的字串是：" + DeString);
//        lUseTime = System.currentTimeMillis() - lStart;
//        System.out.println("解密耗时：" + lUseTime + "毫秒");
//    }

}