#include <bits/stdc++.h>
using namespace std;
using namespace std::chrono;

// Left rotate function
unsigned int leftRotate(unsigned int x, unsigned int n) {
    return (x << n) | (x >> (32 - n));
}

// SHA-1 Algorithm Implementation
string sha1(const string &message) {
    vector<unsigned int> h = { 0x67452301, 0xEFCDAB89, 0x98BADCFE, 0x10325476, 0xC3D2E1F0 };
    unsigned long long ml = message.size() * 8;
    string paddedMessage = message + (char)0x80;
    while ((paddedMessage.size() + 8) % 64 != 0) {
        paddedMessage += (char)0x00;
    }
    for (int i = 7; i >= 0; i--) {
        paddedMessage += (char)((ml >> (i * 8)) & 0xFF);
    }
    
    for (size_t i = 0; i < paddedMessage.size(); i += 64) {
        unsigned int w[80] = { 0 };
        for (int j = 0; j < 16; j++) {
            w[j] = (paddedMessage[i + j * 4] << 24) |
                   (paddedMessage[i + j * 4 + 1] << 16) |
                   (paddedMessage[i + j * 4 + 2] << 8) |
                   (paddedMessage[i + j * 4 + 3]);
        }
        for (int j = 16; j < 80; j++) {
            w[j] = leftRotate(w[j - 3] ^ w[j - 8] ^ w[j - 14] ^ w[j - 16], 1);
        }
        
        unsigned int a = h[0], b = h[1], c = h[2], d = h[3], e = h[4];
        for (int j = 0; j < 80; j++) {
            unsigned int f, k;
            if (j < 20) {
                f = (b & c) | ((~b) & d);
                k = 0x5A827999;
            } else if (j < 40) {
                f = b ^ c ^ d;
                k = 0x6ED9EBA1;
            } else if (j < 60) {
                f = (b & c) | (b & d) | (c & d);
                k = 0x8F1BBCDC;
            } else {
                f = b ^ c ^ d;
                k = 0xCA62C1D6;
            }
            unsigned int temp = leftRotate(a, 5) + f + e + k + w[j];
            e = d;
            d = c;
            c = leftRotate(b, 30);
            b = a;
            a = temp;
        }
        
        h[0] += a;
        h[1] += b;
        h[2] += c;
        h[3] += d;
        h[4] += e;
    }
    
    stringstream ss;
    for (int i = 0; i < 5; i++) {
        ss << hex << setw(8) << setfill('0') << h[i];
    }
    return ss.str();
}

int main() {
    string message;
    cout<<"Name : Ali Asgar Chandan \n";
    cout<<"Registration number : 22BCE0440\n";
    cout << "Enter message: ";
    getline(cin, message);
    
    auto start_sha1 = high_resolution_clock::now();
    string hash_sha1 = sha1(message);
    auto stop_sha1 = high_resolution_clock::now();
    
    auto duration_sha1 = duration_cast<microseconds>(stop_sha1 - start_sha1);
    
    cout << "SHA-1 Hash: " << hash_sha1 << endl;
    cout << "SHA-1 Execution Time: " << duration_sha1.count() << " microseconds" << endl;
    
    return 0;
}
