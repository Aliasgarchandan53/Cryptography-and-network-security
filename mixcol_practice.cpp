#include <bits/stdc++.h>
using namespace std;
typedef uint8_t uint8_t; 

typedef array<array<uint8_t ,4>,4> State ;

uint8_t gmul(uint8_t a, uint8_t b){
    uint8_t p=0;
    for(int i=0;i<8;i++){
        if(b & 1)p^=a;
        bool hi_bit_st = a & 0x80;
        a<<=1;
        if(hi_bit_st)a ^= 0x1b;
        b>>=1;
    }
    return p;
}

void mix_columns(State &st){
    for(int i=0;i<4;i++){
        uint8_t s0 = st[0][i];
        uint8_t s1 = st[1][i];
        uint8_t s2=st[2][i];
        uint8_t  s3=st[3][i];

        st[0][i]= gmul(0x02,s0)^gmul(0x03,s1)^s2^s3;
        st[1][i]= s0^gmul(0x02,s1)^gmul(0x03,s2)^s3;
        st[2][i]= s0^s1^gmul(0x02,s2)^gmul(0x03,s3);
        st[3][i]= gmul(0x03,s0)^s1^s2^gmul(0x02,s3);
    }
}

void shiftrows(State &st){
    //row 0:no shift
    //row 1: left shift by 1
    swap(st[1][0],st[1][1]);
    swap(st[1][1],st[1][2]);
    swap(st[1][2],st[1][3]);
    //row2: left shift by 2
    swap(st[2][0],st[2][2]);
    swap(st[2][1],st[2][3]);
    //row3 : left shift by 3
    swap(st[3][2],st[3][3]);
    swap(st[3][1],st[3][2]);
    swap(st[3][0],st[3][1]);
}

void printstate(State &st){
    for(int i=0;i<4;i++){
        for(int j=0;j<4;j++)
            printf("%02X ",st[i][j]);
        printf("\n");
    }
}

int main(){
    State state;
    cout<<"Input 4*4 state array :\n";
    for(int i=0;i<4;i++){
        for(int j=0;j<4;j++){
            int val;
            cin>>hex>>val;
            state[i][j]=val;
        }
    }
    cout<<"Initial state :\n";
    printstate(state);
    shiftrows(state);
    cout<<"After shift rows :\n";
    printstate(state);
    mix_columns(state);
    cout<<"After mix columns :\n";
    printstate(state);
    return 0;
}