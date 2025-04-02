import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Scanner;

public class Des_ciph {
    
    // Method to perform DES encryption
    public static String encrypt(String plainText, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Method to perform DES decryption
    public static String decrypt(String encryptedText, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        try {
            // Generate a 64-bit DES key
            KeyGenerator keyGen = KeyGenerator.getInstance("DES");
            keyGen.init(56); // DES key size is 56 bits (64-bit block size, 8 bits for parity)
            SecretKey secretKey = keyGen.generateKey();

            // Example plaintext inputs
            System.out.println("Enter plaintext: ");
            String plainText= scan.next();

            // Encrypt the plaintext
            String encryptedText = encrypt(plainText, secretKey);

            // Decrypt the ciphertext
            String decryptedText = decrypt(encryptedText, secretKey);

            // Display results
            System.out.println("Key (Base64): " + Base64.getEncoder().encodeToString(secretKey.getEncoded()));
            System.out.println("Plaintext : " + plainText);
            System.out.println("Encrypted Text : " + encryptedText);
            System.out.println("Decrypted Text : " + decryptedText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
