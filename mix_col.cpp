#include <bits/stdc++.h>
using namespace std;

// AES state array is 4x4 (each element is 1 byte)
typedef array<array<uint8_t, 4>, 4> State;

// Galois Field multiplication for MixColumns
uint8_t gmul(uint8_t a, uint8_t b) {
    uint8_t p = 0;
    for (int i = 0; i < 8; i++) {
        if (b & 1) p ^= a;
        bool hi_bit_set = a & 0x80;
        a <<= 1;
        if (hi_bit_set) a ^= 0x1b; // AES's irreducible polynomial (x^8 + x^4 + x^3 + x + 1)
        b >>= 1;
    }
    return p;
}

// ShiftRows step
void shiftRows(State &state) {
    // Row 1: No shift
    // Row 2: Shift left by 1
    swap(state[1][0], state[1][1]);
    swap(state[1][1], state[1][2]);
    swap(state[1][2], state[1][3]);

    // Row 3: Shift left by 2
    swap(state[2][0], state[2][2]);
    swap(state[2][1], state[2][3]);

    // Row 4: Shift left by 3 (or right by 1)
    swap(state[3][0], state[3][3]);
    swap(state[3][3], state[3][2]);
    swap(state[3][2], state[3][1]);
}

// MixColumns step
void mixColumns(State &state) {
    for (int col = 0; col < 4; col++) {
        uint8_t s0 = state[0][col];
        uint8_t s1 = state[1][col];
        uint8_t s2 = state[2][col];
        uint8_t s3 = state[3][col];

        state[0][col] = gmul(0x02, s0) ^ gmul(0x03, s1) ^ s2 ^ s3;
        state[1][col] = s0 ^ gmul(0x02, s1) ^ gmul(0x03, s2) ^ s3;
        state[2][col] = s0 ^ s1 ^ gmul(0x02, s2) ^ gmul(0x03, s3);
        state[3][col] = gmul(0x03, s0) ^ s1 ^ s2 ^ gmul(0x02, s3);
    }
}

// Function to print the state array
void printState(const State &state) {
    for (int i = 0; i < 4; i++) {
        for (int j = 0; j < 4; j++) {
            printf("%02X ", state[i][j]);
        }
        cout << endl;
    }
    cout << endl;
}

int main() {
    State state;

    cout << "Enter the 4x4 state matrix (hex values, space-separated):\n";
    for (int i = 0; i < 4; i++)
        for (int j = 0; j < 4; j++) {
            int value;
            cin >> hex >> value;
            state[i][j] = value;
        }

    cout << "\nInitial State (after Byte Substitution):" << endl;
    printState(state);

    // ShiftRows transformation
    shiftRows(state);
    cout << "After ShiftRows:" << endl;
    printState(state);

    // MixColumns transformation
    mixColumns(state);
    cout << "After MixColumns:" << endl;
    printState(state);

    return 0;
}
