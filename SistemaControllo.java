import java.util.*;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import java.io.*;

public class SistemaControllo {
    private ArrayList<cella> registro;
    private ArrayList<SecretKey> registroChiavi;
    private ArrayList<IvParameterSpec> registroIV;
    ObjectInputStream inputBin;
    ObjectOutputStream outputBin;

    private int current = 0;


    public SistemaControllo() throws NoSuchAlgorithmException{
        registro = new ArrayList<>();
        registroChiavi = new ArrayList<>();
        registroIV = new ArrayList<>();

    }
    public void insertNode(String name, String password,String rePassword) throws NoSuchAlgorithmException, Exception{
        cella c = null;
        boolean stop = false;
        try{
            if(password.compareTo(rePassword) == 0){
                SecretKey secretKey = this.generateKey();
                IvParameterSpec iv = this.generateIv();
                byte[] encryptedText = this.encrypt(rePassword, secretKey, iv);

                c = new cella(name, encryptedText);
                registro.add(current,c);
                registroChiavi.add(current,secretKey);
                registroIV.add(current,iv);
                stop = true;
                current++;
                //manca l'ordinamento
                
            } 
        }catch(NoSuchAlgorithmException ex){
            throw ex;
        }catch(Exception ex){
            throw ex;
        }
        
    }

    //metodo per eliminare un nodo//manca l'ordinamento

    public void deleteNode(int i){
        registro.remove(i);
        registroChiavi.remove(i);
        registroIV.remove(i);
        if(current-- < 0){
            current = 0;
        }
    }

    //metodo per verificare la libertà di una cella


    public boolean isFree(int i){
        if(registro.get(i) != null){
            if(registroChiavi.get(i) != null){
                if(registroIV.get(i) != null){
                    return true;
                }
            }   
        }
        return false;
    }

    //metodo per ottenere una cella

    public cella getCell(int i){
        if(registro.get(i) != null){
            return registro.get(i);
        }
        return new cella("null",new String("null").getBytes());
    }

    //metodo per il salvataggio di dati su diversi file, implementare la serializzazione

    public void serializeInfo(String destinationFile_1, String destinationFile_2,String destinationFile_3 ) throws IOException{
        try{
            if(!destinationFile_1.equals(destinationFile_2)){
                if(!destinationFile_2.equals(destinationFile_3)){
                    if(!destinationFile_3.equals(destinationFile_1)){
                        outputBin = new ObjectOutputStream(new FileOutputStream(destinationFile_1));
                        outputBin.writeObject(registro);
                        outputBin.close();
                        outputBin = new ObjectOutputStream(new FileOutputStream(destinationFile_2));
                        outputBin.writeObject(registroChiavi);
                        outputBin.close();
                        outputBin = new ObjectOutputStream(new FileOutputStream(destinationFile_3));
                        outputBin.writeObject(registroIV);
                        outputBin.close();
                    }
                }
            }
        }catch(IOException ex){
            throw ex;
        }
    }

    //metodo per recuperare la grandezza del vettore

    public int getSize(){
        return registro.size();
    }

    //metodo per caricare dei dati dai file separati, implementare la serializzazione
    public void loadData(String destinationFile_1, String destinationFile_2,String destinationFile_3) throws ClassNotFoundException,FileNotFoundException,IOException{
        try{
            if(!destinationFile_1.equals(destinationFile_2)){
                if(!destinationFile_2.equals(destinationFile_3)){
                    if(!destinationFile_3.equals(destinationFile_1)){
                        inputBin = new ObjectInputStream(new FileInputStream(destinationFile_1));
                        if(inputBin.readObject() instanceof cella){
                            registro = (ArrayList<cella>)inputBin.readObject();
                        }
                        inputBin.close();
                        inputBin = new ObjectInputStream(new FileInputStream(destinationFile_2));
                        if(inputBin.readObject() instanceof SecretKey){
                            registroChiavi = (ArrayList<SecretKey>)inputBin.readObject();
                        }
                        
                        inputBin.close();
                        inputBin = new ObjectInputStream(new FileInputStream(destinationFile_3));
                        if(inputBin.readObject() instanceof IvParameterSpec){
                            registroIV = (ArrayList<IvParameterSpec>)inputBin.readObject();
                        }
                        inputBin.close();
                    }
                }
            }
            current = 0;

        }catch(ClassNotFoundException ex){
            throw ex;
        }
        catch(FileNotFoundException ex){
            throw ex;
        }catch(IOException ex){
            throw ex;
        }
    }

    //metodo per decriptare un nodo
    public String decryptNode(int i) throws Exception{
        SecretKey secretKey = registroChiavi.get(i);
        IvParameterSpec iv = registroIV.get(i);
        byte[] text = registro.get(i).getEncryptedPassword();
        String decryptedKey = null;
        try{
            decryptedKey = decrypt(text, secretKey, iv);
        }catch(Exception ex){
            throw ex;
        }
        return decryptedKey;
    }

    //Sistema di decriptazione e criptazione password

    private SecretKey generateKey() throws NoSuchAlgorithmException {
        KeyGenerator gen = KeyGenerator.getInstance("AES");
        gen.init(256);
        return gen.generateKey();
    }
    private IvParameterSpec generateIv(){
        byte[] data = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(data);
        return new IvParameterSpec(data);
    }
    private byte[] encrypt(String input, SecretKey key, IvParameterSpec iv) throws Exception{
        Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        return cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));
    }
    private String decrypt(byte[] cipherText, SecretKey key, IvParameterSpec iv) throws Exception{
        Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] plainText = cipher.doFinal(cipherText);
        return new String(plainText);
    }
    public String byteToEx(byte[] e){
        StringBuilder hexString = new StringBuilder(2 * e.length);
        for(int i=0;i<e.length;i++){
            String hex = Integer.toHexString(0xff & e[i]);
            if(hex.length() == 1){
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
    @Override

    //toString della lista
    public String toString(){
        String man = "";
        for(int i=0;i<registro.size();i++){
            man += "\n\n----------------------\n"+registro.get(i).toString()+"\ncella: "+i+"\n----------------------\n\n";
        }
        return man;
    }
}
