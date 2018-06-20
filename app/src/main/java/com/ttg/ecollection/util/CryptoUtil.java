package com.ttg.ecollection.util;

import android.util.Base64;

import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class CryptoUtil {
    /**对字符串sha1编码*/
    public static String SHA1(String decript) {
        BigInteger sha =null;
        byte[] inputData = decript.getBytes();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(inputData);
            sha = new BigInteger(messageDigest.digest());
            return sha.toString(32);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**MD5加密*/
    public static String Md5(String source) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] bytes =  md5.digest(source.getBytes());
        // 字符数组转换成字符串返回
        return byteArrayToHex(bytes);
    }

    /**字符数组转换成字符串*/
    private static String byteArrayToHex(byte[] byteArray) {
        // 首先初始化一个字符数组，用来存放每个16进制字符
        char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9', 'a','b','c','d','e','f' };
        // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
        char[] resultCharArray =new char[byteArray.length * 2];
        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b>>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b& 0xf];
        }
        // 字符数组组合成字符串返回
        return new String(resultCharArray);
    }

    private static String KEY_ALGORITHM = "RSA/ECB/PKCS1Padding";
    
    /**RSA最大加密明文大小 */  
    private static final int MAX_ENCRYPT_BLOCK = 117;  
      
    /*** RSA最大解密密文大小 */  
    private static final int MAX_DECRYPT_BLOCK = 128;  


    /**RSA公钥解密*/
    public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey) throws Exception {
        byte[] keyBytes = Base64.decode(publicKey, Base64.DEFAULT);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        return cipher.doFinal(encryptedData);
    }

    /**RSA公钥加密*/
    public static byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception {
    	byte[] keyBytes = Base64.decode(publicKey, Base64.DEFAULT);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        return cipher.doFinal(data);
//    	Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
//		cipher.init(Cipher.ENCRYPT_MODE, publicK);
//		int blockSize = cipher.getBlockSize();// 获得加密块大小，如：加密前数据为128个byte，而key_size=1024
//		// 加密块大小为127byte,加密后为128个byte;因此共有2个加密块，第一个127byte第二个为1个byte
//		int outputSize = cipher.getOutputSize(data.length);// 获得加密块加密后块大小
//		int leavedSize = data.length % blockSize;
//		int blocksSize = leavedSize != 0 ? data.length / blockSize + 1
//				: data.length / blockSize;
//		byte[] raw = new byte[outputSize * blocksSize];
//		int i = 0;
//		while (data.length - i * blockSize > 0) {
//			if (data.length - i * blockSize > blockSize)
//				cipher.doFinal(data, i * blockSize, blockSize, raw, i
//						* outputSize);
//			else
//				cipher.doFinal(data, i * blockSize, data.length - i
//						* blockSize, raw, i * outputSize);
//			// 这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把byte[]放到
//			// ByteArrayOutputStream中，而最后doFinal的时候才将所有的byte[]进行加密，可是到了此时加密块大小很可能已经超出了
//			// OutputSize所以只好用dofinal方法。
//
//			i++;
//		}
//		return raw;


//        return cipher.doFinal(data);
    }

    /**RSA私钥解密*/
    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey) throws Exception {
        byte[] keyBytes = Base64.decode(privateKey, Base64.DEFAULT);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
//        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        return cipher.doFinal(encryptedData);
    }

    /**RSA私钥加密*/
    public static byte[] encryptByPrivateKey(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = Base64.decode(privateKey, Base64.DEFAULT);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        return cipher.doFinal(data);
    }
    
    private static String AES_KEY_ALGORITHM = "AES/ECB/PKCS5Padding";
//    private static String DES = "AES";

    /**AES解密*/
    public static String AESDecrypt(String sSrc, String sKey) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(sKey.getBytes("ASCII"), "AES");
        Cipher cipher = Cipher.getInstance(AES_KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] encrypted1 = hex2bin(sSrc);
        byte[] original = cipher.doFinal(encrypted1);
        return new String(original);
    }

    /**AES加密*/
    public static String AESEncrypt(String sSrc, String sKey) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(sKey.getBytes("ASCII"), "AES");
        Cipher cipher = Cipher.getInstance(AES_KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes());
        return bin2hex(encrypted);
    }

    /**AES加密,base64编码*/
    public static String AESEncryptBase64(String sSrc, String sKey) throws Exception {
    	SecretKeySpec skeySpec = new SecretKeySpec(sKey.getBytes("ASCII"), "AES");
    	Cipher cipher = Cipher.getInstance(AES_KEY_ALGORITHM);
    	cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
    	byte[] encrypted = cipher.doFinal(sSrc.getBytes());
    	return Base64.encodeToString(encrypted, Base64.DEFAULT);
    }

    /**二进制转十六进制*/
    public static String bin2hex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**十六进制转二进制*/
    public static byte[] hex2bin(String src) {
        int l = src.length() / 2;
        byte[] ret = new byte[l];
        for (int i = 0; i < l; i++) {
            ret[i] = Integer.valueOf(src.substring(i * 2, i * 2 + 2), 16).byteValue();
        }
        return ret;
    }

    /** *//**
     * <p>
     * 用私钥对信息生成数字签名
     * </p>
     *
     * @param data 已加密数据
     * @param privateKey
     *
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = Base64.decode(privateKey,Base64.DEFAULT);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance("SHA1withRSA");
        signature.initSign(privateK);
        signature.update(data);
        return bin2hex(signature.sign());
    }

    /** *//**
     * <p>
     * 校验数字签名
     * </p>
     *
     * @param data 已加密数据
     * @param publicKey 公钥(BASE64编码)
     * @param sign 数字签名
     *
     * @return
     * @throws Exception
     *
     */
    public static boolean verify(byte[] data, String publicKey, String sign)
            throws Exception {
        byte[] keyBytes = Base64.decode(publicKey,Base64.DEFAULT);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance("SHA1withRSA");
        signature.initVerify(publicK);
        signature.update(data);
        return signature.verify(hex2bin(sign));
    }
}
