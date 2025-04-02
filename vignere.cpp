#include <bits/stdc++.h>
using namespace std;

string  vigenere_encrypt(string pt,string key){
    int n = key.length();
    string ct;
    for(int i=0;i<pt.length();i++){
        // ct+=(char)(((int)pt[i] + key[i%n])%26);
        char pt_ch= toupper(pt[i]);
        char key_ch= toupper(key[i%n]);

        char enc = ((pt_ch-'A')+(key_ch-'A'))%26 + 'A';
        ct+=enc;
    }
    return ct;
}

string vigenere_decrypt(string ct, string key){
    int n = key.length();
    string pt;
    for(int i=0;i<ct.length();i++){
        char ct_ch = toupper(ct[i]);
        char key_ch = toupper(key[i%n]);

        char dec = ((ct_ch - 'A') - (key_ch - 'A') + 26)%26  + 'A';
        pt+=dec;
    }
    return pt;
}

int main(){
    string pt,key;
    cin>>pt>>key;
    string ciphertext = vigenere_encrypt(pt,key);
    string plaintext = vigenere_decrypt(ciphertext,key);
    cout<<"Ciphertext : "<<ciphertext<<endl;
    cout<<"Decrypted text : "<<plaintext<<endl;
    return 0;
}