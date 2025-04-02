import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Scanner;

public class RSA_ChipperScheme{

    private BigInteger n, d, e;
    private int bitLength = 1024;
    private SecureRandom random = new SecureRandom();

    // Constructor to generate keys
    public RSA_ChipperScheme() {
        BigInteger p = BigInteger.probablePrime(bitLength / 2, random);
        BigInteger q = BigInteger.probablePrime(bitLength / 2, random);
        n = p.multiply(q);
        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        e = BigInteger.probablePrime(bitLength / 2, random);
        while (phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0) {
            e = e.add(BigInteger.ONE);
        }

        d = e.modInverse(phi);
    }

    // Encryption method
    public BigInteger encrypt(BigInteger message) {
        return message.modPow(e, n);
    }

    // Decryption method
    public BigInteger decrypt(BigInteger cipher) {
        return cipher.modPow(d, n);
    }

    public static void main(String[] args) {
        RSA_ChipperScheme rsa = new RSA_ChipperScheme();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Public Key (e, n): " + rsa.e + ", " + rsa.n);
        System.out.println("Private Key (d, n): " + rsa.d + ", " + rsa.n);

        System.out.print("Enter a plaintext message (integer format): ");
        BigInteger message = scanner.nextBigInteger();

        // Encrypt the message
        BigInteger encrypted = rsa.encrypt(message);
        System.out.println("Encrypted Message: " + encrypted);

        // Decrypt the message
        BigInteger decrypted = rsa.decrypt(encrypted);
        System.out.println("Decrypted Message: " + decrypted);

        scanner.close();
    }
}
