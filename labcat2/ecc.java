import java.util.*;

class Point {
    int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isInfinity() {
        return x == -1 && y == -1;
    }

    @Override
    public String toString() {
        return isInfinity() ? "Infinity" : "(" + x + ", " + y + ")";
    }
}

class EllipticCurve {
    int a, b, p;

    public EllipticCurve(int a, int b, int p) {
        this.a = a;
        this.b = b;
        this.p = p;
    }

    private int modInverse(int k, int p) {
        k = k % p;
        for (int x = 1; x < p; x++) {
            if ((k * x) % p == 1) return x;
        }
        return -1; // No modular inverse exists
    }

    public Point addPoints(Point P, Point Q) {
        if (P.isInfinity()) return Q;
        if (Q.isInfinity()) return P;

        if (P.x == Q.x && (P.y + Q.y) % p == 0) {
            return new Point(-1, -1);
        }

        int lambda;
        if (P.x == Q.x && P.y == Q.y) {
            int numerator = (3 * P.x * P.x + a) % p;
            int denominator = modInverse(2 * P.y, p);
            lambda = (numerator * denominator) % p;
        } else {
            int numerator = (Q.y - P.y) % p;
            int denominator = modInverse(Q.x - P.x, p);
            lambda = (numerator * denominator) % p;
        }

        if (lambda < 0) lambda += p;

        int xr = (lambda * lambda - P.x - Q.x) % p;
        if (xr < 0) xr += p;
        int yr = (lambda * (P.x - xr) - P.y) % p;
        if (yr < 0) yr += p;

        return new Point(xr, yr);
    }

    public Point scalarMultiply(Point P, int n) {
        Point result = new Point(-1, -1);
        Point temp = P;

        while (n > 0) {
            if ((n & 1) == 1) {
                result = addPoints(result, temp);
            }
            temp = addPoints(temp, temp);
            n >>= 1;
        }
        return result;
    }

    public Point encrypt(Point M, Point P, Point G, int k) {
        Point C1 = scalarMultiply(G, k);
        Point C2 = addPoints(M, scalarMultiply(P, k));
        System.out.println("Ciphertext (C1, C2): " + C1 + ", " + C2);
        return new Point(C1.x * 100 + C2.x, C1.y * 100 + C2.y);
    }

    public Point decrypt(Point C1, Point C2, int n) {
        Point S = scalarMultiply(C1, n);
        int negS_y = (p - S.y) % p;
        Point negS = new Point(S.x, negS_y);
        Point M = addPoints(C2, negS);
        return M;
    }
} 

public class ecc {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the curve parameters (a, b) for E11(a, b):");
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int p = 11;

        EllipticCurve curve = new EllipticCurve(a, b, p);

        System.out.println("Enter the generator point G (x, y):");
        int gx = scanner.nextInt();
        int gy = scanner.nextInt();
        Point G = new Point(gx, gy);

        System.out.println("Enter private key n:");
        int n = scanner.nextInt();

        Point P = curve.scalarMultiply(G, n);
        System.out.println("Public key P = " + P);

        System.out.println("Enter message point M (x, y) to encrypt:");
        int mx = scanner.nextInt();
        int my = scanner.nextInt();
        Point M = new Point(mx, my);

        System.out.println("Enter random k for encryption:");
        int k = scanner.nextInt();

        Point C = curve.encrypt(M, P, G, k);
        System.out.println("Encrypted: " + C);

        System.out.println("Decrypting...");
        Point decryptedM = curve.decrypt(new Point(C.x / 100, C.y / 100), new Point(C.x % 100, C.y % 100), n);
        System.out.println("Decrypted message: " + decryptedM);

        scanner.close();
    }
}
