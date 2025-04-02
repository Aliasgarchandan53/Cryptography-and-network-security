import java.util.*;

class ElCyS {
    int p, g, d, e, k, y1, y2; // Encryption keys
    int a, m, q, xa, ya, s1, s2; // Signature keys
    Random rand = new Random();

    ElCyS(int p, int g) {
        this.p = p;
        this.g = g;
    }

    // Function to compute (a^b) % p using modular exponentiation
    long power_mod(long a, long b, long p) {
        long res = 1;
        a = a % p;
        while (b > 0) {
            if ((b & 1) == 1)
                res = (res * a) % p;
            a = (a * a) % p;
            b >>= 1;
        }
        return res;
    }

    // Function to send public keys
    void send_keys_x() {
        d = rand.nextInt((p - 2) - 2 + 1) + 2; // Private key d
        e = (int) power_mod(g, d, p); // Public key e
        System.out.printf("X sends {p=%d, g=%d, e=%d}\n", p, g, e);
    }

    // Encryption function
    void encrypt_yx(int M) {
        k = rand.nextInt((p - 2) - 2 + 1) + 2;
        y1 = (int) power_mod(g, k, p);
        y2 = (M * (int) power_mod(e, k, p)) % p;
        System.out.printf("Y sends {y1=%d, y2=%d}\n", y1, y2);
    }

    // Decryption function
    void decrypt_xy() {
        int y1_d = (int) power_mod(y1, d, p);
        int inv = (int) power_mod(y1_d, p - 2, p); // Modular inverse using Fermat's theorem
        int res = (y2 * inv) % p;
        System.out.println("Message M = " + res);
    }

    // Extended Euclidean Algorithm to find modular inverse
    int mod_inv(int a, int b) {
        int b0 = b, x0 = 0, x1 = 1;
        if (b == 1) return 1;

        while (a > 1) {
            int q = a / b;
            int t = b;
            b = a % b;
            a = t;
            t = x0;
            x0 = x1 - q * x0;
            x1 = t;
        }
        if (x1 < 0) x1 += b0;
        return x1;
    }

    // Signature creation function
    void create_sign(int m, int q1, int a1, int xa1) {
        q = q1;
        a = a1;
        xa = xa1;

        ya = (int) power_mod(a, xa, q);
        System.out.printf("A's public key ya=%d\n", ya);

        // Select k such that gcd(k, q-1) == 1
        do {
            k = rand.nextInt(q - 1) + 1; // Ensure 1 ≤ k ≤ q-1
        } while (gcd(k, q - 1) != 1);

        s1 = (int) power_mod(a, k, q);
        int kinv = mod_inv(k, q - 1); // Compute k^-1 mod (q-1)

        s2 = (kinv * (m - xa * s1)) % (q - 1);
        if (s2 < 0) s2 += (q - 1); // Ensure s2 is positive

        System.out.printf("Signature: s1=%d, s2=%d\n", s1, s2);
    }

    // Function to verify signature
    void verify_sign(int m) {
        int v1 = (int) power_mod(a, m, q);
        int t1 = (int) power_mod(ya, s1, q);
        int t2 = (int) power_mod(s1, s2, q);
        int v2 = (t1 * t2) % q;

        if (v1 == v2) System.out.println("Signature Verified ");
        else System.out.println("Signature NOT Verified ");
    }

    // Function to compute GCD
    int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }
}

public class elgamal {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        ElCyS elg = new ElCyS(13, 2);

        elg.send_keys_x();
        elg.encrypt_yx(4);
        elg.decrypt_xy();
        elg.create_sign(14, 19, 10, 16);
        elg.verify_sign(14);

        scan.close();
    }
}
