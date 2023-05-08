import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class data{
   public static void main(String args[]){
    try{
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
        SecretKey symmetricKey = generateKey();
        IvParameterSpec iv = generateIv();

        String data = buffer.readLine();
        buffer.close();

        byte[] cipherText = encrypt(data, symmetricKey, iv);

        System.out.println("Encrypted message: "+ cipherText);

        System.out.println("Key" + symmetricKey.toString());
        System.out.println("iv: "+ iv.toString());
        String decryptedKey = decrypt(cipherText, symmetricKey, iv);

        System.out.println("Original password: "+ decryptedKey);
    }catch(Exception ex){
        System.out.println("Error in encryption/decription");
    }
    
   }
   static public SecretKey generateKey() throws NoSuchAlgorithmException {
    KeyGenerator gen = KeyGenerator.getInstance("AES");
    gen.init(256);
    return gen.generateKey();
   }
   static public IvParameterSpec generateIv(){
    byte[] data = new byte[16];
    SecureRandom secureRandom = new SecureRandom();
    secureRandom.nextBytes(data);
    return new IvParameterSpec(data);
   }
   static public byte[] encrypt(String input, SecretKey key, IvParameterSpec iv) throws Exception{
    Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
    cipher.init(Cipher.ENCRYPT_MODE, key, iv);
    return cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));
   }
   static public String decrypt(byte[] cipherText, SecretKey key, IvParameterSpec iv) throws Exception{
    Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
    cipher.init(Cipher.DECRYPT_MODE, key, iv);
    byte[] plainText = cipher.doFinal(cipherText);
    return new String(plainText);
   }
}