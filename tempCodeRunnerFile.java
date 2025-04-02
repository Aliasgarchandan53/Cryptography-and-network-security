import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Scanner;

public class AES_Ciph {

    // Method to generate an AES key with a specified size
    public static SecretKey generateKey(int keySize) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(keySize); // Key size: 128, 192, or 256 bits
        return keyGen.generateKey();
    }

    // Method to encrypt a plain text using AES
    public static String encrypt(String plainText, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Method to decrypt a cipher text using AES
    public static String decrypt(String cipherText, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter key size (128, 192, or 256): ");
            int keySize = scanner.nextInt();
            scanner.nextLine(); 

            SecretKey secretKey = generateKey(keySize);

            System.out.println("Generated AES Key (Base64): " + Base64.getEncoder().encodeToString(secretKey.getEncoded()));

            System.out.println("Enter plaintext: ");
            String plainText = scanner.nextLine();

            // Encrypt the plaintext
            String cipherText = encrypt(plainText, secretKey);

            // Decrypt the ciphertext
            String decryptedText = decrypt(cipherText, secretKey);

            System.out.println("Plaintext: " + plainText);
            System.out.println("Encrypted Text: " + cipherText);
            System.out.println("Decrypted Text: " + decryptedText);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
