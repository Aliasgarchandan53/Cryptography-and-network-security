import java.util.*;

public class DES {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        byte[] text = new byte[32];
        byte[] permuted = new byte[48];

        for(int i=0;i<32;i++)
            text[i] = scan.nextByte();
        int [] permutation = new int[]{
            32,1,2,3,4,5,4,5,6,7,8,9,8,9,10,11,12,13,12,13,14,15,16,17
            ,16,17,18,19,20,21,20,21,22,23,24,25,24,25,26,27,28,29,28,29,30,31,32,1
        };
        for(int i=0;i<48;i++){
            permuted[i] = text[permutation[i]-1];
        }
        for(int i:permuted)
            System.out.print(i+" ");
        System.out.println();
        scan.close();
    }    
}
