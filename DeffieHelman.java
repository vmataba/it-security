/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.math.BigInteger;
import java.util.Scanner;

/**
 *
 * @author victor
 */
public class DeffieHelman {

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);
        try {
            System.out.print("Enter Generator (Should be Prime): ");
            //Generator
            BigInteger g = new BigInteger(s.next());
            if (!g.isProbablePrime(1)) {
                throw new Exception("Not a prime number");
            }
            System.out.print("Enter Prime number: ");
            //Prime number
            BigInteger P = new BigInteger(s.next());
            if (!P.isProbablePrime(1)) {
                throw new Exception("Not a prime number");
            }

            System.out.println("\n");
            System.out.println("Alice's Side.....");
            //Alice Private Key
            System.out.print("Enter Private Key: ");
            BigInteger aliceSecretKey = new BigInteger(s.next());
            BigInteger alice = g.modPow(aliceSecretKey, P);
            //Bob's Side
            System.out.println("\n");
            System.out.println("Bob's Side.....");
            System.out.print("Enter Bob's private Key: ");
            BigInteger bobSecretKey = new BigInteger(s.next());
            BigInteger bob = g.modPow(bobSecretKey, P);

            BigInteger aliceKey = bob.modPow(aliceSecretKey, P);
            BigInteger bobKey = alice.modPow(bobSecretKey, P);

            System.out.println("\nResults....");
            System.out.println("Alice's Key: " + aliceKey);
            System.out.println("Bobs's Key: " + bobKey);

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }

    }

}
