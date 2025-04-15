import java.util.*;

class elgamal{
    int p,g,d,e,k,y1,y2,M;

    elgamal(int p,int g, int d,int k){
        this.p=p;
        this.g=g;
        this.k=k;
        this.d=d;
    }

    int mod_inv(int a, int b){
        int b0=b,x0=0,x1=1,q,t;

        while(a>1){
            q=a/b;
            
            t=b;
            b=a%b;
            a=t;

            t=x0;
            x0 = x1 - q*x0;
            x1=t;
        }
        if(x1<0)x1+=b0;
        return x1;
    }
    long power_mod(long a, long b,long n){
        long res=1;
        a%=n;
        while(b>0){
            if((b&1)==1)res = (res*a)%n;
            a=(a*a)%n;
            b>>=1;
        }
        return res;
    }

    void encrypt(int message){
        M=message;
        e = (int)power_mod(g, d, p);
        y1 = (int)power_mod(g, k, p);
        y2 = (M * (int)power_mod(e, k, p))%p;
        System.out.printf("Ciphertext : {y1,y2} : {%d , %d}\n",y1,y2);
    }
    void decrypt(){
        int y1_d = (int) power_mod(y1, d, p);
        int inv = mod_inv(y1_d, p);
        int res = (y2 * inv) % p;

    // Normalize if negative
    if (res < 0) res += p;
        System.out.printf("Plaintext : %d\n",res);
    }
}

public class elgamal_cryptosystem {
    public static void main(String[] args) {
        elgamal eg = new elgamal(13, 2, 3, 7);
        eg.encrypt(4);
        eg.decrypt();
    }
}
