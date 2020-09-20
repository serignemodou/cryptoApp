package generateKey;

import pack1.ByteHex;

import java.security.*;

public class GenerateKeyPaire {
    SecureRandom secureRandom=new SecureRandom();
    byte bytes[]=new byte[20];
    //secureRandom.nextBytes[bytes];
    String pubKey,privKey;
    PrivateKey privateKey=null;

    public GenerateKeyPaire(){

    }

    public KeyPair GenerateKeyPair(String algorithme, int taille) throws NoSuchAlgorithmException{
        KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance(algorithme);
        keyPairGenerator.initialize(taille,secureRandom);
        KeyPair keyPair=keyPairGenerator.generateKeyPair();
        // private key
        privateKey=keyPair.getPrivate();
        privKey= ByteHex.bytesToHex(privateKey.getEncoded());

        // public key
        PublicKey publicKey=keyPair.getPublic();
        pubKey=ByteHex.bytesToHex(publicKey.getEncoded());

        System.out.println("priv"+privKey+"\npub"+pubKey);
        return keyPair;
        //return pubKey+" "+privKey;
    }
}
