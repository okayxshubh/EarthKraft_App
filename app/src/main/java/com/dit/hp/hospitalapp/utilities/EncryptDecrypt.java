package com.dit.hp.hospitalapp.utilities;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class EncryptDecrypt {

    private static final String SECRET_KEY_1 = "1234567890abcdef"; // 16 bytes
    private static final String SECRET_KEY_2 = "1234567890abcdef"; // 16 bytes

    private static final IvParameterSpec ivParameterSpec = new IvParameterSpec(SECRET_KEY_1.getBytes(StandardCharsets.UTF_8));
    private static final SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY_2.getBytes(StandardCharsets.UTF_8), "AES");

    public static String encrypt(String toBeEncrypt) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] encrypted = cipher.doFinal(toBeEncrypt.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error during encryption: " + e.getMessage();
        }
    }

    public static String decrypt(String encrypted) {
        try {
            encrypted = handleBase64Padding(encrypted);
            byte[] decodedBytes = Base64.getDecoder().decode(encrypted);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);

        } catch (Exception e) {
            e.printStackTrace();
            return "General Decryption Error: " + e.getMessage();
        }
    }

    private static String handleBase64Padding(String base64) {
        if (base64 == null || base64.isEmpty()) {
            throw new IllegalArgumentException("Base64 input is empty");
        }
        base64 = base64.replace("\n", "").replace("\r", "").replace(" ", ""); // Clean unwanted characters
        int paddingLength = base64.length() % 4;
        if (paddingLength > 0) {
            base64 += "=".repeat(4 - paddingLength);
        }
        return base64;
    }
}
