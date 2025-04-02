import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Scanner;

public class MD5 {
    public static String generateMAC_MD5(String message, String key) throws Exception {
        Mac mac = Mac.getInstance("HmacMD5");
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "HmacMD5");
        mac.init(secretKey);
        byte[] macBytes = mac.doFinal(message.getBytes());
        return Base64.getEncoder().encodeToString(macBytes);
    }
    
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter message: ");
        String message = scanner.nextLine();
        System.out.print("Enter key: ");
        String key = scanner.nextLine();
        scanner.close();
        
        String mac = generateMAC_MD5(message, key);
        System.out.println("MD5 MAC: " + mac);
    }
}

/*
Test Cases:
Input 1:
Enter message: Hello, Secure World!
Enter key: securekey123
Expected Output:
MD5 MAC: (Base64 encoded HMAC-MD5 string)

Input 2:
Enter message: TestMessage123
Enter key: MySecretKey
Expected Output:
MD5 MAC: (Base64 encoded HMAC-MD5 string)
*/
