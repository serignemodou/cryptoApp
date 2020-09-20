package generateKey;

import pack1.ByteHex;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

public class GenerateSecreteKey {
    //public String algorithme;
    //public int taille;
    public SecretKey secretKey=null;


    public GenerateSecreteKey(){

    }
    public SecretKey GenerateKey(String algorithme,int taille) throws NoSuchAlgorithmException {

            KeyGenerator keyGen=KeyGenerator.getInstance(algorithme);
            keyGen.init(taille);
            secretKey=keyGen.generateKey();
            System.out.println("secret secret"+secretKey);
            System.out.println(" "+ ByteHex.bytesToHex(secretKey.getEncoded()));
            //return ByteHex.bytesToHex(secretKey.getEncoded());
        return secretKey;
    }
}
