package hashage;

import java.io.*;
import java.security.*;

public class Signatur {
    FileOutputStream fos;
    Signature sign;

    public Signatur(){

    }
    public FileOutputStream SignHash(String algorithme, FileInputStream fis, KeyPair keyPair) throws FileNotFoundException {

        fos = new FileOutputStream("C://Users/Dell/cipher/sign.txt");
        BufferedInputStream bis=new BufferedInputStream(fis);
        BufferedOutputStream bos=new BufferedOutputStream(fos);
        try {

            sign =Signature.getInstance(algorithme);
            sign.initSign(keyPair.getPrivate());
            byte[] signature=new byte[250];
            byte[] buff=new byte[10];
            int i=bis.read(buff);
            while (i!=-1){
                sign.update(buff);
                signature= sign.sign();
                bos.write(signature);
                i=bis.read(buff);
            }
            bis.close();
            bos.close();
            // verify signature
            sign.initVerify(keyPair.getPublic());
            sign.update(buff);
            boolean resultat= sign.verify(signature);
            System.out.println("resultat"+resultat);

        } catch (NoSuchAlgorithmException | InvalidKeyException | IOException | SignatureException e) {
            e.printStackTrace();
        }
        return fos;
    }



}
