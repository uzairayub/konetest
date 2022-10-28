package com.tsl.elevator.utils;

import android.os.Build;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

public class EncodeUtil {

//    public static void main(String[] args) {
//        EncodeUtil encodeUtil = new EncodeUtil();
//        //System.out.print("Calling encoder");
//        String data =
//        System.out.print(data);
//
//    }

    public static String getEncryptedPassword(String password) {
        return encryptedDataOnJava(password, "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCHuGtqLu3iX7H1ag1kqNShNRIHe0iUHEOnESBgsemF9DQNM6ebpUMMwyUs7lkqfgto5/Ql843n8WEy8uW1T6MwOI/rvYfWDQDaxbc8gjwiG10FFRCdTSUpMBitFylC6++HkxsckT4ZAxIJksV5E7rcBCCWH1fqI7qb/TPMLg+r/QIDAQAB");
    }

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 245;

    public static String encryptedDataOnJava(String data, String PUBLICKEY) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                data = Base64.getEncoder().encodeToString(encryptByPublicKey(data.getBytes(), PUBLICKEY));
            } else {
                data = android.util.Base64.encodeToString(encryptByPublicKey(data.getBytes(), PUBLICKEY), android.util.Base64.DEFAULT);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("报错了");
        }
        return data;
    }

    /**
     * 公钥加密
     *
     * @param data      源数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    private static byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception {
        byte[] keyBytes = new byte[0];
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            keyBytes = Base64.getDecoder().decode(publicKey);
        } else {
            keyBytes = android.util.Base64.decode(publicKey, android.util.Base64.DEFAULT);
        }
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        return getBytes(data, publicK, cipher, Cipher.ENCRYPT_MODE, MAX_ENCRYPT_BLOCK);
    }

    private static byte[] getBytes(byte[] data, Key privateK, Cipher cipher, int encryptMode, int maxEncryptBlock) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
        cipher.init(encryptMode, privateK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache = new byte[0];
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > maxEncryptBlock) {
                try {
                    cache = cipher.doFinal(data, offSet, maxEncryptBlock);
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                }
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * maxEncryptBlock;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /**
     * public static String publicEncrypt(String data, RSAPublicKey publicKey) {
     * try {
     * Cipher cipher = Cipher.getInstance("RSA");
     * cipher.init(Cipher.ENCRYPT_MODE, publicKey);
     * return Base64.encode(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes("UTF-8"), publicKey.getModulus().bitLength()));
     * } catch (Exception e) {
     * throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
     * }
     * }
     **/

    //rsa切割解码  , ENCRYPT_MODE,加密数据   ,DECRYPT_MODE,解密数据
    private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas, int keySize) {
        int maxBlock = 0;  //最大块
        if (opmode == Cipher.DECRYPT_MODE) {
            maxBlock = keySize / 8;
        } else {
            maxBlock = keySize / 8 - 11;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] buff;
        int i = 0;
        try {
            while (datas.length > offSet) {
                if (datas.length - offSet > maxBlock) {
                    //可以调用以下的doFinal（）方法完成加密或解密数据：
                    buff = cipher.doFinal(datas, offSet, maxBlock);
                } else {
                    buff = cipher.doFinal(datas, offSet, datas.length - offSet);
                }
                out.write(buff, 0, buff.length);
                i++;
                offSet = i * maxBlock;
            }
        } catch (Exception e) {
            throw new RuntimeException("加解密阀值为[" + maxBlock + "]的数据时发生异常", e);
        }
        byte[] resultDatas = out.toByteArray();
        if (out != null) {
            try {
                out.close();
                out = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultDatas;
    }

}
