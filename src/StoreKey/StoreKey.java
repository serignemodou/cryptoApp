package StoreKey;


import javax.crypto.SecretKey;
import java.io.*;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

public class StoreKey {

    char[] keyStorePassword = "123abc".toCharArray();
    char[] keyPassword = "789xyz".toCharArray();
    KeyStore.ProtectionParameter keySecretePassword =
            new KeyStore.PasswordProtection(keyPassword);
    KeyStore keyStore = null;

    public StoreKey (){

    }

    public void KeyStore(SecretKey secretKey){


        try {

            String username=System.getProperty("user.home");
            keyStore=KeyStore.getInstance("PKCS12");
            keyStore.load(null,null);
            OutputStream keyStoreData = new FileOutputStream("keystore.ks");
            keyStore.store(keyStoreData,keyStorePassword);
            KeyStore.SecretKeyEntry secretKeyEntry=new KeyStore.SecretKeyEntry(secretKey);
            keyStore.setEntry("keySecreteAlias",secretKeyEntry,keySecretePassword);

        } catch (FileNotFoundException | KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public SecretKey getSecreteKet() throws UnrecoverableEntryException, NoSuchAlgorithmException, KeyStoreException {
        KeyStore.SecretKeyEntry ksc= (KeyStore.SecretKeyEntry) keyStore.getEntry("keySecreteAlias",keySecretePassword);
        //String keySecrete=ByteHex1.bytesToHex(ksc.getSecretKey().getEncoded());
        return ksc.getSecretKey();
    }



}
