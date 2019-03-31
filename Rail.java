import java.util.Scanner;

public class Rail{

    private int key;
    private String plainText;
    private String cipherText;
    private char[][] matrix;

    public Rail(String text,int key,boolean isEncryption){
        if (isEncryption){
            this.plainText = text;
        } else{
            this.cipherText = text;
        }
        this.key = key;
        this.matrix = new char[key][text.length()];
        init(text);
    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);
        System.out.print("Press 1 for Encryption 2 for Decryption: ");
        int choice = s.nextInt();
        switch (choice) {
            case 1:
            System.out.print("Enter Plain Text: ");
            String pText = s.next();
            System.out.print("Enter Key: ");
            int kE = s.nextInt();
            Rail rE = new Rail(pText,kE,true);
            System.out.println("\nRail Fence Matrix");
            rE.displayMatrix();
            System.out.println("Cipher Text is: "+rE.encrypt());
                break;
            case 2:
            System.out.print("Enter Cipher Text: ");
            String cText = s.next();
            System.out.print("Enter Key: ");
            int kD = s.nextInt();
            Rail rD = new Rail(cText,kD,false);
            System.out.println("\nRail Fence Matrix");
            rD.decrypt();
            rD.displayMatrix();
            System.out.println("Cipher Text is: "+rD.decrypt());
            break;
            default:
            System.out.println("Colud not process your request");
            System.exit(0);
                break;
        }
        
    }

    private void init(String text){
        int col = 0;
        int row = 0;
        int charIndex = 0;
        boolean fromTop = true;
       while (col <matrix[0].length) {
           if (charIndex<text.length()){
            if (fromTop){
                matrix[row++][col++] = text.charAt(charIndex++);
            } else {
                matrix[row--][col++] = text.charAt(charIndex++);
            }
           } else {
               break;
           }
           if (row == key-1){
               fromTop = false;
           }
           if (row == 0){
               fromTop = true;
           }
           
       }
    }

    private String encrypt(){
        String cText = "";
        for (int i=0; i<this.matrix.length; i++){
            for (int j=0; j<matrix[i].length; j++){
                if ((int)matrix[i][j]>90 || (int)matrix[i][j]<65){
                    continue;
                } else{
                    cText += matrix[i][j];
                }
            }
        }
        return cText;
    }

    private String decrypt(){
        int cipherIndex = 0;
        for (int i=0; i<this.matrix.length; i++){
            for (int j=0; j<matrix[i].length; j++){
                if ((int)matrix[i][j]>90 || (int)matrix[i][j]<65){
                    continue;
                } else{
                     if (cipherIndex<this.cipherText.length()){
                        this.matrix[i][j] = this.cipherText.charAt(cipherIndex++);
                     } else{
                         break;
                     }
                }
            }
        }
        
        String pText = "";
        int col = 0;
        int row = 0;
        int charIndex = 0;
        boolean fromTop = true;
       while (col <matrix[0].length) {
           if (charIndex<this.cipherText.length()){
            if (fromTop){
               pText += matrix[row++][col++];
            } else {
               pText += matrix[row--][col++];
            }
           } else {
               break;
           }
           if (row == key-1){
               fromTop = false;
           }
           if (row == 0){
               fromTop = true;
           }
           
       }
       return pText;
    }

    private void displayMatrix(){
        for (int i=0; i<this.matrix.length; i++){
            for (int j=0; j<matrix[i].length; j++){
                System.out.print(this.matrix[i][j]+" ");
            }
            System.out.println("");
        }
    }

    private void test(){
        this.displayMatrix();
        //System.out.println("\nCipher Text: "+this.encrypt());
        System.out.println("\nPlain Text: "+this.decrypt());
    }
}