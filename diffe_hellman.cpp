#include <bits/stdc++.h>

using namespace std;

// Function to find (base^exp) % mod 
long long power(long long base, long long exp, long long mod) {
    long long result = 1;
    while (exp > 0) {
        if (exp % 2 == 1)
            result = (result * base) % mod;
        base = (base * base) % mod;
        exp /= 2;
    }
    return result;
}

int main() {
    srand(time(0));

    long long P, G;
    cout << "Enter a prime number (P): ";
    cin >> P;
    cout << "Enter a primitive root modulo P (G): ";
    cin >> G;

    // Number of parties
    int numParties;
    cout << "Enter the number of participants: ";
    cin >> numParties;

    vector<long long> privateKeys(numParties);
    vector<long long> publicKeys(numParties);

    cout << "Participants generating keys...\n";
    for (int i = 0; i < numParties; i++) {
        privateKeys[i] = rand() % (P - 1) + 1; // Random private key
        publicKeys[i] = power(G, privateKeys[i], P);
        cout << "Party " << i+1 << " private key: " << privateKeys[i] << "\n";
        cout << "Party " << i+1 << " public key: " << publicKeys[i] << "\n";
    }

    // MITM Attacker interception and alteration
    long long attackerPrivateKey = rand() % (P - 1) + 1;
    long long attackerPublicKey = power(G, attackerPrivateKey, P);
    
    cout << "\nMan-in-the-Middle (MITM) Attack: Attacker intercepts and modifies keys!\n";

    // attacker sends fake keys
    vector<long long> fakePublicKeys(numParties, attackerPublicKey);
    
    // computing shared secret key
    vector<long long> sharedSecrets(numParties);
    for (int i = 0; i < numParties; i++) {
        sharedSecrets[i] = power(fakePublicKeys[i], privateKeys[i], P);
        cout << "Party " << i+1 << " thinks its shared secret: " << sharedSecrets[i] << "\n";
    }

    // attacker finding shared secrets
    cout << "\nAttacker computes the same secrets:\n";
    for (int i = 0; i < numParties; i++) {
        long long attackerSharedSecret = power(publicKeys[i], attackerPrivateKey, P);
        cout << "Attacker's shared secret with Party " << i+1 << ": " << attackerSharedSecret << "\n";
    }

    string plaintext;
    cout << "\nEnter a plaintext message to simulate encryption: ";
    cin.ignore();
    getline(cin, plaintext);

    //encryption
    long long encryptionKey = sharedSecrets[0] % 256;
    string encryptedMessage = plaintext;
    for (char &c : encryptedMessage) {
        c ^= encryptionKey;
    }

    cout << "Encrypted message: " << encryptedMessage << "\n";

    // Attacker decrypting message
    string decryptedMessage = encryptedMessage;
    for (char &c : decryptedMessage) {
        c ^= encryptionKey;
    }
    cout << "\nAttacker decrypts the message: " << decryptedMessage << "\n";

    return 0;
}