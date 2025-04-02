#include <bits/stdc++.h>
using namespace std;

void ksa(vector<int> &s, const string &key){
    for(int i=0;i<256;i++)
        s[i]=i;
    int j=0;
    int n=key.length();
    for(int i=0;i<256;i++){
        j = (j+s[i]+key[i%n])%256;
        swap(s[i],s[j]);
    }
}

string prga(string &text , vector<int> &s){
    string output=text; 
    int i=0,j=0;
    for(int k=0;k<text.length();k++){
        i=(i+1)%256;
        j=(j+s[i])%256;
        swap(s[i],s[j]);
        int rnd = s[(s[i]+s[j])%256];
        output[i]=text[i]^rnd;
    }
    return output;
}

string rc4(string &text,const string &key){
    vector<int> s(256);
    ksa(s,key);
    return prga(text,s);
}

int main(){
    string plaintext, key;
    cout<<"Enter the text : \n";
    getline(cin,plaintext);
    cout<<"Enter the secret key :\n";
    getline(cin,key);

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