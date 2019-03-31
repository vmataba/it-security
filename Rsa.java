import java.util.*;

public class Rsa{

    private int p;
    private int q;
    private int n;
    private int fn;
    private int e;
    private int d;
    private String plainText;
    private String cipherText; 
    private char[] alphabets;

    public Rsa (String text,int p,int q,int e,boolean isEncryption){
        if (isEncryption){
            this.plainText = text;
        } else{
            this.cipherText = text;
        }
        this.p = p;
        this.q = q;
        this.n = this.p * this.q;
        this.fn = (this.p-1)*(this.q-1);
        this.e = e;
        this.d = this.findD();
        prepAlphabets();
    }

    public static void main(String[] args) {
        Rsa rsa = new Rsa("K",3,5,3,false);
        rsa.displayValues();
        System.out.println(rsa.decrypt());
    }

    private boolean validateE(){
        return this.fn % e == 1;
    }


    private int findD(){
        int result = -1;
        for (int i=1; i<=this.fn-1; i++){
            if ((i*this.e)%this.fn == 1){
                result = i;
                break;
            }
        }
        return result;
    }

    private String encrypt(){
        String cText = "";
        for (int i=0; i<this.plainText.length(); i++){
            int pVal = new String(this.alphabets).indexOf(this.plainText.charAt(i));
            int cVal = ((int)Math.pow(pVal,this.e))%this.n;
            char cAlpha = this.alphabets[cVal];
            cText += cAlpha;

        }
        return cText;
    }

    private String decrypt(){
        String pText = "";
        for (int i=0; i<this.cipherText.length(); i++){
            int cVal = new String(this.alphabets).indexOf(this.cipherText.charAt(i));
            int pVal = ((int)Math.pow(cVal,this.p))%this.n;
            char pAlpha = alphabets[pVal];
            pText += pAlpha;
        }
        return pText;
    }

    private void prepAlphabets(){
        alphabets = new char[27];
        int key = 1;
        for (int i=65; i<=90; i++){
            alphabets[i-64] = (char)i;
        }
    }

    private void displayValues(){
        System.out.println("p: "+this.p);
        System.out.println("q: "+this.q);
        System.out.println("n: "+this.n);
        System.out.println("fn: "+this.fn);
        System.out.println("e: "+this.e);
        System.out.println("d: "+this.d);
        System.out.println("Public Key: {"+this.e+","+this.n+"}");
        System.out.println("Private Key: {"+this.d+","+this.n+"}");
    }
}