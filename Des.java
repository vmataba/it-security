/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author victor
 */
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Scanner;
import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.util.Base64;

public class Des {

    public static final String UNICODE_FORMAT = "UTF8";
    public static final String ENC_SCHEME = "DES";
    private KeySpec keySpec;
    private SecretKeyFactory keyFactory;
    private Cipher cipher;
    private byte[] keyBuffer;
    private String encKey;
    private String encScheme;
    private SecretKey secretKey;

    public Des(String encKey) throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException {
        this.encKey = encKey;
        this.keyBuffer = this.encKey.getBytes(UNICODE_FORMAT);
        this.keySpec = new DESKeySpec(this.keyBuffer);
        this.keyFactory = SecretKeyFactory.getInstance(ENC_SCHEME);
        this.cipher = Cipher.getInstance(ENC_SCHEME);
        this.secretKey = this.keyFactory.generateSecret(this.keySpec);
    }

    private String encrypt(String plainText) {
        String cipherText = "";
        try {
            this.cipher.init(Cipher.ENCRYPT_MODE, this.secretKey);
            byte[] plainStream = plainText.getBytes(UNICODE_FORMAT);
            byte[] cipherStream = this.cipher.doFinal(plainStream);
            cipherText = Base64.getEncoder().encodeToString(cipherStream);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return cipherText;
    }

    private String decrypt(String cipherText) {
        String plainText = "";

        try {
            this.cipher.init(Cipher.DECRYPT_MODE, this.secretKey);
            byte[] cipherStream = Base64.getDecoder().decode(cipherText);
            byte[] plainStream = this.cipher.doFinal(cipherStream);
            for (int i = 0; i < plainStream.length; i++) {
                plainText += (char) plainStream[i];
            }

            //plainText = decoder.dec
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        return plainText;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {

            Scanner scanner = new Scanner(System.in);
            System.out.print("Press 1 for Encryption 2 for Decryption: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.print("Enter Plain Text: ");
                    String plainText = scanner.next();
                    System.out.print("Enter Key:");
                    String key = scanner.next();
                    Des desEnc = new Des(key);
                    System.out.println("Cipher Text is: "+desEnc.encrypt(plainText));
                    break;
                case 2:
                    System.out.print("Enter Cipher Text: ");
                    String cipherText = scanner.next();
                    System.out.print("Enter Key: ");
                    String keyDec = scanner.next();
                    Des desDec = new Des(keyDec);
                    System.out.println("Plain Text is: "+desDec.decrypt(cipherText));
                    break;
                default:
                    System.out.println("Uknown Request");
                    System.exit(0);
                    break;
            }

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }

    }

}
