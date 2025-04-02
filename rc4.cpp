#include <bits/stdc++.h>
using namespace std;

// Key-Scheduling Algorithm (KSA)
void ksa(vector<int>& S, const string& key) {
    int key_length = key.length();
    for (int i = 0; i < 256; i++) {
        S[i] = i;
    }
    int j = 0;
    for (int i = 0; i < 256; i++) {
        j = (j + S[i] + key[i % key_length]) % 256;
        swap(S[i], S[j]);
    }
}

// Pseudo-Random Generation Algorithm (PRGA)
string prga(const string& plaintext, vector<int>& S) {
    int i = 0, j = 0;
    string output = plaintext;
    for (int k = 0; k < plaintext.length(); k++) {
        i = (i + 1) % 256;
        j = (j + S[i]) % 256;
        swap(S[i], S[j]);
        int rnd = S[(S[i] + S[j]) % 256];
        output[k] = plaintext[k] ^ rnd;
    }
    return output;
}

// RC4 encryption and decryption
string rc4(const string& text, const string& key) {
    vector<int> S(256);
    ksa(S, key);
    return prga(text, S);
}

int main() {
    string plaintext, key;
    cout << "Enter plaintext: ";
    getline(cin, plaintext);
    cout << "Enter key: ";
    getline(cin, key);

    // Encrypt
    string ciphertext = rc4(plaintext, key);
    cout << "Ciphertext (in hex): ";
    for (char c : ciphertext) {
        cout << hex << (int)(unsigned char)c << " ";
    }
    cout << endl;

    // Decrypt (RC4 decryption is same as encryption)
    string decrypted = rc4(ciphertext, key);
    cout << "Decrypted text: " << decrypted << endl;

    return 0;
}
