import java.util.*;

class rsa{
    int e,d,p,q,n,M,C,phi_n;
    int gcd(int a,int  b){
        while(b!=0){
            int temp=b;
            b=a%b;
            a=temp;
        }
        return b;
    }
    
    rsa(int p, int q){
        this.p=p;
        this.q=q;
        n=p*q;
        phi_n = (p-1)*(q-1);
    }
    int mod_inv(int a,int b){
        if (b == 1) return 1;
        int b0=b,x1=1,x0=0,q;
        while(a>1){         
            q=a/b;
            int temp = b;
            b=a%b;
            a=temp;
            
            temp=x0;
            x0 = x1 - q*x0;
            x1=temp;
        }
        if(x1<0)x1+=b0;
        return x1;
    }
    long power_mod(long a, long b, long p){
        long res=1;
        a=a%p;
        while(b>0){
            if((b&1)==1) 
                res = (res*a) % p;
            a = (a*a)%p;
            b>>=1;
        }
        return res;
    }
    void encrypt(int exp,int m){
        M=m;
        //key finding
        e = exp;
        d = mod_inv(e, phi_n);
        System.out.printf("Public key is {%d , %d}\n",e,n);
        System.out.printf("Private key is {%d , %d}\n",d,n);
        C = (int)power_mod(M, e, n);
        System.out.println("Ciphertext is "+C);
    }
    void decrypt(){
        int res = (int)power_mod(C, d, n);
        System.out.println("plaintext is "+res);
    }
}

public class rsa_algo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Take inputs for prime numbers p and q
        System.out.print("Enter prime number p: ");
        int p = scanner.nextInt();

        System.out.print("Enter prime number q: ");
        int q = scanner.nextInt();

        // Ensure p and q are prime
        // In a real RSA system, you would want to validate if p and q are prime
        // For simplicity, we assume they are prime in this code.

        // Create an RSA object with the given p and q
        rsa rsaKey = new rsa(p, q);

        // Take message input
        System.out.print("Enter message (plaintext): ");
        int message = scanner.nextInt();

        // Encrypt the message
        rsaKey.encrypt(13,message);

        // Decrypt the ciphertext
        rsaKey.decrypt();

        scanner.close();
    }
}
