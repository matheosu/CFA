package br.edu.unigranrio.ect.si.cfa.commons.util;


import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

/**
 * Classe responsável por segurança e criptgrafias.
 *
 * @author matheuscastro
 */
public final class SecurityUtils {

    private static final String MD5 = "MD5";
    private static final String ALGORITHM = "PBEWithMD5AndDES";
    private static final String FINAL_ENCRYPT = "=";
    private static final String NEW_FINAL_ENCRYPT = "/*";
    private static final String DEFAULT_PASSPHRASE = "ControleDeFluxoDeAgua";

    private static final int    ITERATION_COUNT = 19;
    private static final byte[] SALT = {
            (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32,
            (byte) 0x56, (byte) 0x34, (byte) 0xE3, (byte) 0x03
    };


    private SecurityUtils() {
    }

    /**
     * md5 de uma string em representação Hexadecimal
     *
     * @param data string
     * @return md5 em hexadecimal
     */
    public static String md5Hex(String data) {
        return StringUtils.bytes2Hex(md5(data));
    }

    /**
     * md5 de uma string
     *
     * @param data string
     * @return md5 em array de bytes
     */
    public static byte[] md5(String data) {
        return data != null ? md5(StringUtils.string2Bytes(data)) : null;
    }

    public static byte[] md5(byte[] data) {
        MessageDigest md5 = getAlgorithm(MD5);
        return (md5 != null && data != null) ? md5.digest(data) : null;
    }

    public static String encrypt(String data) throws SecurityException {
        return encrypt(DEFAULT_PASSPHRASE, data);
    }

    public static String encrypt(String passPhrase, String data) throws SecurityException {
        Cipher cipher = getCipher(passPhrase, Cipher.ENCRYPT_MODE);
        try {
            byte[] crypt = cipher != null ? cipher.doFinal(StringUtils.string2Bytes(data)) : null;

            return crypt != null ? new String(Base64.getEncoder().encode(crypt)).replace(FINAL_ENCRYPT, NEW_FINAL_ENCRYPT) : "";
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new SecurityException("Error in encrypt data");
        } catch (Exception e) {
            throw new SecurityException("Exception to encrypt data", e);
        }
    }

    public static String decrypt(String data) throws SecurityException {
        return decrypt(DEFAULT_PASSPHRASE, data);
    }

    public static String decrypt(String passPhrase, String data) throws SecurityException {
        data = data != null ? data.replace(NEW_FINAL_ENCRYPT, FINAL_ENCRYPT) : "";
        Cipher cipher = getCipher(passPhrase, Cipher.DECRYPT_MODE);
        try {
            byte[] crypt = cipher != null ? cipher.doFinal(Base64.getDecoder().decode(data)) : null;
            return StringUtils.byte2String(crypt);
        } catch (BadPaddingException | IllegalBlockSizeException e) {
            throw new SecurityException("Error in decrypt data");
        } catch (Exception e) {
            throw new SecurityException("Exception to decrypt data",e);
        }
    }

    private static MessageDigest getAlgorithm(String algorithm) throws SecurityException {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new SecurityException("NoSuchAlgorithmException", e);
        }
    }

    private static Cipher getCipher(String passPhrase, int mode) throws SecurityException {
        try {
            KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), SALT, ITERATION_COUNT);
            SecretKey key = SecretKeyFactory.getInstance(ALGORITHM).generateSecret(keySpec);
            Cipher cipher = Cipher.getInstance(key.getAlgorithm());
            AlgorithmParameterSpec paramSpec = new PBEParameterSpec(SALT, ITERATION_COUNT);
            cipher.init(mode, key, paramSpec);
            return cipher;
        } catch (InvalidAlgorithmParameterException e) {
            throw new SecurityException("InvalidAlgorithmParameterException");
        } catch (InvalidKeySpecException e) {
            throw new SecurityException("InvalidKeySpecException");
        } catch (NoSuchPaddingException e) {
            throw new SecurityException("NoSuchPaddingException");
        } catch (NoSuchAlgorithmException e) {
            throw new SecurityException("NoSuchAlgorithmException");
        } catch (InvalidKeyException e) {
            throw new SecurityException("InvalidKeyException");
        } catch (Exception e) {
            throw new SecurityException("Error in getCipher ", e);
        }
    }


}
