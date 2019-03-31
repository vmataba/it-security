

import java.util.Scanner;

public class Hill {
 
    private int key[][];
    private String plainText;
    private String cipherText;
    private char[] alphabets;
   
    public Hill (String text,boolean isEncryption){
      if (isEncryption){
        this.plainText = text;
      } else {
        this.cipherText = text;
      }
      this.init();
    }

    private void setKey(int[][] key){
        this.key = key;
    }
    
     private void init (){
      this.alphabets = new char [26];
     
      for (int i=65;i <=90; i++){
        this.alphabets[i-65] = (char)i;
      }
    }
   
    public static void main (String[] args){

        Scanner s = new Scanner(System.in);
        System.out.print("Press 1 for E ncryption 2 for Decryption: ");
        int choice = s.nextInt();
        switch(choice){
            case 1://Encryption
            System.out.print("Enter Plain Text: ");
            String pText = s.next();
            Hill hE = new Hill(pText,true);
            hE.setKey(hE.readMatrix(s));
            hE.displayMatrix();
            System.out.println("Cipher Text is: "+hE.encrypt());
            break;

            case 2://Decryption
            System.out.print("Enter Cipher Text: ");
            String cText = s.next();
            Hill hD = new Hill(cText,false);
            hD.setKey(hD.readMatrix(s));
            hD.displayMatrix();
            System.out.println("Plain Text is: "+hD.decrypt());

            break;

            default://Unkown Choice

            System.out.println("Unkown Choice");
            System.exit(0);

            break;
        }
      
    }
    
      private String encrypt (){
      String cText = "";
      if (this.plainText.length()%2!=0){
          this.plainText += "X";
      }
      for (int i=0; i<this.plainText.length()-1; i+=2){
      char pAlpha1 = this.plainText.charAt(i);
      char pAlpha2 = this.plainText.charAt(i+1);
      int pIndex1 = new String(this.alphabets).indexOf(pAlpha1+"");
      int pIndex2 = new String(this.alphabets).indexOf(pAlpha2+"");
      int cIndex1 = (key[0][0]*pIndex1 + key[0][1]*pIndex2)%26;
      int cIndex2 = (key[1][0]*pIndex1 + key[1][1]*pIndex2)%26;
      char cAlpha1 = alphabets[cIndex1];
      char cAlpha2 = alphabets[cIndex2];
      cText += cAlpha1+""+cAlpha2;  
      }
      return cText;
    }

    private String decrypt(){
        String pText = "";
        int inverseMode26 = this.findInverseMod26();
        int oldFirstItem = this.key[0][0]; 
        this.key[0][0] = this.key[1][1];
        this.key[0][1] = this.key[0][1]*-1;
        this.key[1][0] = this.key[1][0]*-1;
        this.key[1][1] = oldFirstItem;
        for (int i=0; i<this.key.length; i++){
            for (int j=0; j<this.key[i].length; j++){
                this.key[i][j] *= inverseMode26;
                if (this.key[i][j]<0){
                    this.key[i][j] = this.getPositiveNumber(this.key[i][j]);
                }
                this.key[i][j] %= 26;
            }
        }

        for (int i=0; i<this.cipherText.length()-1; i+=2){
            char cAlpha1 = this.cipherText.charAt(i);
            char cAlpha2 = this.cipherText.charAt(i+1);
            int cIndex1 = new String(this.alphabets).indexOf(cAlpha1+"");
            int cIndex2 = new String(this.alphabets).indexOf(cAlpha2+"");
            int pIndex1 = (this.key[0][0]*cIndex1 + this.key[0][1]*cIndex2)%26;
            int pIndex2 = (this.key[1][0]*cIndex1 + this.key[1][1]*cIndex2)%26;
            char pAlpha1 = alphabets[pIndex1];
            char pAlpha2 = alphabets[pIndex2];
            pText += pAlpha1+""+pAlpha2;  
            }

        return pText;
    }

    private int findInverseMod26(){
        int mainDiagonal = this.key[0][0]*this.key[1][1];
        int leadingDiagonal = this.key[1][0]*this.key[0][1];
        int determinant =  mainDiagonal - leadingDiagonal;
        int result = -1;
        for (int i=1; i<=26-1; i++){
            if ((determinant*i)%26 == 1){
                result = i;
                break;
            }
        }
        return result;
    }

    private static int getPositiveNumber(int key){
        if (key>0)
        return key;
        else return getPositiveNumber(key+26);
    }

    private int[][] readMatrix(Scanner s){
        int mat[][] = new int[2][2];
        System.out.println("\nReading Key Matrix ...");
        System.out.print("Row 1 Col 1 : ");
        mat[0][0] = s.nextInt();
        System.out.print("Row 1 Col 2 : ");
        mat[0][1] = s.nextInt();
        System.out.print("Row 2 Col 1 : ");
        mat[1][0] = s.nextInt();
        System.out.print("Row 2 Col 2 : ");
        mat[1][1] = s.nextInt(); 
        return mat; 
    }

    private void displayMatrix(){
        System.out.println ("\nKey Array...");
        for (int i=0; i < key.length; i++){
          for (int j=0; j < key [i].length;j++){
            System.out.print (key [i][j]+"\t");
          }
          System.out.println ("\n");
        }
    }    
  }
  