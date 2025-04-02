import java.util.Scanner;

public class mixcol {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        byte[][] state = new byte[4][4];

        System.out.println("Enter the 4x4 state matrix (hex values, space-separated):");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                state[i][j] = (byte) Integer.parseInt(sc.next(), 16);
            }
        }

        System.out.println("\nInitial State (after Byte Substitution):");
        printState(state);

        shiftRows(state);
        System.out.println("After ShiftRows:");
        printState(state);

        mixColumns(state);
        System.out.println("After MixColumns:");
        printState(state);

        sc.close();
    }

    static void shiftRows(byte[][] state) {
        // Row 1: Shift left by 1
        byte temp = state[1][0];
        for (int i = 0; i < 3; i++) {
            state[1][i] = state[1][i + 1];
        }
        state[1][3] = temp;

        // Row 2: Shift left by 2
        byte temp1 = state[2][0];
        byte temp2 = state[2][1];
        state[2][0] = state[2][2];
        state[2][1] = state[2][3];
        state[2][2] = temp1;
        state[2][3] = temp2;

        // Row 3: Shift left by 3 (or right by 1)
        temp = state[3][3];
        for (int i = 3; i > 0; i--) {
            state[3][i] = state[3][i - 1];
        }
        state[3][0] = temp;
    }

    static byte gmul(byte a, byte b) {
        byte p = 0;
        for (int i = 0; i < 8; i++) {
            if ((b & 1) != 0) p ^= a;
            boolean hiBitSet = (a & 0x80) != 0;
            a <<= 1;
            if (hiBitSet) a ^= 0x1B;
            b >>= 1;
        }
        return p;
    }

    static void mixColumns(byte[][] state) {
        for (int col = 0; col < 4; col++) {
            byte s0 = state[0][col];
            byte s1 = state[1][col];
            byte s2 = state[2][col];
            byte s3 = state[3][col];

            state[0][col] = (byte) (gmul((byte) 0x02, s0) ^ gmul((byte) 0x03, s1) ^ s2 ^ s3);
            state[1][col] = (byte) (s0 ^ gmul((byte) 0x02, s1) ^ gmul((byte) 0x03, s2) ^ s3);
            state[2][col] = (byte) (s0 ^ s1 ^ gmul((byte) 0x02, s2) ^ gmul((byte) 0x03, s3));
            state[3][col] = (byte) (gmul((byte) 0x03, s0) ^ s1 ^ s2 ^ gmul((byte) 0x02, s3));
        }
    }

    static void printState(byte[][] state) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.printf("%02X ", state[i][j] & 0xFF);
            }
            System.out.println();
        }
        System.out.println();
    }
}
