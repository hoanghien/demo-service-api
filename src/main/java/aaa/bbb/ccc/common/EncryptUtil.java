package aaa.bbb.ccc.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

@Slf4j
public class EncryptUtil {
    private SecretKeySpec secretKey;
    private String CHAR_SET = "UTF-8";

    public void setKey(String strKey) {
        MessageDigest sha = null;
        try {
            byte[] key = strKey.getBytes(CHAR_SET);
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException e) {
            log.error("", e);
        } catch (UnsupportedEncodingException e) {
            log.error("", e);
        }
    }

    public String encrypt(String strData, String strKey) {
        try {
            setKey(strKey);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            String strEncrypt = Base64.getEncoder().encodeToString(cipher.doFinal(strData.getBytes(CHAR_SET)));
            String strHexEncrypt = Hex.encodeHexString(strEncrypt.getBytes(CHAR_SET));
            return strHexEncrypt;
        } catch (Exception e) {
            log.error("Error while encrypting: " + e.toString());
        }
        return null;
    }

//    public String encrypt(String strData) {
//        try {
//            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
//            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
//            String strEncrypt = Base64.getEncoder().encodeToString(cipher.doFinal(strData.getBytes(CHAR_SET)));
//            String strHexEncrypt = Hex.encodeHexString(strEncrypt.getBytes(CHAR_SET));
//            return strHexEncrypt;
//        } catch (Exception e) {
//            log.error("Error while encrypting: " + e.toString());
//        }
//        return null;
//    }
    public static String encrypt(String strData) {
        try{
            MessageDigest md5 = MessageDigest.getInstance("SHA1");
            byte[] digest = md5.digest(strData.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < digest.length; ++i) {
                sb.append(Integer.toHexString((digest[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString().toUpperCase();
        } catch (Exception e) {
            log.error("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public String decrypt(String strData, String strKey) {
        try {
            byte[] bDecrypt = Hex.decodeHex(strData.toCharArray());
            strData = new String(bDecrypt);

            setKey(strKey);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            String strDecrypt = new String(cipher.doFinal(Base64.getDecoder().decode(strData)));
            return strDecrypt;
        } catch (Exception e) {
            log.error("Error while decrypting: " + e.toString());
        }
        return null;
    }


    public static String hash(String messages) throws Exception{
        if(messages==null) throw new NullPointerException();
        StringBuffer result = null;
        byte[] data = messages.getBytes();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(data);
            byte[] msgDigest = md.digest();
            result = new StringBuffer();
            for(int i=0; i<msgDigest.length; i++) {
                String hex = Integer.toHexString(0xff & msgDigest[i]);
                if(hex.length()==1) result.append('0');
                result.append(hex);
            }
        } catch (NoSuchAlgorithmException ex) {
            throw new Exception(ex.toString());
        }
        return result.toString();
    }
}
