/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pgp;

/**
 *
 * @author emmanuel
 */
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.util.Base64;
import java.util.Scanner;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import javax.crypto.Cipher;
import static jdk.nashorn.internal.codegen.Compiler.LOG;

public class Pgp {

    public static KeyPair ALICE_KEY_PAIR;
    public static KeyPair BOB_KEY_PAIR;

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        //Create Instance of KeyPair Genearator
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        //Generate ALICE KEY PAIR
        ALICE_KEY_PAIR = kpg.genKeyPair();
        //Genearate BOB KEY PAIR
        BOB_KEY_PAIR = kpg.genKeyPair();
        /**
         * **Start ALICE'S Side***
         */
        System.out.println("\n***ALICE side (SENDER)***");
        //Display ALICE Private Key
        System.out.println("Private Key: " + ALICE_KEY_PAIR.getPrivate());
        //Display ALICE Public Key
        System.out.println("Public Key: " + ALICE_KEY_PAIR.getPublic());
        //Initialize Scanner Object
        Scanner s = new Scanner(System.in);
        //Prompt ALICE to Enter a Message/Email
        System.out.print("Enter an Email to send: ");
        //Read Email
        String email = s.nextLine();
        //Hash an Email With MD5
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] emailBuffer = md.digest(email.getBytes());
        //Display Message Digest for Email
        String messageDigest = getHexString(emailBuffer);
        System.out.println("Message Digest: " + messageDigest);
        //Encrypt Message Digest
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, ALICE_KEY_PAIR.getPrivate());
        byte[] certificateBuffer = cipher.doFinal(emailBuffer);
        String certificateString = getHexString(certificateBuffer);
        //Display Certificate
        System.out.println("Signature: " + certificateString);
        //Join Email and Encrypted Message Digest
        //String emailCertificate = email + certificateString;
        //Display Joined Email and Certificate
       // System.out.println("Email + Certificate: " + emailCertificate);

        //Encrypt with Bob Public Key
        Cipher cipher2 = Cipher.getInstance("RSA");
        cipher2.init(Cipher.ENCRYPT_MODE, BOB_KEY_PAIR.getPublic());
        byte[] finalBuffer = cipher2.doFinal(certificateBuffer);
        String sentContent = getHexString(finalBuffer);
        System.out.println("The Down Message Will be sent to BOB");
        System.out.println(sentContent);
        //Send Encrypted Result
        System.out.println("Sending Message to BOB ...\n\n");
        /**
         * **Start BOB'S Side***
         */
        System.out.println("\n***BOB side (RECEIVER)***");
        //Receive Result
        //Decrypt Result with ALICE Public Key
        //Separate Email and Encrypted Message Digest
        //Decrypt Encrypted Message Digest with ALICE public Key
        //Read Email Digest
        //Hash Received Email
        //Compare Two Message Digests
    }

    private static String getHexString(byte[] text) throws Exception {
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

    private static byte[] compress(byte[] data) throws Exception {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        deflater.finish();
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer); // returns the generated code... index  
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();
        byte[] output = outputStream.toByteArray();
        return output;
    }

    public static byte[] decompress(byte[] data) throws Exception {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!inflater.finished()) {
            int count = inflater.inflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();
        byte[] output = outputStream.toByteArray();
        return output;
    }

}
