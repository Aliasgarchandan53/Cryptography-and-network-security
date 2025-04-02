#include <bits/stdc++.h>
using namespace std;

// Function to compute (base^exp) % mod
long long mod_exp(long long base, long long exp, long long mod) {
    long long result = 1;
    while (exp > 0) {
        if (exp % 2 == 1)
            result = (result * base) % mod;
        base = (base * base) % mod;
        exp /= 2;
    }
    return result;
}

// Extended Euclidean Algorithm to compute modular inverse
long long mod_inverse(long long a, long long mod) {
    long long m0 = mod, t, q;
    long long x0 = 0, x1 = 1;
    if (mod == 1) return 0;
    while (a > 1) {
        q = a / mod;
        t = mod;
        mod = a % mod, a = t;
        t = x0;
        x0 = x1 - q * x0;
        x1 = t;
    }
    if (x1 < 0) x1 += m0;
    return x1;
}

// Hash function (Simple Modulo-based Hash for Demonstration)
long long hash_message(const string &message, long long q) {
    long long hash = 0;
    for (char c : message) {
        hash = (hash * 31 + c) % q;
    }
    return hash;
}

// Digital Signature Algorithm (DSA) Key Generation
void generate_keys(long long &p, long long &q, long long &g, long long &x, long long &y) {
    q = 23;  // Small prime number
    p = 47;  // p = 2*q + 1, a larger prime number
    g = 2;   // Generator
    x = rand() % (q - 1) + 1;  // Private key
    y = mod_exp(g, x, p);  // Public key
}

// Signing the message
pair<long long, long long> sign_message(const string &message, long long p, long long q, long long g, long long x) {
    long long k = rand() % (q - 1) + 1;
    long long r = mod_exp(g, k, p) % q;
    long long h = hash_message(message, q);
    long long s = (mod_inverse(k, q) * (h + x * r)) % q;
    return {r, s};
}

// Verifying the signature
bool verify_signature(const string &message, long long p, long long q, long long g, long long y, long long r, long long s) {
    if (r <= 0 || r >= q || s <= 0 || s >= q) return false;
    long long h = hash_message(message, q);
    long long w = mod_inverse(s, q);
    long long u1 = (h * w) % q;
    long long u2 = (r * w) % q;
    long long v = ((mod_exp(g, u1, p) * mod_exp(y, u2, p)) % p) % q;
    return v == r;
}

int main() {
    srand(time(0));
    long long p, q, g, x, y;
    generate_keys(p, q, g, x, y);
    
    cout<<"Name : Ali Asgar Chandan \n";
    cout<<"Registration number : 22BCE0440\n";
    
    string message;
    cout << "Enter message: ";
    getline(cin, message);
    
    auto signature = sign_message(message, p, q, g, x);
    cout << "Generated Signature: (r = " << signature.first << ", s = " << signature.second << ")\n";
    
    bool valid = verify_signature(message, p, q, g, y, signature.first, signature.second);
    cout << "Signature Verification: " << (valid ? "Valid" : "Invalid") << endl;
    
    return 0;
}