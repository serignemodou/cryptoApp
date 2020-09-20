package saveKey;

import javax.crypto.SecretKey;
import javax.swing.*;
import java.io.FileOutputStream;

public class SaveToFile {
    public SaveToFile(){
    }

    public void saveSecrKeyToFile(SecretKey secretKey, String fileName) throws Exception{
        FileOutputStream fos=new FileOutputStream(fileName,true);
        fos.write(secretKey.getEncoded());
    }

    public void savePubKeyToFile(JTextField jTextField, String fileName) throws Exception{
        FileOutputStream fos=new FileOutputStream(fileName,true);
        fos.write(jTextField.getText().getBytes());
    }

    public void savePrivKeyToFile(JTextField jTextField, String fileName) throws Exception{
        FileOutputStream fos=new FileOutputStream(fileName,true);
        fos.write(jTextField.getText().getBytes());
    }
}
