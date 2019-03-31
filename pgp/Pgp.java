/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package pgp;

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

public class Pgp {

    public static KeyPair ALICE_KEY_PAIR;
    public static KeyPair BOB_KEY_PAIR;

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {

        //Initialize Alice public variables
        Alice.init();
        //Initialize Bob's public Variables
        Bob.init();

        //In ALICE Side
        System.out.println("***IN ALICE SIDE (SENDER)***");
        Alice.displayInfo();
        Scanner s = new Scanner(System.in);
        System.out.print("ENTER AN EMAIL: ");
        String email = s.nextLine();
        //Send Message
        Alice.sendEmail(email);

        /**
         * **Start BOB'S Side***
         */
        System.out.println("\n***BOB SIDE (RECEIVER)***");
        //Receive Result
        Bob.displayInfo();
        System.out.print("ENTER RECEIVED EMAIL: ");
        String receivedEmail = s.nextLine();
        Bob.receiveEmail(Alice.data, receivedEmail);
    }
}
