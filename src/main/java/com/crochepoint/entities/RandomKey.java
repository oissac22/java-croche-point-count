package com.crochepoint.entities;

import java.security.SecureRandom;

public class RandomKey {
    public static byte[] generateRandomKey(int keyLength) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[keyLength];
        secureRandom.nextBytes(key);
        return key;
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b:bytes) {
            hexString.append(String.format("%02X", b));
        }
        return hexString.toString();
    }

    public static String generateRandonKeyOnHex(int keyLength) {
        return bytesToHex(generateRandomKey(keyLength));
    }
}
