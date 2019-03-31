/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package pgp;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

/**
 *
 * @author emmanuel
 */
public class Alice {

    private static Key privateKey;
    public static Key publicKey;
    protected static Cipher cipher;
    public static byte[][] data;
    public static byte[] signatureBuffer;
    public static String sentData;

    public static void sendEmail(String email) throws Exception {
        //Initialize all necessary variables
        //init();
        //Hash Email with MD5
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] emailBuffer = md.digest(email.getBytes());
        //Display Message Digest for Email
        String messageDigest = Tools.getHexString(emailBuffer);
        System.out.println("\nMESSAGE DIGEST: " + messageDigest);
        //Encrypt Message Digest
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        signatureBuffer = cipher.doFinal(emailBuffer);
        String signature = Tools.getHexString(signatureBuffer);
        //Display Certificate
        System.out.println("\nSIGNATURE: " + signature);
        //Append Signature to an email
        String signedEmail = email + Tools.dataPattern + signature;
        //Display Signed Email
        System.out.println("\nEMAIL||SIGNATURE: " + signedEmail.replace(Tools.dataPattern, ""));
        //Encrypt Email + Signature With Bob's Public Key
        byte[][] cSignedEmail = encrypt(Tools.splitAt245(signedEmail));
        data = cSignedEmail;
        //Display Encrypted Signed Email
        String messageToSend = "";
        for (byte[] b : cSignedEmail) {
            messageToSend += Tools.getHexString(b);
        }
        sentData = messageToSend;
        System.out.println("\nTEXT TO BE SENT: " + messageToSend);
        //Send Message
        System.out.println("\nSENDING MESSAGE TO BOB...\n");
    }

    public static void init() throws Exception {
        //Asymmetric Key, i.e RSA Keys Generation
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair pair = kpg.genKeyPair();
        privateKey = pair.getPrivate();
        //Display Private Key
        //System.out.println("Private Key: " + privateKey);
        //Display Public Key
        //System.out.println("Public Key: " + publicKey);
        publicKey = pair.getPublic();

        //Display Session Key
        //RSA Cipher
        cipher = Cipher.getInstance("RSA");
    }

    private static byte[][] encrypt(String[] message) throws Exception {
        byte res[][] = new byte[message.length][245];
        cipher.init(Cipher.ENCRYPT_MODE, Bob.publicKey);
        for (int i = 0; i < message.length; i++) {
            res[i] = cipher.doFinal(message[i].getBytes());
        }
        return res;
    }

    public static void displayInfo() {
        System.out.println("\nPUBLIC KEY: " + publicKey);
        System.out.println("\nPRIVATE KEY: " + privateKey + "\n");
    }

}
