package decipherFile;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

public class DecryptFile {
    public FileOutputStream fos;
    CipherInputStream cis=null;
    InputStreamReader reader=null;
    public DecryptFile(){
    }
    public FileOutputStream DecryptFileWithSymetric(FileInputStream fis, SecretKey secretKey,String fileName) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        try {
        fos=new FileOutputStream(fileName);
        Cipher cipher= Cipher.getInstance(secretKey.getAlgorithm());
            byte[] buf={99, 126, 11, -10, -128, -58, -122, -16, -48, -82, 40, 69, 52, -121, 121, 88};
            IvParameterSpec ivspec = new IvParameterSpec(buf);
        cipher.init(Cipher.DECRYPT_MODE,secretKey,ivspec);
        cis=new CipherInputStream(fis,cipher);
        reader=new InputStreamReader(cis);
        BufferedReader br=new BufferedReader(reader);
        String xmlString="";
        String xmlData="";
        while ((xmlString= br.readLine())!=null){
            xmlData+=xmlString;
            fos.write(xmlData.getBytes());
        }
        } catch (IOException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        cis.close();
        fis.close();
        return fos;
    }
    public FileOutputStream DecryptFileWithAsymetric(FileInputStream fis, KeyPair keyPair, String fileName) throws FileNotFoundException {
        fos=new FileOutputStream(fileName);
        return fos;
    }
}
