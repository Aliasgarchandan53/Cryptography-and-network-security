import java.util.*;

class RoundKey {
    private final byte[] key;
    private final byte[][] w;
    private static final byte[] Rcon = {
        (byte)0x01, (byte)0x02, (byte)0x04, (byte)0x08, (byte)0x10,
        (byte)0x20, (byte)0x40, (byte)0x80, (byte)0x1B, (byte)0x36
    };
    private static final byte[] sBox = {
        (byte)0x63, (byte)0x7c, (byte)0x77, (byte)0x7b, (byte)0xf2, (byte)0x6b, (byte)0x6f, (byte)0xc5,
        (byte)0x30, (byte)0x01, (byte)0x67, (byte)0x2b, (byte)0xfe, (byte)0xd7, (byte)0xab, (byte)0x76,
        (byte)0xca, (byte)0x82, (byte)0xc9, (byte)0x7d, (byte)0xfa, (byte)0x59, (byte)0x47, (byte)0xf0,
        (byte)0xad, (byte)0xd4, (byte)0xa2, (byte)0xaf, (byte)0x9c, (byte)0xa4, (byte)0x72, (byte)0xc0,
        (byte)0xb7, (byte)0xfd, (byte)0x93, (byte)0x26, (byte)0x36, (byte)0x3f, (byte)0xf7, (byte)0xcc,
        (byte)0x34, (byte)0xa5, (byte)0xe5, (byte)0xf1, (byte)0x71, (byte)0xd8, (byte)0x31, (byte)0x15,
        (byte)0x04, (byte)0xc7, (byte)0x23, (byte)0xc3, (byte)0x18, (byte)0x96, (byte)0x05, (byte)0x9a,
        (byte)0x07, (byte)0x12, (byte)0x80, (byte)0xe2, (byte)0xeb, (byte)0x27, (byte)0xb2, (byte)0x75,
        (byte)0x09, (byte)0x83, (byte)0x2c, (byte)0x1a, (byte)0x1b, (byte)0x6e, (byte)0x5a, (byte)0xa0,
        (byte)0x52, (byte)0x3b, (byte)0xd6, (byte)0xb3, (byte)0x29, (byte)0xe3, (byte)0x2f, (byte)0x84,
        (byte)0x53, (byte)0xd1, (byte)0x00, (byte)0xed, (byte)0x20, (byte)0xfc, (byte)0xb1, (byte)0x5b,
        (byte)0x6a, (byte)0xcb, (byte)0xbe, (byte)0x39, (byte)0x4a, (byte)0x4c, (byte)0x58, (byte)0xcf,
        (byte)0xd0, (byte)0xef, (byte)0xaa, (byte)0xfb, (byte)0x43, (byte)0x4d, (byte)0x33, (byte)0x85,
        (byte)0x45, (byte)0xf9, (byte)0x02, (byte)0x7f, (byte)0x50, (byte)0x3c, (byte)0x9f, (byte)0xa8,
        (byte)0x51, (byte)0xa3, (byte)0x40, (byte)0x8f, (byte)0x92, (byte)0x9d, (byte)0x38, (byte)0xf5,
        (byte)0xbc, (byte)0xb6, (byte)0xda, (byte)0x21, (byte)0x10, (byte)0xff, (byte)0xf3, (byte)0xd2,
        (byte)0xcd, (byte)0x0c, (byte)0x13, (byte)0xec, (byte)0x5f, (byte)0x97, (byte)0x44, (byte)0x17,
        (byte)0xc4, (byte)0xa7, (byte)0x7e, (byte)0x3d, (byte)0x64, (byte)0x5d, (byte)0x19, (byte)0x73,
        (byte)0x60, (byte)0x81, (byte)0x4f, (byte)0xdc, (byte)0x22, (byte)0x2a, (byte)0x90, (byte)0x88,
        (byte)0x46, (byte)0xee, (byte)0xb8, (byte)0x14, (byte)0xde, (byte)0x5e, (byte)0x0b, (byte)0xdb,
        (byte)0xe0, (byte)0x32, (byte)0x3a, (byte)0x0a, (byte)0x49, (byte)0x06, (byte)0x24, (byte)0x5c,
        (byte)0xc2, (byte)0xd3, (byte)0xac, (byte)0x62, (byte)0x91, (byte)0x95, (byte)0xe4, (byte)0x79,
        (byte)0xe7, (byte)0xc8, (byte)0x37, (byte)0x6d, (byte)0x8d, (byte)0xd5, (byte)0x4e, (byte)0xa9,
        (byte)0x6c, (byte)0x56, (byte)0xf4, (byte)0xea, (byte)0x65, (byte)0x7a, (byte)0xae, (byte)0x08,
        (byte)0xba, (byte)0x78, (byte)0x25, (byte)0x2e, (byte)0x1c, (byte)0xa6, (byte)0xb4, (byte)0xc6,
        (byte)0xe8, (byte)0xdd, (byte)0x74, (byte)0x1f, (byte)0x4b, (byte)0xbd, (byte)0x8b, (byte)0x8a,
        (byte)0x70, (byte)0x3e, (byte)0xb5, (byte)0x66, (byte)0x48, (byte)0x03, (byte)0xf6, (byte)0x0e,
        (byte)0x61, (byte)0x35, (byte)0x57, (byte)0xb9, (byte)0x86, (byte)0xc1, (byte)0x1d, (byte)0x9e,
        (byte)0xe1, (byte)0xf8, (byte)0x98, (byte)0x11, (byte)0x69, (byte)0xd9, (byte)0x8e, (byte)0x94,
        (byte)0x9b, (byte)0x1e, (byte)0x87, (byte)0xe9, (byte)0xce, (byte)0x55, (byte)0x28, (byte)0xdf,
        (byte)0x8c, (byte)0xa1, (byte)0x89, (byte)0x0d, (byte)0xbf, (byte)0xe6, (byte)0x42, (byte)0x68,
        (byte)0x41, (byte)0x99, (byte)0x2d, (byte)0x0f, (byte)0xb0, (byte)0x54, (byte)0xbb, (byte)0x16
    };
    

    RoundKey(byte[] inputKey) {
        if (inputKey.length != 16) throw new IllegalArgumentException("AES-128 key must be 16 bytes.");
        this.key = inputKey.clone();
        this.w = new byte[44][4];
        keyExpansion();
    }

    private void keyExpansion() {
        for (int i = 0; i < 4; i++) {
            w[i][0] = key[4 * i];
            w[i][1] = key[4 * i + 1];
            w[i][2] = key[4 * i + 2];
            w[i][3] = key[4 * i + 3];
        }
        for (int i = 4; i < 44; i++) {
            byte[] temp = w[i - 1].clone();
            if (i % 4 == 0) {
                temp = subWord(rotWord(temp));
                temp[0] ^= Rcon[(i / 4) - 1];
            }
            for (int j = 0; j < 4; j++) {
                w[i][j] = (byte)(w[i - 4][j] ^ temp[j]);
            }
        }
    }

    private byte[] rotWord(byte[] word) {
        return new byte[]{word[1], word[2], word[3], word[0]};
    }

    private byte[] subWord(byte[] word) {
        for (int i = 0; i < 4; i++) {
            word[i] = sBox[byteToUnsigned(word[i])];
        }
        return word;
    }

    private int byteToUnsigned(byte b) {
        return b & 0xFF;
    }

    public byte[][] getRoundKey(int round) {
        if (round < 0 || round > 10) throw new IllegalArgumentException("Round must be 0-10");
        byte[][] roundKey = new byte[4][4];
        for (int i = 0; i < 4; i++) {
            roundKey[i][0] = w[round * 4 + i][0];
            roundKey[i][1] = w[round * 4 + i][1];
            roundKey[i][2] = w[round * 4 + i][2];
            roundKey[i][3] = w[round * 4 + i][3];
        }
        return roundKey;
    }
}

class AES {
    byte[][] state = new byte[4][4];
    byte[][] fixed = {
        {2, 3, 1, 1},
        {1, 2, 3, 1},
        {1, 1, 2, 3},
        {3, 1, 1, 2}
    };

    void loadPlaintextBlock(byte[] block) {
        for (int i = 0; i < 16; i++) {
            state[i % 4][i / 4] = block[i];
        }
    }

    void shiftRow(int row) {
        byte[] temp = new byte[4];
        for (int i = 0; i < 4; i++)
            temp[i] = state[row][(i + row) % 4];
        System.arraycopy(temp, 0, state[row], 0, 4);
    }

    void shiftRows() {
        shiftRow(1);
        shiftRow(2);
        shiftRow(3);
    }

    byte gmul(byte a, byte b) {
        byte p = 0;
        for (int i = 0; i < 8; i++) {
            if ((b & 1) != 0) p ^= a;
            boolean hbit = (a & 0x80) != 0;
            a <<= 1;
            if (hbit) a ^= 0x1B;
            b >>= 1;
        }
        return p;
    }

    void mixCols() {
        for (int j = 0; j < 4; j++) {
            byte[] col = new byte[4];
            System.arraycopy(state[j], 0, col, 0, 4);
            for (int i = 0; i < 4; i++) {
                byte result = 0;
                for (int k = 0; k < 4; k++) {
                    result ^= gmul(fixed[i][k], col[k]);
                }
                state[i][j] = result;
            }
        }
    }

    void addRoundKey(byte[][] roundKey) {
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                state[i][j] ^= roundKey[i][j];
    }

    byte[] getStateAsArray() {
        byte[] output = new byte[16];
        for (int i = 0; i < 16; i++)
            output[i] = state[i % 4][i / 4];
        return output;
    }

    void round(byte[][] roundKey, boolean isFinal) {
        shiftRows();
        if (!isFinal) mixCols();
        addRoundKey(roundKey);
    }
}

public class AES_round {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter 16-char AES key: ");
        String keyString = scan.nextLine();
        byte[] key = Arrays.copyOf(keyString.getBytes(), 16);
        RoundKey roundKey = new RoundKey(key);

        System.out.print("Enter plaintext: ");
        String plaintext = scan.nextLine();
        byte[] inputBytes = plaintext.getBytes();

        int blocks = (int) Math.ceil(inputBytes.length / 16.0);
        byte[] ciphertext = new byte[blocks * 16];

        for (int block = 0; block < blocks; block++) {
            AES cipher = new AES();
            byte[] currentBlock = Arrays.copyOfRange(inputBytes, block * 16, Math.min((block + 1) * 16, inputBytes.length));
            if (currentBlock.length < 16) {
                currentBlock = Arrays.copyOf(currentBlock, 16); // simple zero padding
            }

            cipher.loadPlaintextBlock(currentBlock);
            cipher.addRoundKey(roundKey.getRoundKey(0));  // Initial round

            for (int round = 1; round <= 9; round++) {
                cipher.round(roundKey.getRoundKey(round), false);
            }
            cipher.round(roundKey.getRoundKey(10), true);  // Final round

            byte[] encrypted = cipher.getStateAsArray();
            System.arraycopy(encrypted, 0, ciphertext, block * 16, 16);
        }

        System.out.println("Encrypted Hex Ciphertext:");
        for (byte b : ciphertext) System.out.printf("%02X ", b);
        System.out.println();
    }
}


// import java.util.*;

// class RoundKey {
//     private final byte[] key; // Original 16-byte key
//     private final byte[][] w; // Expanded keys (44 words for AES-128)

//     private static final byte[] Rcon = {
//         (byte)0x01, (byte)0x02, (byte)0x04, (byte)0x08, (byte)0x10,
//         (byte)0x20, (byte)0x40, (byte)0x80, (byte)0x1B, (byte)0x36
//     };

//     private static final byte[] sBox = {
//         // AES S-Box table (first 16 for example)
//         (byte)0x63, (byte)0x7c, (byte)0x77, (byte)0x7b, (byte)0xf2, (byte)0x6b, (byte)0x6f, (byte)0xc5,
//         (byte)0x30, (byte)0x01, (byte)0x67, (byte)0x2b, (byte)0xfe, (byte)0xd7, (byte)0xab, (byte)0x76,
//         // ... fill in the full 256-byte S-box here ...
//     };

//     RoundKey(byte[] inputKey) {
//         if (inputKey.length != 16) throw new IllegalArgumentException("AES-128 key must be 16 bytes.");
//         this.key = inputKey.clone();
//         this.w = new byte[44][4];  // 44 words of 4 bytes each
//         keyExpansion();
//     }

//     void keyExpansion() {
//         for (int i = 0; i < 4; i++) {
//             w[i][0] = key[4 * i];
//             w[i][1] = key[4 * i + 1];
//             w[i][2] = key[4 * i + 2];
//             w[i][3] = key[4 * i + 3];
//         }

//         for (int i = 4; i < 44; i++) {
//             byte[] temp = w[i - 1].clone();
//             if (i % 4 == 0) {
//                 temp = subWord(rotWord(temp));
//                 temp[0] ^= Rcon[(i / 4) - 1];
//             }
//             for (int j = 0; j < 4; j++) {
//                 w[i][j] = (byte)(w[i - 4][j] ^ temp[j]);
//             }
//         }
//     }

//     byte[] rotWord(byte[] word) {
//         return new byte[]{word[1], word[2], word[3], word[0]};
//     }

//     byte[] subWord(byte[] word) {
//         for (int i = 0; i < 4; i++) {
//             word[i] = sBox[byteToUnsigned(word[i])];
//         }
//         return word;
//     }

//     int byteToUnsigned(byte b) {
//         return b & 0xFF;
//     }

//     byte[][] getRoundKey(int round) {
//         if (round < 0 || round > 10) throw new IllegalArgumentException("Round must be between 0 and 10.");
//         byte[][] roundKey = new byte[4][4];
//         for (int i = 0; i < 4; i++) {
//             roundKey[i][0] = w[round * 4 + i][0];
//             roundKey[i][1] = w[round * 4 + i][1];
//             roundKey[i][2] = w[round * 4 + i][2];
//             roundKey[i][3] = w[round * 4 + i][3];
//         }
//         return roundKey;
//     }

//     void printRoundKeys() {
//         for (int round = 0; round <= 10; round++) {
//             System.out.println("Round " + round + ":");
//             for (int i = 0; i < 4; i++) {
//                 for (int j = 0; j < 4; j++) {
//                     System.out.printf("%02X ", w[round * 4 + i][j]);
//                 }
//                 System.out.println();
//             }
//             System.out.println();
//         }
//     }
// }

// class AES {
//     byte[][] state;
//     Scanner scan = new Scanner(System.in);
//     byte[][] fixed;

//     AES() {
//         state = new byte[4][4];
//         fixed = new byte[][] {
//                 { 2, 3, 1, 1 },
//                 { 1, 2, 3, 1 },
//                 { 1, 1, 2, 3 },
//                 { 3, 1, 1, 2 }
//         };
//     }

//     void readState() {
//         System.out.println("Enter the state:\n");
//         for (int i = 0; i < 4; i++)
//             for (int j = 0; j < 4; j++)
//                 state[i][j] = (byte) Integer.parseInt(scan.next(), 16);
//     }

//     void printState() {
//         for (int i = 0; i < 4; i++) {
//             for (int j = 0; j < 4; j++)
//                 System.out.printf("%02X ", state[i][j]);
//             System.out.println();
//         }
//         System.out.println();
//     }

//     void shiftRow(int row) {
//         byte[] temp = new byte[4];
//         for (int i = 0; i < 4; i++) {
//             temp[i] = state[row][(i + row) % 4]; // rotate left by `row`
//         }
//         for (int i = 0; i < 4; i++) {
//             state[row][i] = temp[i];
//         }
//     }

//     byte gmul(byte a, byte b) {
//         byte p = 0;
//         for (int i = 0; i < 8; i++) {
//             if ((b & 1) != 0)
//                 p ^= a;
//             boolean hbit = (a & 0x80) != 0;
//             a <<= 1;
//             if (hbit)
//                 a ^= 0x1B;
//             b >>= 1;
//         }
//         return p;
//     }

//     void mixCols() {
//         for (int j = 0; j < 4; j++) { // for each column
//             byte[] col = new byte[4];
//             for (int i = 0; i < 4; i++) {
//                 col[i] = state[i][j]; // copy original column
//             }

//             for (int i = 0; i < 4; i++) {
//                 byte result = 0;
//                 for (int k = 0; k < 4; k++) {
//                     result ^= gmul(fixed[i][k], col[k]); // correct AES matrix multiplication
//                 }
//                 state[i][j] = result;
//             }
//         }
//     }

//     void shiftRows() {
//         shiftRow(1);
//         shiftRow(2);
//         shiftRow(3);
//     }

//     void addRoundKey(byte[][] roundKey) {
//         for (int i = 0; i < 4; i++) {
//             for (int j = 0; j < 4; j++) {
//                 state[i][j] ^= roundKey[i][j];
//             }
//         }
//     }
// }

// public class AES_round {
//     public static void main(String[] args) {
//         AES cipher = new AES();
//         cipher.readState();
//         cipher.printState();
//         cipher.shiftRows();
//         cipher.printState();
//         cipher.mixCols();
//         cipher.printState();
//     }
// }