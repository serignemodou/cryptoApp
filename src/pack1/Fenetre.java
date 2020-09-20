package pack1;

import StoreKey.StoreKey;
import cipherFile.CipherAsymetric;
import cipherFile.CipherSymetric;
import decipherFile.DecryptFile;
import generateKey.GenerateKeyPaire;
import generateKey.GenerateSecreteKey;
import hashage.Empreinte;
import hashage.Signatur;
import saveKey.SaveToFile;

import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;
import java.security.*;
import java.util.ArrayList;

public class Fenetre extends JFrame {

    private JPanel container=new JPanel();
    private JComboBox combo,combo2,combo3,combo4,combo5;
    private JLabel label1,label2,label3,label4,label5,label6,label7,label8,label9,label10;
    private JTextField textField,textField2,textField3,textField4,textField5,textField6,textField7;
    private JButton button1,button2,buttonCrypt,buttonDecrypt,buttonHash,buttonSign,buttonDecryptSym,
            buttonDecryptAsy,buttonCertificat,buttonVerifySignature;
    private JRadioButton jRadioButton,jRadioButton1,jRadioButton2,jRadioButton3,jRadioButtonSecret,
    jRadioButtonPub,jRadioButtonPriv,jRadioButtonGetKeySecrete,jRadioButtonCipher,jRadioButtonSym,jRadioButtonAsym;
    private JFileChooser jFileChooser;
    private JCheckBox jCheckBox1,jCheckBox2;


    GenerateSecreteKey generateSecreteKey=new GenerateSecreteKey();
    GenerateKeyPaire generateKeyPaire=new GenerateKeyPaire();
    CipherSymetric cipherSymetric=new CipherSymetric();
    CipherAsymetric cipherAsymetric=new CipherAsymetric();
    Empreinte empreinte=new Empreinte();
    Signatur signatur=new Signatur();
    SaveToFile saveToFile=new SaveToFile();
    DecryptFile decryptFile=new DecryptFile();
    StoreKey storeKey=new StoreKey();



    private JFileChooser fileChooser=new JFileChooser();
    private JButton openButton,openButtonSecretKey,openButtonPublicKey,openButtonPrivateKey;
    private BufferedReader br;
    FileInputStream fis;
    FileInputStream fis_secrete_key;
    private File file;
    int returnVal;
    String currentLine;
    public String typeFile;
    private File fileOutput;
    FileOutputStream fos;
    public String fileSecretKey;


    public String algo;
    public int taille;
    public String algoAsym;
    public String fileNameHash;
    public String fileNameCipher;
    public String fileNameCipherSym;

    SecretKey secretKey =null;
    KeyPair keyPair=null;
    String fonctHach;
    String typeCipher;
    SecretKey secretKeyGenerated;


    public Fenetre(){
        this.setTitle("Crypto Software");
        this.setSize(500,670);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // container
        container.setBackground(Color.GRAY);
        container.setLayout(new BorderLayout());

        // format de données
        JPanel panFormat=new JPanel();
        panFormat.setBackground(Color.white);
        panFormat.setPreferredSize(new Dimension(220,60));
        panFormat.setBorder(BorderFactory.createTitledBorder("Data Format to Cipher"));
        combo=new JComboBox();
        combo.addItem("File");
        combo.addItem("text string");
        combo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if((e.getStateChange()==ItemEvent.SELECTED)){
                    typeFile=combo.getSelectedItem().toString();
                    System.out.println("type file :"+typeFile);
                    if(typeFile=="text string"){
                        openButton.setVisible(false);
                    }else
                        openButton.setVisible(true);

                }

            }
        });
        label1=new JLabel("Data Format");
        panFormat.add(label1);
        panFormat.add(combo);


        // fichier a chiffre
        JPanel panFile=new JPanel();
        panFile.setBackground(Color.white);
        panFile.setPreferredSize(new Dimension(220,60));
        panFile.setBorder(BorderFactory.createTitledBorder("Data"));
        jFileChooser=new JFileChooser();
        textField=new JTextField();

        //jRadioButtonFile=new JRadioButton("...");
        openButton=new JButton("select");
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==openButton){
                    returnVal=fileChooser.showOpenDialog(null);
                    if(returnVal==JFileChooser.APPROVE_OPTION){
                        file=fileChooser.getSelectedFile();
                        try {
                            fis=new FileInputStream(file);
                            System.out.println("fileInputStream "+fis.toString());
                        } catch (FileNotFoundException fileNotFoundException) {
                            fileNotFoundException.printStackTrace();
                        }
                        textField.setText(file.getAbsolutePath());
                        try {
                            br=new BufferedReader(new FileReader(file));
                            while ((currentLine=br.readLine())!=null){
                                //System.out.println(currentLine);
                                // recuperation du fichier a chiffrer
                            }
                        } catch (FileNotFoundException fileNotFoundException) {
                            fileNotFoundException.printStackTrace();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }
            }
        });


        textField=new JTextField();
        textField.setPreferredSize(new Dimension(120,25));
        label2=new JLabel();
        label2.setText("Data");
       // panFile.add(label2);
        panFile.add(textField);
      //  panFile.add(jFileChooser);
        panFile.add(openButton);



        // choix du type de chiffrement
        JPanel panChoice=new JPanel();
        panChoice.setBackground(Color.white);
        panChoice.setBorder(BorderFactory.createTitledBorder("Choice the Cipher type"));
        panChoice.setPreferredSize(new Dimension(445,55));
        //jRadioButtonAsym=new JRadioButton("Asymetric Cipher");
        //jRadioButtonSym=new JRadioButton("Symetric Cipher");
        jCheckBox1=new JCheckBox("Symetric Cipher");
        jCheckBox2=new JCheckBox("Asymetric Cipher");
        jCheckBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                typeCipher=jCheckBox1.getActionCommand();
                if(jCheckBox1.isSelected() && typeCipher=="Symetric Cipher"){
                    combo4.setVisible(false);
                    label4.setVisible(false);
                    jCheckBox2.setVisible(false);
                }else {
                    combo4.setVisible(true);
                    label4.setVisible(true);
                    jCheckBox2.setVisible(true);
                }
            }
        });
        jCheckBox2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                typeCipher=jCheckBox2.getActionCommand();
                if(jCheckBox2.isSelected() && typeCipher=="Asymetric Cipher"){
                    combo2.setVisible(false);
                    label3.setVisible(false);
                    jCheckBox1.setVisible(false);
                }else {
                    combo2.setVisible(true);
                    label3.setVisible(true);
                    jCheckBox1.setVisible(true);
                }
            }
        });
        panChoice.add(jCheckBox1);
        panChoice.add(jCheckBox2);






        // choix des agorithmes de chiffrements
        JPanel panCiper=new JPanel();
        panCiper.setBackground(Color.white);
        panCiper.setBorder(BorderFactory.createTitledBorder("Choice algorithm to Generate your key"));
        panCiper.setPreferredSize(new Dimension(445,60));
        combo2=new JComboBox();
        combo2.addItem("AES");
        combo2.addItem("DES");
        combo2.addItem("3DES");
        combo2.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if((e.getStateChange()==ItemEvent.SELECTED)){
                    algo=combo2.getSelectedItem().toString();
                    System.out.println("Algo: "+algo);
                }

            }
        });
        label3=new JLabel();
        //button1=new JButton();
        label5=new JLabel();
        combo3=new JComboBox();
        combo3.addItem(256);
        combo3.addItem(192);
        combo3.addItem(128);
        combo3.addItem(56);
        combo3.addItem(512);
        combo3.addItem(1024);
        combo3.addItem(2048);
        combo3.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if((e.getStateChange()==ItemEvent.SELECTED)){
                    taille= (int) combo3.getSelectedItem();
                    System.out.println("Taill: "+taille);
                }

            }
        });
        label3.setText("Sym Algo");
        label5.setText("Taille");
        combo4=new JComboBox();
        combo4.addItem("RSA");
        combo4.addItem("DSA");
        combo4.addItem("DH");
        combo4.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if((e.getStateChange()==ItemEvent.SELECTED)){
                    algoAsym=combo4.getSelectedItem().toString();
                    System.out.println("Algo: "+algoAsym);
                }
            }
        });
        combo5=new JComboBox();
        label6=new JLabel();
        label6.setText("Mode");
        combo5.addItem("ECB");
        combo5.addItem("CBC");
        combo5.addItem("CFB");
        combo5.addItem("OFB");
        label4=new JLabel();
        label4.setText("Asym Algo");
        panCiper.add(label3);
        panCiper.add(combo2);
        panCiper.add(label4);
        panCiper.add(combo4);
        panCiper.add(label6);
        panCiper.add(combo5);
        panCiper.add(label5);
        panCiper.add(combo3);



        // generate key
        JPanel panGenKey =new JPanel();
        panGenKey.setBackground(Color.white);
        panGenKey.setBorder(BorderFactory.createTitledBorder("Generate Cipher Key"));
        panGenKey.setPreferredSize(new Dimension(445,60));
        button1=new JButton();
        button2=new JButton();
        label8=new JLabel();
        label9=new JLabel();
        button2.setText("Generate Key Paire");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    keyPair=generateKeyPaire.GenerateKeyPair(algoAsym,taille);
                } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                    noSuchAlgorithmException.printStackTrace();
                }
            }
        });
        button1.setText("Generate Secrete Key");
        ArrayList<Character> valeur=new ArrayList<>();
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("algo: "+algo+"Taille :"+taille);
                try {
                   secretKey =generateSecreteKey.GenerateKey(algo,taille);
                } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                    noSuchAlgorithmException.printStackTrace();
                }

            }
        });
        panGenKey.add(button1);
        label8.setText("                 ");
        panGenKey.add(label8);
        panGenKey.add(button2);

        // panel for dilay key

        // display public key
        JPanel panKeyPub=new JPanel();
        panKeyPub.setPreferredSize(new Dimension(400,25));
        panKeyPub.setBackground(Color.white);
        textField2=new JTextField();
        textField2.setPreferredSize(new Dimension(305,25));
        jRadioButtonPub=new JRadioButton("public key ");
        jRadioButtonPub.setPreferredSize(new Dimension(85,25));
        jRadioButtonPub.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField2.setText(ByteHex.bytesToHex(keyPair.getPublic().getEncoded()));
                String fileName="C://Users/Dell/cipher/pubKey.txt";
                try {
                    saveToFile.savePubKeyToFile(textField2,fileName);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        //label6=new JLabel();

        //label6.setText("public  key");
        //panKeyPub.add(label6);
        panKeyPub.add(jRadioButtonPub);
        panKeyPub.add(textField2);

        // display private key
        JPanel panKeyPrivate=new JPanel();
        panKeyPrivate.setPreferredSize(new Dimension(400,25));
        panKeyPrivate.setBackground(Color.white);
        textField3=new JTextField();
        textField3.setPreferredSize(new Dimension(305,25));
        jRadioButtonPriv=new JRadioButton("private key");
        jRadioButtonPriv.setPreferredSize(new Dimension(85,25));
        jRadioButtonPriv.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField3.setText(ByteHex.bytesToHex(keyPair.getPrivate().getEncoded()));
                String fileName="C://Users/Dell/cipher/privateKey.txt";
                try {
                    saveToFile.savePrivKeyToFile(textField3,fileName);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        //label7=new JLabel();


        //label7.setText("private key");
       // panKeyPrivate.add(label7);
        panKeyPrivate.add(jRadioButtonPriv);
        panKeyPrivate.add(textField3);

        // display secrete key
        JPanel panSecreteKey=new JPanel();
        panSecreteKey.setPreferredSize(new Dimension(400,25));
        panSecreteKey.setBackground(Color.white);
        textField5=new JTextField();
        //label10=new JLabel();
        textField5.setPreferredSize(new Dimension(305,25));
        jRadioButtonSecret=new JRadioButton("secret key");
        jRadioButtonSecret.setPreferredSize(new Dimension(85,25));
        jRadioButtonSecret.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("val"+valeur.toString().replace(",",""));
               // textField5.setText(valeur.toString().replace(",",""));
                textField5.setText(ByteHex.bytesToHex(secretKey.getEncoded()));
                //fileSecretKey="C://Users/Dell/cipher/secretKey.pem";
                try {
                    //saveToFile.saveSecrKeyToFile(secretKey,fileSecretKey);
                    storeKey.KeyStore(secretKey);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                JOptionPane jop=new JOptionPane();
                String username=System.getProperty("user.home");
                fileSecretKey =username+"/cipher/secretKey.ks";
                String message="votre clé secrete est savaugarder dans le répertoire: \n";
                jop.showMessageDialog(null,message+fileSecretKey,"Attention",JOptionPane.WARNING_MESSAGE);

            }
        });
        //label10.setText("secrete key");
        //panSecreteKey.add(label10);
        panSecreteKey.add(jRadioButtonSecret);
        panSecreteKey.add(textField5);


        JPanel panKey=new JPanel();
        panKey.setBackground(Color.white);
        panKey.setBorder(BorderFactory.createTitledBorder("Display and Save your Key"));
        panKey.setPreferredSize(new Dimension(445,120));
        panKey.add(panSecreteKey);
        panKey.add(panKeyPub);
        panKey.add(panKeyPrivate);


        // hashage and hash value
        JPanel panHash=new JPanel();
        panHash.setBackground(Color.white);
        panHash.setBorder(BorderFactory.createTitledBorder("Hashage Function"));
        panHash.setPreferredSize(new Dimension(445,60));
        jRadioButton=new JRadioButton("MD5");
        jRadioButton.setSelected(true);
        jRadioButton1=new JRadioButton("SHA-1");
        jRadioButton2=new JRadioButton("SHA-256");
        jRadioButton3=new JRadioButton("SHA-512");
        jRadioButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               fonctHach=jRadioButton2.getActionCommand();
               System.out.println("fonctionHash "+fonctHach);

            }
        });
        jRadioButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fonctHach=jRadioButton1.getActionCommand();
                System.out.println("hashfonction "+fonctHach);
            }
        });
        jRadioButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fonctHach=jRadioButton3.getActionCommand();
                System.out.println("hashfonction "+fonctHach);
            }
        });
        jRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fonctHach=jRadioButton.getActionCommand();
                System.out.println("hashfonction "+fonctHach);
            }
        });
        panHash.add(jRadioButton);
        panHash.add(jRadioButton1);
        panHash.add(jRadioButton2);
        panHash.add(jRadioButton3);


        // Upload secrete file
        JPanel panUploadSecreteKey=new JPanel();
        panUploadSecreteKey.setBackground(Color.white);
        panUploadSecreteKey.setBorder(BorderFactory.createTitledBorder("Upload Secrete Key"));
        panUploadSecreteKey.setPreferredSize(new Dimension(220,60));
        textField4=new JTextField();
        textField4.setPreferredSize(new Dimension(120,25));
        //openButtonSecretKey=new JButton("select");
       // openButtonSecretKey
        jRadioButtonGetKeySecrete=new JRadioButton("upload KeySecrete");
        jRadioButtonGetKeySecrete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    secretKeyGenerated=storeKey.getSecreteKet();
                } catch (UnrecoverableEntryException unrecoverableEntryException) {
                    unrecoverableEntryException.printStackTrace();
                } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                    noSuchAlgorithmException.printStackTrace();
                } catch (KeyStoreException keyStoreException) {
                    keyStoreException.printStackTrace();
                }
            }
        });
        panUploadSecreteKey.add(jRadioButtonGetKeySecrete);
        panUploadSecreteKey.add(textField4);



        // upload public key
        JPanel panUploadPublicKey=new JPanel();
        panUploadPublicKey.setBackground(Color.white);
        panUploadPublicKey.setBorder(BorderFactory.createTitledBorder("Upload Private Key"));
        panUploadPublicKey.setPreferredSize(new Dimension(220,60));
        textField6=new JTextField();
        textField6.setPreferredSize(new Dimension(120,25));
        openButtonPrivateKey=new JButton("select");
        openButtonPrivateKey.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==openButtonPrivateKey){
                    returnVal=fileChooser.showOpenDialog(null);
                    if(returnVal==JFileChooser.APPROVE_OPTION){
                        file=fileChooser.getSelectedFile();
                        try {
                            fis_secrete_key=new FileInputStream(file);
                            System.out.println("fileInputStream "+fis_secrete_key.toString());
                        } catch (FileNotFoundException fileNotFoundException) {
                            fileNotFoundException.printStackTrace();
                        }
                        textField6.setText(file.getAbsolutePath());
                        try {
                            br=new BufferedReader(new FileReader(file));
                            while ((currentLine=br.readLine())!=null){
                                //System.out.println(currentLine);
                                // recuperation du fichier a chiffrer
                            }
                        } catch (FileNotFoundException fileNotFoundException) {
                            fileNotFoundException.printStackTrace();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }
            }
        });
        panUploadPublicKey.add(textField6);
        panUploadPublicKey.add(openButtonPrivateKey);

        // resultat and action
        JPanel panResultat=new JPanel();
        panResultat.setBackground(Color.white);
        panResultat.setBorder(BorderFactory.createTitledBorder("Action"));
        panResultat.setPreferredSize(new Dimension(445,95));
        buttonCrypt=new JButton("Symetric cipher");
        buttonDecrypt=new JButton("Asymetric cipher");
        buttonHash=new JButton("Calcul Hash");
        buttonSign=new JButton("Sign");
        buttonVerifySignature=new JButton("Verify Sign");
        buttonDecryptAsy=new JButton("Decrypt Asym");
        buttonDecryptSym=new JButton("Decrypt Sym");
        buttonCertificat=new JButton("Certificate");
        buttonCertificat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                storeKey.KeyStore(secretKey);
            }
        });


        buttonDecryptSym.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username=System.getProperty("user.home");
                String fileNameDecryptSym=username+"/cipher/dechiffreSym.txt";
                try {
                    decryptFile.DecryptFileWithSymetric(fis,secretKeyGenerated,fileNameDecryptSym);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (NoSuchPaddingException noSuchPaddingException) {
                    noSuchPaddingException.printStackTrace();
                } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                    noSuchAlgorithmException.printStackTrace();
                } catch (InvalidKeyException invalidKeyException) {
                    invalidKeyException.printStackTrace();
                }

            }
        });

        buttonCrypt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username=System.getProperty("user.home");
                fileNameCipherSym =username+"/cipher/cipherText.txt";
                cipherSymetric.Cipher(secretKey.getAlgorithm(), secretKey,fis, fileNameCipherSym);
            }
        });
        buttonDecrypt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username=System.getProperty("user.home");
                fileNameCipher=username+"/cipher/cipherTextAsy.txt";
                cipherAsymetric.CipherFile(keyPair.getPublic(),keyPair.getPrivate().getAlgorithm(),fis,fileNameCipher);
            }
        });
        buttonHash.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username=System.getProperty("user.home");
                fileNameHash=username+"/cipher/hashText.txt";
                empreinte.Hashage(fonctHach,fis,fileNameHash);
                System.out.println("username:"+System.getProperty("user.home"));
            }
        });
        buttonSign.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String f=fonctHach.replace("-","");
                //System.out.println("ffff"+f);
                String algo=f+"With"+keyPair.getPrivate().getAlgorithm();
                //String algo="SHA256WithDSA";
                try {
                    signatur.SignHash(algo,fis,keyPair);
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }

            }
        });
        buttonVerifySignature.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane jOptionPane=new JOptionPane();
                jOptionPane.showMessageDialog(null,"Votre fichier à été modifier","Attention",JOptionPane.WARNING_MESSAGE);
            }
        });
        panResultat.add(buttonCrypt);
        panResultat.add(buttonDecrypt);
        panResultat.add(buttonHash);
        panResultat.add(buttonSign);
        panResultat.add(buttonVerifySignature);
        panResultat.add(buttonDecryptAsy);
        panResultat.add(buttonDecryptSym);
        panResultat.add(buttonCertificat);


        JPanel content=new JPanel();
        content.setBackground(Color.gray);
        content.add(panFormat);
        content.add(panFile);
        content.add(panChoice);
        content.add(panCiper);
        content.add(panGenKey);
        content.add(panKey);
        content.add(panUploadSecreteKey);
        content.add(panUploadPublicKey);
        content.add(panHash);
        content.add(panResultat);



        this.getContentPane().add(content);
        this.setVisible(true);
    }

}
