import java.util.Scanner;

public class PlayFair {

    private String plainText;
    private String cipherText;
    private String key;
    private String alphabets[][];

    
    public PlayFair (String text,String key,boolean isEncryption){
       if (isEncryption){
        this.plainText = text;
       } else {
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
            PlayFair playFairE = new PlayFair(pText,kE,true);
            playFairE.displayMatrix();
            System.out.println("Cipher Text is: "+playFairE.encrypt());
            break;

            case 2://Decryption
            System.out.print("Enter a Cipher Text: ");
            String cText  = s.next();
            System.out.print("Enter a Key: ");
            String kD = s.next();
            PlayFair playFairD = new PlayFair(cText,kD,false);
            playFairD.displayMatrix();
            System.out.println("Plain Text is: "+playFairD.decrypt());
            break;

            default://Unkown Choice
            System.out.println("Unkown Choice!");
            System.exit(0);
            break;
        }

    }


    private void init(){
        //Initialize alphabets
        alphabets = new String[5][5];
        int textIndex = 0;
        int alphaIndex = 65;
        //Populate Alphabets with KEY characters
        for (int i=0; i<this.alphabets.length; i++){
            for (int j=0; j<alphabets[i].length; j++){
                if (textIndex<this.key.length()){
                    String character = key.charAt(textIndex++)+"";
                    if (!this.exists(character)){
                        if (character.equals("I")){
                            character = "I/J";
                        }
                        if (character.equals("J")){
                            if (!exists("I")){
                                character = "I/J";
                            }
                            j--;
                            if (j == -1){
                                j = 4;
                            }
                            if (j == 4){
                                i--;
                            }
                        }
                        alphabets[i][j] = character;
                    } else {
                        j--;
                        if (j == -1){
                            j = 4;
                        }
                        if (j == 4){
                            i--;
                        }
                    }
                } else {
                    break;
                }
            }
        }
        //Fill Rest characters in alphabetical order
        int startVal = 65;
        int finalVal = 90;
        for (int i=getStartIndex()[0]; i<alphabets.length; i++){
            for (int j=getStartIndex()[1]; j<alphabets[i].length; j++){

                if (startVal<=finalVal){
                    
            String alphabet = ((char)startVal++)+"";
                if (!exists(alphabet)){
                    if (alphabet.equals("I")){
                        alphabet = "I/J";
                    }
                    if (alphabet.equals("J")){
                        if (!exists("I")){
                            alphabet = "I/J";
                        }
                        j--;
                        if (j == -1){
                            j = 4;
                        }
                        if (j == 4){
                            i--;
                        }
                    }
                    alphabets[i][j] = alphabet;
                } else{
                    j--;
                    if (j == -1){
                        j = 4;
                    }
                    if (j == 4){
                        i--;
                    }
                }
                }

            }
        }
    }
    

    private  void test (){

        

        System.out.println(this.decrypt());

        //System.out.println(this.getStartIndex()[0]);
        //System.out.println(this.getStartIndex()[1]);

    }

    private void displayMatrix(){
        System.out.println("\nPlay Fair Matrix");

        for (int i=0; i<this.alphabets.length; i++){
            for (int j=0; j<this.alphabets[i].length; j++){
                System.out.print(this.alphabets[i][j]+"\t");
            }
            System.out.println("\n");
        }
        System.out.println("");
    }

    private boolean exists(String x){
        String text = "";
        for (int i=0; i<this.alphabets.length; i++){
            for (int j=0; j<this.alphabets.length; j++){
                text += alphabets[i][j];
            }
        }
        return  text.indexOf(x)!=-1;
    }

    private int[] getStartIndex(){
        int row = -1,col = -1;
        for (int i=0; i<this.alphabets.length; i++){
            for (int j=0; j<this.alphabets[i].length; j++){
                if (this.alphabets[i][j] == null){
                    row = i;
                    col = j;
                    break;
                }
            }
            if (row != -1 || col != -1){
                break;
            }
        }
        int result[] = new int[2];
        result[0] = row;
        result[1] = col;
        return result;
    }

    private int[] getPosition (String alphabet){
        int row = -1;
        int col = -1;
        for (int i=0; i<this.alphabets.length; i++){
            for (int j=0; j<this.alphabets[i].length; j++){
                if (this.alphabets[i][j].equals(alphabet)){
                    row = i;
                    col = j;
                    break;
                }
            }
            if (col != -1 || row != -1){
                break;
            }
        }
        int result[] = new int[2];
        result[0] = row;
        result[1] = col;
        return result;
    }

    //For Encryption
    private String encrypt(){
        //For storing Cipher Text
        String cText = "";
        //Check if number of characters is not even, then append X
        if (this.plainText.length()%2 != 0){
            plainText += "X";
        }
        //Iterate in groups of two characters from the begining
        for (int i=0; i<this.plainText.length(); i+=2){
            String alpha1 = this.plainText.charAt(i)+"";
            String alpha2 = this.plainText.charAt(i+1)+"";
            int row1 = this.getPosition(alpha1)[0];
            int col1 = this.getPosition(alpha1)[1];
            int row2 = this.getPosition(alpha2)[0];
            int col2 = this.getPosition(alpha2)[1];
            //Same row
            if (row1 == row2){
                //Encrypt alpha1
                col1++;
                col1 %= 5;
                cText += alphabets[row1][col1];
                //Encrypt alpha2
                col2++;
                col2 %= 5;
                cText += alphabets[row2][col2];

            } else if (col1 == col2){
                //Same column
                //Encrypt alpha1
                row1++;
                row1 %= 5;
                cText += alphabets[row1][col1];
                //Encrypt alpha2
                row2++;
                row2 %= 5;
                cText += alphabets[row2][col2];
            } else {
                //Diagonal
                cText += alphabets[row2][col1];
                cText += alphabets[row1][col2];
            }
        }
        return cText;
    }

    //For Decryption
    private String decrypt(){
        //For storing Plain Text
        String pText = "";
        //Iterate in groups of two characters from the begining
        for (int i=0; i<this.cipherText.length(); i+=2){
            String alpha1 = this.cipherText.charAt(i)+"";
            String alpha2 = this.cipherText.charAt(i+1)+"";
            int row1 = this.getPosition(alpha1)[0];
            int col1 = this.getPosition(alpha1)[1];
            int row2 = this.getPosition(alpha2)[0];
            int col2 = this.getPosition(alpha2)[1];
            //Same row
            if (row1 == row2){
                //Decrypt alpha1
                col1--;
                if (col1<0){
                    col1 += 5;
                }
                pText += alphabets[row1][col1];
                //Decrypt alpha2
                col2--;
                if (col2<0){
                    col2 += 5;
                }
                pText += alphabets[row2][col2];

            } else if (col1 == col2){
                //Same column
                //Decrypt alpha1
                row1--;
                if (row1 < 0){
                    row1 += 5;
                }
                pText += alphabets[row1][col1];
                //Decrypt alpha2
                row2--;
                if (row2 < 0){
                    row2 += 5;
                }
                pText += alphabets[row2][col2];
            } else {
                //Diagonal
                pText += alphabets[row2][col1];
                pText += alphabets[row1][col2];
            }
        }
        return pText;
    }
}