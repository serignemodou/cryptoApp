package cipherFile;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

public class CipherAsymetric {
    FileOutputStream fileOutputStream;
    Cipher cipher=null;
    CipherInputStream cipherInputStream=null;

    public CipherAsymetric(){

    }

    public FileOutputStream CipherFile(PublicKey publicKey, String algorithme, FileInputStream fileInputStream, String fileNameCipher){
        try {
            fileOutputStream=new FileOutputStream(fileNameCipher);
            cipher=Cipher.getInstance(algorithme);
            cipher.init(Cipher.ENCRYPT_MODE,publicKey);
            cipherInputStream=new CipherInputStream(fileInputStream,cipher);
            byte[] buf =new byte[256];
            int i= 0;
            i = cipherInputStream.read(buf);
            while(i!=-1) {
                    fileOutputStream.write(buf, 0, i);
                    i=cipherInputStream.read(buf);
            }
            cipherInputStream.close();
            fileInputStream.close();

        } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            }
            catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
      return fileOutputStream;
    }
}
