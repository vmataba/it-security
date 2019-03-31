import java.util.Scanner;

public class Vigenere {

    private char[][] alphabets;
    private String plainText;
    private String cipherText;
    private String key;

    public Vigenere (String text,String key, boolean isEncryption){
        if(isEncryption){
            this.plainText = text;
        } else{
            this.cipherText = text;
        }
        this.key = key;
        init();

    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print("Press 1 for Encryption 2 for Decryption: ");
        int choice = s.nextInt();
        switch (choice){
            case 1://Encryption
            System.out.print("Enter a Plain Text: ");
            String pText  = s.next();
            System.out.print("Enter a Key: ");
            String kE = s.next();
            Vigenere vE = new Vigenere(pText,kE,true);
            vE.displayMatrix();
            System.out.println("\nCipher Text is: "+vE.encrypt());
            break;

            case 2://Decryption
            System.out.print("Enter a Cipher Text: ");
            String cText  = s.next();
            System.out.print("Enter a Key: ");
            String kD = s.next();
            Vigenere vD = new Vigenere(cText,kD,false);
            vD.displayMatrix();
            System.out.println("\nPlain Text is: "+vD.decrypt());
            break;

            default://Unkown Choice
            System.out.println("Unkown Choice!");
            System.exit(0);
            break;
        }

    }

    private void init(){
        alphabets = new char[27][27];
        //Fill Key Alphabets
        for (int i=66; i<=91; i++){
            alphabets[0][i-65] = (char)(i-1);
        }
        //Fill Pain/Cipher Text Alphabets
        for (int i=66; i<=91; i++){
            alphabets[i-65][0] = (char)(i-1);
        }
        //Fill Central part
        for (int i=66; i<=91; i++){
            for (int j=i;j<=91; j++){
               int row = i-65;
               int col = j-65;
               if (row>1){
                col -=(row-1);
                if (j==91){
                    int count = col;
                    for (int k=65; k<=90; k++){
                        if (count<26){
                            alphabets[row][++count] = (char)k;
                        }
                    }
                }
               }

                alphabets[row][col] = (char)(j-1);
            }
        }
    }

    private void displayMatrix(){
        System.out.println("\nVigenere Cipher Matrix");
        for (int i=0; i<alphabets.length; i++){
            for (int j=0; j<alphabets[i].length; j++){
                System.out.print(alphabets[i][j]+" ");
            }
            System.out.println("");
        }
    }

    //For Encryption
    private String encrypt(){
        //Ensure Key Length is same as PlainText Length
        if (this.key.length() != this.plainText.length()){
            if (this.key.length()<this.plainText.length()){
                for (int i=0; this.key.length() != this.plainText.length(); i++){
                    this.key += this.key.charAt(i%this.key.length());
                }
            } else {
                for (int i=0; this.key.length() != this.plainText.length(); i++){
                    this.key = this.key.replace(this.key.charAt(this.key.length()-1)+"","");
                } 
            }
        }
        String cText = "";
        for (int i=0; i<this.key.length(); i++){
            cText += this.getCipherAlpha(this.key.charAt(i),this.plainText.charAt(i));
        }
        return cText;
    }

    //For Decryption
    private String decrypt(){
        //Ensure Key Length is same as PlainText Length
        if (this.key.length() != this.cipherText.length()){
            if (this.key.length()<this.cipherText.length()){
                for (int i=0; this.key.length() != this.cipherText.length(); i++){
                    this.key += this.key.charAt(i%this.key.length());
                }
            } else {
                for (int i=0; this.key.length() != this.cipherText.length(); i++){
                    this.key = this.key.replace(this.key.charAt(this.key.length()-1)+"","");
                } 
            }
        }
        String pText = "";
        for (int i=0; i<this.cipherText.length(); i++){
            pText += this.getPlainAlpha(this.key.charAt(i),this.cipherText.charAt(i));
        }
        return pText;
    }

    private char getCipherAlpha(char keyAlpha, char textAlpha){
        int col = new String(alphabets[0]).indexOf(keyAlpha+"");
        String text = "";
        for (int i=1; i<27; i++){
            text += alphabets[i][0];
        }
        int row = text.indexOf(textAlpha+"")+1;
        return alphabets[row][col];
    }

    private char getPlainAlpha(char keyAlpha, char cipherAlpha){
        int col = new String(alphabets[0]).indexOf(keyAlpha+"");
        char pAlpha = '-';
        for (int i=0; i<alphabets.length; i++){
            for (int j=0; j<alphabets[i].length; j++){
                if (j == col){
                    if (alphabets[i][j] == cipherAlpha){
                        pAlpha = alphabets[i][0];
                        break;
                    }
                } else {
                    continue;
                }
            }
        }
        return pAlpha;
    }

    private void test(){
        displayMatrix();
    }
}