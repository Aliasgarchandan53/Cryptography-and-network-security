#include <bits/stdc++.h>
using namespace std;

// Function to generate the key matrix for Playfair cipher
void generateKeyMatrix(string key, char keyMatrix[5][5]) {
    string adjustedKey = "";
    vector<bool> used(26, false);
    used['J' - 'A'] = true; // Treat 'I' and 'J' as the same letter

    for (char ch : key) {
        if (!used[toupper(ch) - 'A']) {
            adjustedKey += toupper(ch);
            used[toupper(ch) - 'A'] = true;
        }
    }

    for (char ch = 'A'; ch <= 'Z'; ch++) {
        if (!used[ch - 'A']) {
            adjustedKey += ch;
            used[ch - 'A'] = true;
        }
    }

    int index = 0;
    for (int i = 0; i < 5; i++) {
        for (int j = 0; j < 5; j++) {
            keyMatrix[i][j] = adjustedKey[index++];
        }
    }
}

// Function to find the position of a letter in the key matrix
void findPosition(char keyMatrix[5][5], char ch, int &row, int &col) {
    if (ch == 'J') ch = 'I'; // Treat 'J' as 'I'
    for (int i = 0; i < 5; i++) {
        for (int j = 0; j < 5; j++) {
            if (keyMatrix[i][j] == ch) {
                row = i;
                col = j;
                return;
            }
        }
    }
}

// Function to format the plaintext into digraphs (pairs of letters)
string formatPlaintext(string plaintext) {
    string formatted = "";
    for (int i = 0; i < plaintext.length(); i++) {
        formatted += toupper(plaintext[i]);
        if (i + 1 < plaintext.length() && plaintext[i] == plaintext[i + 1])
            formatted += 'X';
    }
    if (formatted.length() % 2 != 0)
        formatted += 'X'; // Padding if odd length
    return formatted;
}

// Playfair cipher encryption
string encrypt(string plaintext, char keyMatrix[5][5]) {
    string formattedText = formatPlaintext(plaintext);
    string ciphertext = "";

    for (int i = 0; i < formattedText.length(); i += 2) {
        char a = formattedText[i];
        char b = formattedText[i + 1];

        int row1, col1, row2, col2;
        findPosition(keyMatrix, a, row1, col1);
        findPosition(keyMatrix, b, row2, col2);

        if (row1 == row2) { // Same row
            ciphertext += keyMatrix[row1][(col1 + 1) % 5];
            ciphertext += keyMatrix[row2][(col2 + 1) % 5];
        } else if (col1 == col2) { // Same column
            ciphertext += keyMatrix[(row1 + 1) % 5][col1];
            ciphertext += keyMatrix[(row2 + 1) % 5][col2];
        } else { // Rectangle rule
            ciphertext += keyMatrix[row1][col2];
            ciphertext += keyMatrix[row2][col1];
        }
    }
    return ciphertext;
}

// Playfair cipher decryption
string decrypt(string ciphertext, char keyMatrix[5][5]) {
    string plaintext = "";

    for (int i = 0; i < ciphertext.length(); i += 2) {
        char a = ciphertext[i];
        char b = ciphertext[i + 1];

        int row1, col1, row2, col2;
        findPosition(keyMatrix, a, row1, col1);
        findPosition(keyMatrix, b, row2, col2);

        if (row1 == row2) { // Same row
            plaintext += keyMatrix[row1][(col1 + 4) % 5];
            plaintext += keyMatrix[row2][(col2 + 4) % 5];
        } else if (col1 == col2) { // Same column
            plaintext += keyMatrix[(row1 + 4) % 5][col1];
            plaintext += keyMatrix[(row2 + 4) % 5][col2];
        } else { // Rectangle rule
            plaintext += keyMatrix[row1][col2];
            plaintext += keyMatrix[row2][col1];
        }
    }
    return plaintext;
}

// Display the key matrix
void displayKeyMatrix(char keyMatrix[5][5]) {
    cout << "Key Matrix:" << endl;
    for (int i = 0; i < 5; i++) {
        for (int j = 0; j < 5; j++) {
            cout << keyMatrix[i][j] << " ";
        }
        cout << endl;
    }
}

int main() {
    string key, plaintext;
    char keyMatrix[5][5];

    cout << "Enter key: ";
    cin >> key;

    cout << "Enter plaintext: ";
    cin >> plaintext;

    generateKeyMatrix(key, keyMatrix);
    displayKeyMatrix(keyMatrix);

    string ciphertext = encrypt(plaintext, keyMatrix);
    cout << "Ciphertext: " << ciphertext << endl;

    string decryptedText = decrypt(ciphertext, keyMatrix);
    cout << "Decrypted text: " << decryptedText << endl;

    return 0;
}
