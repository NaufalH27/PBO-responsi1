package org.uns.notepadmini.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.uns.notepadmini.exception.UnknownException;

public class HashHelper {
    public static byte[] hash(String text) throws UnknownException {
       try {
            String normalizeText = normalizeText(text);
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return md.digest(normalizeText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new UnknownException(e);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }

    public static boolean checkHash(byte[] hashedBytes, String input) throws UnknownException {
        String hashedBytesHex = bytesToHex(hashedBytes);
        String inputHashHex = bytesToHex(hash(input));
        
        return hashedBytesHex.equals(inputHashHex);
    }

    //normilize to windows .txt format
    public static String normalizeText(String input) {
        input = input.replace("\r\n", "\n").replace("\r", "\n");        
        return input;
    }
}
