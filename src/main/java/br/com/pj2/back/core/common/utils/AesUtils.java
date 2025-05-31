package br.com.pj2.back.core.common.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public final class AesUtils {

    private static final String ALGORITHM = "AES";

    private static byte[] getKeyBytes(String key) {
        byte[] keyBytes = new byte[16];
        byte[] inputBytes = key.getBytes(StandardCharsets.UTF_8);

        int length = Math.min(inputBytes.length, keyBytes.length);
        System.arraycopy(inputBytes, 0, keyBytes, 0, length);
        return keyBytes;
    }

    public static String decrypt(String encryptedData, String key) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(getKeyBytes(key), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Erro durante a descriptografia", e);
        }
    }
}
