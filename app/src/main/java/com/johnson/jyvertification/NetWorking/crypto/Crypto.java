package com.johnson.jyvertification.NetWorking.crypto;

import com.johnson.jyvertification.Consts.PublicUtil;

import org.spongycastle.util.Arrays;
import org.spongycastle.util.encoders.Base64;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;



/**
 * Created by javiermanzanomorilla on 04/01/15.
 */
public class Crypto {

//    public static void writePublicKeyToPreferences(KeyPair key) {
//        StringWriter publicStringWriter = new StringWriter();
//        try {
//            PemWriter pemWriter = new PemWriter(publicStringWriter);
//            pemWriter.writeObject(new PemObject("PUBLIC KEY", key.getPublic().getEncoded()));
//            pemWriter.flush();
//            pemWriter.close();
//            Preferences.putString(Preferences.RSA_PUBLIC_KEY, publicStringWriter.toString());
//        } catch (IOException e) {
//            Log.e("RSA", e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//    public static void writePrivateKeyToPreferences(KeyPair keyPair) {
//        StringWriter privateStringWriter = new StringWriter();
//        try {
//            PemWriter pemWriter = new PemWriter(privateStringWriter);
//            pemWriter.writeObject(new PemObject("PRIVATE KEY", keyPair.getPrivate().getEncoded()));
//            pemWriter.flush();
//            pemWriter.close();
//            Preferences.putString(Preferences.RSA_PRIVATE_KEY, privateStringWriter.toString());
//        } catch (IOException e) {
//            Log.e("RSA", e.getMessage());
//            e.printStackTrace();
//        }
//    }

    public static PublicKey getRSAPublicKeyFromString(String publicKeyPEM) throws Exception {
//        publicKeyPEM = stripPublicKeyHeaders(publicKeyPEM);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA", "SC");
        byte[] publicKeyBytes = Base64.decode(PublicUtil.public_key.getBytes("UTF-8"));
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKeyBytes);
        return keyFactory.generatePublic(x509KeySpec);
    }

    public static PrivateKey getRSAPrivateKeyFromString(String privateKeyPEM) throws Exception {
//        privateKeyPEM = stripPrivateKeyHeaders(privateKeyPEM);
        KeyFactory fact = KeyFactory.getInstance("RSA", "SC");
        byte[] clear = Base64.decode(PublicUtil.private_key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(clear);
        PrivateKey priv = fact.generatePrivate(keySpec);
        Arrays.fill(clear, (byte) 0);
        return priv;
    }

//    public static String stripPublicKeyHeaders(String key) {
//        //strip the headers from the key string
//        StringBuilder strippedKey = new StringBuilder();
//        String lines[] = key.split("\n");
//        for (String line : lines) {
//            if (!line.contains("BEGIN PUBLIC KEY") && !line.contains("END PUBLIC KEY") && !Strings.isNullOrEmpty(line.trim())) {
//                strippedKey.append(line.trim());
//            }
//        }
//        return strippedKey.toString().trim();
//    }

//    public static String stripPrivateKeyHeaders(String key) {
//        StringBuilder strippedKey = new StringBuilder();
//        String lines[] = key.split("\n");
//        for (String line : lines) {
//            if (!line.contains("BEGIN PRIVATE KEY") && !line.contains("END PRIVATE KEY") && !Strings.isNullOrEmpty(line.trim())) {
//                strippedKey.append(line.trim());
//            }
//        }
//        return strippedKey.toString().trim();
//    }

}
