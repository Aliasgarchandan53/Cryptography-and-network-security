#include <bits/stdc++.h>
using namespace std;

int det(int key[2][2]){
    return (key[0][0]*key[1][1] - key[1][0]*key[0][1])%26;
}

int mod_inv(int num){
    num = (num%26 +26)%26;
    for(int i=1;i<26;i++){
        if((num*i)%26 == 1)
            return i;
    }
    return -1;
}

void inverse_key(int key[2][2], int inv_key[2][2]){
    int d = det(key);
    int d_inv = mod_inv(d);
    if(d_inv==-1){
        cout << "Key matrix is not invertible. Decryption not possible." << endl;
        exit(1);
    }

    inv_key[0][0]=key[1][1];
    inv_key[0][1]=-key[0][1];
    inv_key[1][0]=-key[1][0];
    inv_key[1][1]=key[0][0];

    for(int i=0;i<2;i++){
        for(int j=0;j<2;j++){
            inv_key[i][j] = (d_inv*inv_key[i][j])%26;
            if(inv_key[i][j]<0){
                inv_key[i][j] += 26;
            }
        }
    }
}

string hill_encrypt(string pt, int key[2][2]){
    if(pt.length() % 2 !=0) pt+='X'; //padding
    string ct="";
    for(int i=0;i<pt.length();i+=2){
        int p1=pt[i]-'A';
        int p2=pt[i+1]-'A';
        // int c1 = (key[0][0]*p1 + key[1][0]*p2)%26;
        // int c2 = (key[0][1]*p1 + key[1][1]*p2)%26;
        int c1 = (key[0][0]*p1 + key[0][1]*p2) % 26;
        int c2 = (key[1][0]*p1 + key[1][1]*p2) % 26;
        ct+=(c1+'A');
        ct+=(c2+'A');
    }
    return ct;
}

string hill_decrypt(string ct,int key[2][2]){
    string pt = "";
    int inv_key[2][2];  
    inverse_key(key, inv_key);

    for (int i = 0; i < ct.length(); i += 2) {
        int c1 = ct[i] - 'A';
        int c2 = ct[i + 1] - 'A';

        int p1 = (inv_key[0][0] * c1 + inv_key[0][1] * c2) % 26;
        int p2 = (inv_key[1][0] * c1 + inv_key[1][1] * c2) % 26;

        pt += (p1 + 'A');
        pt += (p2 + 'A');
    }
    return pt;
}

int main() {
    string pt;
    int key[2][2];

    cout << "Enter plaintext (uppercase letters only): ";
    cin >> pt;

    cout << "Enter 2x2 key matrix (integers between 0-25):\n";
    for (int i = 0; i < 2; i++)
        for (int j = 0; j < 2; j++)
            cin >> key[i][j];

    string ciphertext = hill_encrypt(pt, key);
    string decryptedtext = hill_decrypt(ciphertext, key);

    cout << "Ciphertext: " << ciphertext << endl;
    cout << "Decrypted text: " << decryptedtext << endl;

    return 0;
}