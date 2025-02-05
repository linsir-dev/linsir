package com.linsir.core.utils;


import java.util.Base64;

/**
 * description：Base64Utils
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/2/5 23:18
 */
public class Base64Utils {
    /**
     * Base64-encode the given byte array.
     * @param src the original byte array
     * @return the encoded byte array
     */
    public static byte[] encode(byte[] src) {
        if (src.length == 0) {
            return src;
        }
        return Base64.getEncoder().encode(src);
    }

    /**
     * Base64-decode the given byte array.
     * @param src the encoded byte array
     * @return the original byte array
     */
    public static byte[] decode(byte[] src) {
        if (src.length == 0) {
            return src;
        }
        return Base64.getDecoder().decode(src);
    }

    /**
     * Base64-encode the given byte array using the RFC 4648
     * "URL and Filename Safe Alphabet".
     * @param src the original byte array
     * @return the encoded byte array
     * @since 4.2.4
     */
    public static byte[] encodeUrlSafe(byte[] src) {
        if (src.length == 0) {
            return src;
        }
        return Base64.getUrlEncoder().encode(src);
    }

    /**
     * Base64-decode the given byte array using the RFC 4648
     * "URL and Filename Safe Alphabet".
     * @param src the encoded byte array
     * @return the original byte array
     * @since 4.2.4
     */
    public static byte[] decodeUrlSafe(byte[] src) {
        if (src.length == 0) {
            return src;
        }
        return Base64.getUrlDecoder().decode(src);
    }

    /**
     * Base64-encode the given byte array to a String.
     * @param src the original byte array
     * @return the encoded byte array as a UTF-8 String
     */
    public static String encodeToString(byte[] src) {
        if (src.length == 0) {
            return "";
        }
        return Base64.getEncoder().encodeToString(src);
    }

    /**
     * Base64-decode the given byte array from an UTF-8 String.
     * @param src the encoded UTF-8 String
     * @return the original byte array
     */
    public static byte[] decodeFromString(String src) {
        if (src.isEmpty()) {
            return new byte[0];
        }
        return Base64.getDecoder().decode(src);
    }

    /**
     * Base64-encode the given byte array to a String using the RFC 4648
     * "URL and Filename Safe Alphabet".
     * @param src the original byte array
     * @return the encoded byte array as a UTF-8 String
     */
    public static String encodeToUrlSafeString(byte[] src) {
        return Base64.getUrlEncoder().encodeToString(src);
    }

    /**
     * Base64-decode the given byte array from an UTF-8 String using the RFC 4648
     * "URL and Filename Safe Alphabet".
     * @param src the encoded UTF-8 String
     * @return the original byte array
     */
    public static byte[] decodeFromUrlSafeString(String src) {
        return Base64.getUrlDecoder().decode(src);
    }
}
