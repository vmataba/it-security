/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package pgp;

import java.security.MessageDigest;

/**
 *
 * @author victor
 */
public class Tools {

    public static final String dataPattern = "%..%";
    public static final String dataPattern2 = "*";

    public static String getHexString(byte[] text) throws Exception {
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < text.length; i++) {
            String hex = Integer.toHexString(0xff & text[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
        //return new String(text,"UTF8");
    }

    public static String[] splitAt245(String message) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            sb.append(message.charAt(i));
            if ((i + 1) % 245 == 0) {
                sb.append(",");
            }
        }
        return sb.toString().split(",");
    }

    public static String md5(String text) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] textBuffer = md.digest(text.getBytes());
        //Display Message Digest for Email
        String digest = Tools.getHexString(textBuffer);
        return digest;
    }

}
