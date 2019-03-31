import java.util.Scanner;

public class Caesar{

    private int key;
    private String plainText;
    private String cipherText;
    private static char[] letters;

    //For Encryption
    public Caesar (String plainText,int key){
        this.key = key;
        this.plainText = plainText;
        letters = new char[26];
        for (int i=65; i<=90; i++){
            letters[i-65] = (char)i;
        }
    }
     //For Decryption
    public Caesar(int key,String cipherText){
        this.key = key;
        this.cipherText = cipherText;
         letters = new char[26];
        for (int i=65; i<=90; i++){
            letters[i-65] = (char)i;
        }
    }
    //Main method
    public static void main (String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Press 1 for Encryption and 2 for Decryption: ");
        int choice = scanner.nextInt();
        switch (choice){
            case 1:
                System.out.print("Enter Plain Text: ");
                String plainTextE = scanner.next();
                System.out.print("Enter key: ");
                int keyE = scanner.nextInt();
                Caesar cE = new Caesar(plainTextE,keyE);
                String cipherTextE = cE.encrypt();
                System.out.println("Cipher Text is: "+cipherTextE);
            break;

            case 2:
                System.out.print("Enter Cipher Text: ");
                String cipherTextD = scanner.next();
                System.out.print("Enter key: ");
                int keyD = scanner.nextInt();
                Caesar cD = new Caesar(keyD,cipherTextD);
                //System.out.println(cD.cipherText);
                String plainTextD = cD.decrypt();
                System.out.println("Plain Text is: "+plainTextD);
            break;

            default:
            System.out.println("Your Choice could not be processed");
            System.exit(0);
            break;
        }
    }
    //For Encryption
    private String encrypt(){
        char cipherArray[] = new char[this.plainText.length()];
        for (int i=0; i<this.plainText.length(); i++){
            int plainIndex  = new String(letters).indexOf(plainText.charAt(i)+"".toUpperCase());
            int cipherIndex = plainIndex + this.key;
            if (cipherIndex > 25){
                cipherIndex %= 26;
            }
            cipherArray[i] = letters[cipherIndex];
           
        }
        return new String(cipherArray);
    }
    //For Decryption
    private String decrypt(){
         char plainArray[] = new char[this.cipherText.length()];
          for (int i=0; i<this.cipherText.length(); i++){
            int cipherIndex = new String(letters).indexOf(this.cipherText.charAt(i)+"");
            int plainIndex  = cipherIndex - this.key;
            if (plainIndex < 0){
                 plainIndex = getPositiveKey(plainIndex);
                 if (plainIndex>25){
                     plainIndex %= plainIndex;
                 }
            }
        
            plainArray[i] = letters[plainIndex];
           
        }

        return new String(plainArray);

    }

    //Calculates positive key value from negative one
    private static int getPositiveKey(int key){
        if (key>0)
        return key;
        else return getPositiveKey(key+26);
    }
}