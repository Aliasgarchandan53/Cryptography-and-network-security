import java.util.*;

class point {
    int x, y;

    public point(int x, int y) {
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

class ecc {
    int a, b, p;
    point c1, c2, M, G, P;
    private int n;

    ecc(int a, int b, int p) {
        this.a = a;
        this.b = b;
        this.p = p;
    }

    // int mod_inv(int a,int b){
    // int p=b,x1=1,x2=0,r,q,x;
    // while(b!=0){
    // q=a/b;
    // r=a%b;
    // a=b;
    // b=r;
    // x= x1 - (q*x2);
    // x1=x2;
    // x2=x;
    // }
    // if(x1<0) return (p - x1%p);
    // return x1;
    // }
    int mod_inv(int num, int mod) {
        int m0 = mod, t, q;
        int x0 = 0, x1 = 1;

        if (mod == 1)
            return 0;

        while (num > 1) {
            q = num / mod;
            t = mod;
            mod = num % mod;
            num = t;

            t = x0;
            x0 = x1 - q * x0;
            x1 = t;
        }

        if (x1 < 0)
            x1 += m0;
        return x1;
    }

    point add_points(point p1, point p2) {
        point p3 = new point(-1, -1);

        int lambda, num, den;
        if (p1.x == p2.x && p1.y == p2.y) {
            num = (3 * p1.x * p2.x + a);
            den = (2 * p1.y);
        } else {
            num = p2.y - p1.y;
            den = p2.x - p1.x;
        }
        if (num % den == 0) {
            lambda = (num / den) % p;
        }
        lambda = (num * mod_inv(den, p)) % p;

        int x3 = (lambda * lambda - p1.x - p2.x) % p;
        int y3 = (lambda * (p1.x - x3) - p1.y) % p;

        return new point((x3 + p) % p, (y3 + p) % p);
    }

    point scalar_multiply(point p, int n) {
        point prod = p;
        while (n > 1) {
            prod = add_points(prod, p);
            n--;
        }
        return prod;
    }

    point negate(point pt) {
        int negY = (p - pt.y) % p;
        return new point(pt.x, negY);
    }

    void encrypt(point g, point m, int n) {
        this.n = n;
        G = g;
        M = m;
        P = scalar_multiply(G, n);
        Random rand = new Random();
        int k = rand.nextInt(10) + 1;
        c1 = scalar_multiply(g, k);
        c2 = add_points(M, scalar_multiply(P, k));
    }

    point decrypt() {
        point neg = negate(scalar_multiply(c1, n));
        return add_points(c2, neg);
    }
}

public class elliptic_curve {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the curve parameters (a, b) for E11(a, b):");
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int p = 11; // Fixed prime field

        ecc curve = new ecc(a, b, p);

        // Input for base point G
        System.out.println("Enter Base Point G (x y):");
        int gx = scanner.nextInt();
        int gy = scanner.nextInt();
        point G = new point(gx, gy);

        // Input for message point M
        System.out.println("Enter Message Point M (x y):");
        int mx = scanner.nextInt();
        int my = scanner.nextInt();
        point M = new point(mx, my);

        // Private key (n)
        System.out.println("Enter private key n (integer > 1):");
        int n = scanner.nextInt();

        // Encrypt
        curve.encrypt(G, M, n);
        System.out.println("\n--- Encrypted ---");
        System.out.println("Cipher C1: " + curve.c1);
        System.out.println("Cipher C2: " + curve.c2);

        // Decrypt
        point decrypted = curve.decrypt();
        System.out.println("\n--- Decrypted ---");
        System.out.println("Recovered Message Point: " + decrypted);

        scanner.close();
    }

}
