package cipherFile;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


public class CipherSymetric {
    public FileOutputStream fileOutputStream;
    Cipher cipher= null;
    CipherInputStream cipherInputStream=null;


    public CipherSymetric(){

    }

    public FileOutputStream Cipher(String algorithm,SecretKey secretKey, FileInputStream fileInputStream,String fileNameCipherSym){


        try {
            fileOutputStream=new FileOutputStream(fileNameCipherSym);
            cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE,secretKey);
             cipherInputStream=new CipherInputStream(fileInputStream,cipher);
            byte[] buf=new byte[8];
            int i=cipherInputStream.read(buf);
            while (i!=-1){
                fileOutputStream.write(buf,0,i);
                i=fileInputStream.read(buf);
            }
            cipherInputStream.close();
            fileOutputStream.close();
        } catch (InvalidKeyException | IOException e) {
            e.printStackTrace();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        return fileOutputStream;
    }
}
