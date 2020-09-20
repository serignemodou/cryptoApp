package hashage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class  Empreinte {
    FileOutputStream fos;
    DigestOutputStream dos;

    public Empreinte(){
    }

    public DigestOutputStream Hashage(String fonctHashage,FileInputStream fis,String fileName) {
        try {
            fos = new FileOutputStream(fileName);

            MessageDigest md = MessageDigest.getInstance(fonctHashage);
            DigestInputStream dis = new DigestInputStream(fis, md);
            dos = new DigestOutputStream(fos, md);

            byte[] buff = new byte[8];
            int length = dis.read(buff);
            while (length != -1) {
                md.update(buff, 0, length);
                byte[] emmp = md.digest(buff);
                emmp = md.digest(buff);
                dos.write(emmp);
                length = dis.read(buff);
            }
            dis.close();
            dos.close();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dos;
    }

    }
