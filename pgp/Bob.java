/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package pgp;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import javax.crypto.Cipher;

/**
 *
 * @author emmanuel
 */
public class Bob {

    private static Key privateKey;
    public static Key publicKey;

    public static void receiveEmail(byte[][] email, String sentData) throws Exception {

        System.out.println("\nDECRYPTING RECEIVED DATA WITH BOB's PRIVATE KEY (AUTHENTICATION TEST)...");

        if (sentData.equals(Alice.sentData)) {
            System.out.println("\nDECRYPTION PASSED (AUTHENTICATION ESTABLISHED)...");
            byte[][] cEmail = decrypt(email);
            String signedEmail = "";
            for (byte[] b : cEmail) {
                signedEmail += new String(b, "UTF8");
            }
            System.out.println("\nEMAIL||SIGNATURE: " + signedEmail.replace(Tools.dataPattern, ""));
            String[] signedEmailArray = signedEmail.split(Tools.dataPattern);
            String email0 = signedEmailArray[0];
            String signature1 = signedEmailArray[1];
            System.out.println("\nRECEIVED EMAIL: " + email0);
            System.out.println("\nRECEIVED SIGNATURE: " + signature1);
            String digest = decryptDigest(Alice.signatureBuffer);
            String bobDigest = Tools.md5(email0);
            System.out.println("\nRECEIVED EMAIL DIGEST: " + digest);
            System.out.println("\nCOMPUTED EMAIL DIGEST: "+ bobDigest );
            System.out.println("\nVALIDATING EMAIL DIGEST (INTEGRITY TEST) ...");
            if (bobDigest.equals(digest)) {
                System.out.println("\nINTEGRITY TEST PASSED ...");

            } else {
                System.out.println("\nEMAIL DIGESTS AREN'T THE SAME, INTEGRITY TEST FAILED ...");
            }
        } else {
            System.out.println("\nDECRYPTION FAILED (AUTHENTICATION FAILED) ...");
        }

    }

    private static byte[][] decrypt(byte[][] cText) throws Exception {
        byte res[][] = new byte[cText.length][245];
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        for (int i = 0; i < cText.length; i++) {
            res[i] = cipher.doFinal(cText[i]);
        }
        return res;
    }

    private static String decryptDigest(byte[] signature) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, Alice.publicKey);
        byte[] cipherSignature = cipher.doFinal(signature);
        return Tools.getHexString(cipherSignature);
    }

    private static void validateSignature(String signature) {

    }

    public static void init() throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair pair = kpg.genKeyPair();
        publicKey = pair.getPublic();
        privateKey = pair.getPrivate();
    }

    public static void displayInfo() {
        System.out.println("\nPUBLIC KEY: " + publicKey);
        System.out.println("\nPRIVATE KEY: " + privateKey + "\n");
    }

}
