#include <bits/stdc++.h>
using namespace std;

class MD5 {
private:
    static constexpr uint32_t s[64] = { 7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 17, 22,
                                        5,  9, 14, 20, 5,  9, 14, 20, 5,  9, 14, 20, 5,  9, 14, 20,
                                        4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23,
                                        6, 10, 15, 21, 6, 10, 15, 21, 6, 10, 15, 21, 6, 10, 15, 21 };
    static constexpr uint32_t K[64] = { /* Precomputed constants */ };
    uint32_t a, b, c, d;
    vector<uint8_t> buffer;
    uint64_t bit_len;

    void transform(const uint8_t block[64]) {
        uint32_t M[16], A = a, B = b, C = c, D = d;
        memcpy(M, block, 64);
        for (int i = 0; i < 64; ++i) {
            uint32_t F, g;
            if (i < 16) {
                F = (B & C) | (~B & D);
                g = i;
            } else if (i < 32) {
                F = (D & B) | (~D & C);
                g = (5 * i + 1) % 16;
            } else if (i < 48) {
                F = B ^ C ^ D;
                g = (3 * i + 5) % 16;
            } else {
                F = C ^ (B | ~D);
                g = (7 * i) % 16;
            }
            F += A + K[i] + M[g];
            A = D;
            D = C;
            C = B;
            B += (F << s[i]) | (F >> (32 - s[i]));
        }
        a += A;
        b += B;
        c += C;
        d += D;
    }

public:
    MD5() {
        a = 0x67452301;
        b = 0xefcdab89;
        c = 0x98badcfe;
        d = 0x10325476;
        bit_len = 0;
    }

    void update(const string &input, const string &key) {
        string combined = key + input;
        buffer.insert(buffer.end(), combined.begin(), combined.end());
        bit_len += combined.size() * 8;
        while (buffer.size() >= 64) {
            transform(buffer.data());
            buffer.erase(buffer.begin(), buffer.begin() + 64);
        }
    }

    string digest() {
        buffer.push_back(0x80);
        while (buffer.size() % 64 != 56) buffer.push_back(0);
        for (int i = 0; i < 8; i++) buffer.push_back(bit_len >> (i * 8));
        transform(buffer.data());
        stringstream ss;
        for (uint32_t v : {a, b, c, d})
            for (int i = 0; i < 4; i++)
                ss << hex << setw(2) << setfill('0') << ((v >> (i * 8)) & 0xff);
        return ss.str();
    }
};

int main() {
    string message, key;
    cout<<"Name : Ali Asgar Chandan \n";
    cout<<"Registration number : 22BCE0440\n";
    cout << "Enter message: ";
    getline(cin, message);
    cout << "Enter key: ";
    getline(cin, key);
    MD5 md5;
    md5.update(message, key);
    cout << "MD5 MAC: " << md5.digest() << endl;
    return 0;
}

/*
Test Cases:
Input 1:
Enter message: Hello, Secure World!
Enter key: securekey123
Expected Output:
MD5 MAC: (Computed MD5 hash of concatenated key and message)

Input 2:
Enter message: TestMessage123
Enter key: MySecretKey
Expected Output:
MD5 MAC: (Computed MD5 hash of concatenated key and message)
*/

