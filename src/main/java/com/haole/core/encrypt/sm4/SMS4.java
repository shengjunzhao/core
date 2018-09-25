package com.haole.core.encrypt.sm4;

import com.haole.core.hex.HexByte;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by shengjunzhao on 2017/8/27.
 */
public class SMS4 {

    private final static int ROUND = 32;

    // static short sbox[][]={
    // {0xd6,0x90,0xe9,0xfe,0xcc,0xe1,0x3d,0xb7,0x16,0xb6,0x14,0xc2,0x28,0xfb,0x2c,0x05},
    // {0x2b,0x67,0x9a,0x76,0x2a,0xbe,0x04,0xc3,0xaa,0x44,0x13,0x26,0x49,0x86,0x06,0x99},
    // {0x9c,0x42,0x50,0xf4,0x91,0xef,0x98,0x7a,0x33,0x54,0x0b,0x43,0xed,0xcf,0xac,0x62},
    // {0xe4,0xb3,0x1c,0xa9,0xc9,0x08,0xe8,0x95,0x80,0xdf,0x94,0xfa,0x75,0x8f,0x3f,0xa6},
    // {0x47,0x07,0xa7,0xfc,0xf3,0x73,0x17,0xba,0x83,0x59,0x3c,0x19,0xe6,0x85,0x4f,0xa8},
    // {0x68,0x6b,0x81,0xb2,0x71,0x64,0xda,0x8b,0xf8,0xeb,0x0f,0x4b,0x70,0x56,0x9d,0x35},
    // {0x1e,0x24,0x0e,0x5e,0x63,0x58,0xd1,0xa2,0x25,0x22,0x7c,0x3b,0x01,0x21,0x78,0x87},
    // {0xd4,0x00,0x46,0x57,0x9f,0xd3,0x27,0x52,0x4c,0x36,0x02,0xe7,0xa0,0xc4,0xc8,0x9e},
    // {0xea,0xbf,0x8a,0xd2,0x40,0xc7,0x38,0xb5,0xa3,0xf7,0xf2,0xce,0xf9,0x61,0x15,0xa1},
    // {0xe0,0xae,0x5d,0xa4,0x9b,0x34,0x1a,0x55,0xad,0x93,0x32,0x30,0xf5,0x8c,0xb1,0xe3},
    // {0x1d,0xf6,0xe2,0x2e,0x82,0x66,0xca,0x60,0xc0,0x29,0x23,0xab,0x0d,0x53,0x4e,0x6f},
    // {0xd5,0xdb,0x37,0x45,0xde,0xfd,0x8e,0x2f,0x03,0xff,0x6a,0x72,0x6d,0x6c,0x5b,0x51},
    // {0x8d,0x1b,0xaf,0x92,0xbb,0xdd,0xbc,0x7f,0x11,0xd9,0x5c,0x41,0x1f,0x10,0x5a,0xd8},
    // {0x0a,0xc1,0x31,0x88,0xa5,0xcd,0x7b,0xbd,0x2d,0x74,0xd0,0x12,0xb8,0xe5,0xb4,0xb0},
    // {0x89,0x69,0x97,0x4a,0x0c,0x96,0x77,0x7e,0x65,0xb9,0xf1,0x09,0xc5,0x6e,0xc6,0x84},
    // {0x18,0xf0,0x7d,0xec,0x3a,0xdc,0x4d,0x20,0x79,0xee,0x5f,0x3e,0xd7,0xcb,0x39,0x48}
    // };

    private static short sbox[] = {
            0xd6, 0x90, 0xe9, 0xfe, 0xcc, 0xe1, 0x3d, 0xb7, 0x16, 0xb6, 0x14, 0xc2, 0x28, 0xfb, 0x2c, 0x05,
            0x2b, 0x67, 0x9a, 0x76, 0x2a, 0xbe, 0x04, 0xc3, 0xaa, 0x44, 0x13, 0x26, 0x49, 0x86, 0x06, 0x99,
            0x9c, 0x42, 0x50, 0xf4, 0x91, 0xef, 0x98, 0x7a, 0x33, 0x54, 0x0b, 0x43, 0xed, 0xcf, 0xac, 0x62,
            0xe4, 0xb3, 0x1c, 0xa9, 0xc9, 0x08, 0xe8, 0x95, 0x80, 0xdf, 0x94, 0xfa, 0x75, 0x8f, 0x3f, 0xa6,
            0x47, 0x07, 0xa7, 0xfc, 0xf3, 0x73, 0x17, 0xba, 0x83, 0x59, 0x3c, 0x19, 0xe6, 0x85, 0x4f, 0xa8,
            0x68, 0x6b, 0x81, 0xb2, 0x71, 0x64, 0xda, 0x8b, 0xf8, 0xeb, 0x0f, 0x4b, 0x70, 0x56, 0x9d, 0x35,
            0x1e, 0x24, 0x0e, 0x5e, 0x63, 0x58, 0xd1, 0xa2, 0x25, 0x22, 0x7c, 0x3b, 0x01, 0x21, 0x78, 0x87,
            0xd4, 0x00, 0x46, 0x57, 0x9f, 0xd3, 0x27, 0x52, 0x4c, 0x36, 0x02, 0xe7, 0xa0, 0xc4, 0xc8, 0x9e,
            0xea, 0xbf, 0x8a, 0xd2, 0x40, 0xc7, 0x38, 0xb5, 0xa3, 0xf7, 0xf2, 0xce, 0xf9, 0x61, 0x15, 0xa1,
            0xe0, 0xae, 0x5d, 0xa4, 0x9b, 0x34, 0x1a, 0x55, 0xad, 0x93, 0x32, 0x30, 0xf5, 0x8c, 0xb1, 0xe3,
            0x1d, 0xf6, 0xe2, 0x2e, 0x82, 0x66, 0xca, 0x60, 0xc0, 0x29, 0x23, 0xab, 0x0d, 0x53, 0x4e, 0x6f,
            0xd5, 0xdb, 0x37, 0x45, 0xde, 0xfd, 0x8e, 0x2f, 0x03, 0xff, 0x6a, 0x72, 0x6d, 0x6c, 0x5b, 0x51,
            0x8d, 0x1b, 0xaf, 0x92, 0xbb, 0xdd, 0xbc, 0x7f, 0x11, 0xd9, 0x5c, 0x41, 0x1f, 0x10, 0x5a, 0xd8,
            0x0a, 0xc1, 0x31, 0x88, 0xa5, 0xcd, 0x7b, 0xbd, 0x2d, 0x74, 0xd0, 0x12, 0xb8, 0xe5, 0xb4, 0xb0,
            0x89, 0x69, 0x97, 0x4a, 0x0c, 0x96, 0x77, 0x7e, 0x65, 0xb9, 0xf1, 0x09, 0xc5, 0x6e, 0xc6, 0x84,
            0x18, 0xf0, 0x7d, 0xec, 0x3a, 0xdc, 0x4d, 0x20, 0x79, 0xee, 0x5f, 0x3e, 0xd7, 0xcb, 0x39, 0x48
    };

    // private static byte sbox[][]={
    // {(byte)0xd6,(byte)0x90,(byte)0xe9,(byte)0xfe,(byte)0xcc,(byte)0xe1,(byte)0x3d,(byte)0xb7,(byte)0x16,(byte)0xb6,(byte)0x14,(byte)0xc2,(byte)0x28,(byte)0xfb,(byte)0x2c,(byte)0x05},
    // {(byte)0x2b,(byte)0x67,(byte)0x9a,(byte)0x76,(byte)0x2a,(byte)0xbe,(byte)0x04,(byte)0xc3,(byte)0xaa,(byte)0x44,(byte)0x13,(byte)0x26,(byte)0x49,(byte)0x86,(byte)0x06,(byte)0x99},
    // {(byte)0x9c,(byte)0x42,(byte)0x50,(byte)0xf4,(byte)0x91,(byte)0xef,(byte)0x98,(byte)0x7a,(byte)0x33,(byte)0x54,(byte)0x0b,(byte)0x43,(byte)0xed,(byte)0xcf,(byte)0xac,(byte)0x62},
    // {(byte)0xe4,(byte)0xb3,(byte)0x1c,(byte)0xa9,(byte)0xc9,(byte)0x08,(byte)0xe8,(byte)0x95,(byte)0x80,(byte)0xdf,(byte)0x94,(byte)0xfa,(byte)0x75,(byte)0x8f,(byte)0x3f,(byte)0xa6},
    // {(byte)0x47,(byte)0x07,(byte)0xa7,(byte)0xfc,(byte)0xf3,(byte)0x73,(byte)0x17,(byte)0xba,(byte)0x83,(byte)0x59,(byte)0x3c,(byte)0x19,(byte)0xe6,(byte)0x85,(byte)0x4f,(byte)0xa8},
    // {(byte)0x68,(byte)0x6b,(byte)0x81,(byte)0xb2,(byte)0x71,(byte)0x64,(byte)0xda,(byte)0x8b,(byte)0xf8,(byte)0xeb,(byte)0x0f,(byte)0x4b,(byte)0x70,(byte)0x56,(byte)0x9d,(byte)0x35},
    // {(byte)0x1e,(byte)0x24,(byte)0x0e,(byte)0x5e,(byte)0x63,(byte)0x58,(byte)0xd1,(byte)0xa2,(byte)0x25,(byte)0x22,(byte)0x7c,(byte)0x3b,(byte)0x01,(byte)0x21,(byte)0x78,(byte)0x87},
    // {(byte)0xd4,(byte)0x00,(byte)0x46,(byte)0x57,(byte)0x9f,(byte)0xd3,(byte)0x27,(byte)0x52,(byte)0x4c,(byte)0x36,(byte)0x02,(byte)0xe7,(byte)0xa0,(byte)0xc4,(byte)0xc8,(byte)0x9e},
    // {(byte)0xea,(byte)0xbf,(byte)0x8a,(byte)0xd2,(byte)0x40,(byte)0xc7,(byte)0x38,(byte)0xb5,(byte)0xa3,(byte)0xf7,(byte)0xf2,(byte)0xce,(byte)0xf9,(byte)0x61,(byte)0x15,(byte)0xa1},
    // {(byte)0xe0,(byte)0xae,(byte)0x5d,(byte)0xa4,(byte)0x9b,(byte)0x34,(byte)0x1a,(byte)0x55,(byte)0xad,(byte)0x93,(byte)0x32,(byte)0x30,(byte)0xf5,(byte)0x8c,(byte)0xb1,(byte)0xe3},
    // {(byte)0x1d,(byte)0xf6,(byte)0xe2,(byte)0x2e,(byte)0x82,(byte)0x66,(byte)0xca,(byte)0x60,(byte)0xc0,(byte)0x29,(byte)0x23,(byte)0xab,(byte)0x0d,(byte)0x53,(byte)0x4e,(byte)0x6f},
    // {(byte)0xd5,(byte)0xdb,(byte)0x37,(byte)0x45,(byte)0xde,(byte)0xfd,(byte)0x8e,(byte)0x2f,(byte)0x03,(byte)0xff,(byte)0x6a,(byte)0x72,(byte)0x6d,(byte)0x6c,(byte)0x5b,(byte)0x51},
    // {(byte)0x8d,(byte)0x1b,(byte)0xaf,(byte)0x92,(byte)0xbb,(byte)0xdd,(byte)0xbc,(byte)0x7f,(byte)0x11,(byte)0xd9,(byte)0x5c,(byte)0x41,(byte)0x1f,(byte)0x10,(byte)0x5a,(byte)0xd8},
    // {(byte)0x0a,(byte)0xc1,(byte)0x31,(byte)0x88,(byte)0xa5,(byte)0xcd,(byte)0x7b,(byte)0xbd,(byte)0x2d,(byte)0x74,(byte)0xd0,(byte)0x12,(byte)0xb8,(byte)0xe5,(byte)0xb4,(byte)0xb0},
    // {(byte)0x89,(byte)0x69,(byte)0x97,(byte)0x4a,(byte)0x0c,(byte)0x96,(byte)0x77,(byte)0x7e,(byte)0x65,(byte)0xb9,(byte)0xf1,(byte)0x09,(byte)0xc5,(byte)0x6e,(byte)0xc6,(byte)0x84},
    // {(byte)0x18,(byte)0xf0,(byte)0x7d,(byte)0xec,(byte)0x3a,(byte)0xdc,(byte)0x4d,(byte)0x20,(byte)0x79,(byte)0xee,(byte)0x5f,(byte)0x3e,(byte)0xd7,(byte)0xcb,(byte)0x39,(byte)0x48}
    // };

    private static int fk[] = {0xa3b1bac6, 0x56aa3350, 0x677d9197, 0xb27022dc};

    private static int ck[] = {
            0x00070e15, 0x1c232a31, 0x383f464d, 0x545b6269,
            0x70777e85, 0x8c939aa1, 0xa8afb6bd, 0xc4cbd2d9,
            0xe0e7eef5, 0xfc030a11, 0x181f262d, 0x343b4249,
            0x50575e65, 0x6c737a81, 0x888f969d, 0xa4abb2b9,
            0xc0c7ced5, 0xdce3eaf1, 0xf8ff060d, 0x141b2229,
            0x30373e45, 0x4c535a61, 0x686f767d, 0x848b9299,
            0xa0a7aeb5, 0xbcc3cad1, 0xd8dfe6ed, 0xf4fb0209,
            0x10171e25, 0x2c333a41, 0x484f565d, 0x646b7279
    };

    public static int rotateLeft(int x, int n) {
        return ((x << n) | x >>> (32 - n));
    }

    private static int ltrans(int b) {
        return b ^ (rotateLeft(b, 2)) ^ (rotateLeft(b, 10)) ^ (rotateLeft(b, 18)) ^ (rotateLeft(b, 24));
    }

    private static int keyLtrans(int b) {
        return (b ^ rotateLeft(b, 13) ^ rotateLeft(b, 23));
    }

    private static int substitute(int x) {
        return (sbox[(x >>> 24) & 0xff] << 24)
                ^ (sbox[(x >>> 16) & 0xff] << 16)
                ^ (sbox[(x >>> 8) & 0xff] << 8)
                ^ (sbox[(x) & 0xff]);
    }

    private static int ttrans(int x) {
        return ltrans(substitute(x));
    }

    private static int keyTtrans(int x) {
        return keyLtrans(substitute(x));
    }

    /**
     * 计算轮密钥rk
     *
     * @param input
     * @param rk
     */
    private static void roundFun(int[] input, int rk) {
        int tmp = input[0];
        input[0] = input[1];
        input[1] = input[2];
        input[2] = input[3];
        input[3] = tmp ^ ttrans(input[0] ^ input[1] ^ input[2] ^ rk);
    }

    public static void reverse(int[] input) {
        int tmp;
        tmp = input[0];
        input[0] = input[3];
        input[3] = tmp;
        tmp = input[1];
        input[1] = input[2];
        input[2] = tmp;
    }

    public static int[] calcRk(int[] mainKey) {
        int rk[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] key = new int[4];
        key[0] = mainKey[0];
        key[1] = mainKey[1];
        key[2] = mainKey[2];
        key[3] = mainKey[3];

        key[0] ^= fk[0];
        key[1] ^= fk[1];
        key[2] ^= fk[2];
        key[3] ^= fk[3];
        for (int i = 0; i < ROUND; i++) {
            rk[i] = key[0] ^ keyTtrans(key[1] ^ key[2] ^ key[3] ^ ck[i]);
            key[0] = key[1];
            key[1] = key[2];
            key[2] = key[3];
            key[3] = rk[i];
        }
        return rk;
    }

    /**
     * 加密
     *
     * @param plain 明文
     * @param key   密钥
     * @return 密文
     */
    public static int[] encrypt(int[] plain, int[] key) {
        int[] plainData = Arrays.copyOf(plain, plain.length);
        int rk[] = calcRk(key);
        for (int i = 0; i < ROUND; i++) {
            roundFun(plainData, rk[i]);
        }
        reverse(plainData);
        return plainData;
    }

    public static byte[] encrypt(byte[] plain, byte[] key) {
        if (plain.length % 16 != 0) throw new ArrayIndexOutOfBoundsException("明文plain长度必须是16的倍数");
        if (key.length != 16) throw new ArrayIndexOutOfBoundsException("密钥key长度必须是8字节");
        int[] plainInt = new int[4];
        int[] keyInt = HexByte.byte2ArrayInt(key, true);
        int pos = 0;
        int len = plain.length;
        byte input[] = new byte[16];
        List<Integer> list = new ArrayList<Integer>();
        while (len >= 16) {
            input = Arrays.copyOfRange(plain, pos, pos + 16);
            plainInt = HexByte.byte2ArrayInt(input, true);
            int out[] = encrypt(plainInt, keyInt);
            list.add(out[0]);
            list.add(out[1]);
            list.add(out[2]);
            list.add(out[3]);
            len -= 16;
            pos += 16;
        }
        byte[] cipher = new byte[list.size() * 4];
        for (int i = 0; i < list.size(); i++) {
            byte[] o = HexByte.int2byte(list.get(i), true);
            cipher[4 * i + 0] = o[0];
            cipher[4 * i + 1] = o[1];
            cipher[4 * i + 2] = o[2];
            cipher[4 * i + 3] = o[3];
        }
        return cipher;
    }

    /**
     * @param plain 按照utf-8格式转换为byte
     * @param key   密钥必须是字节的16进制表示方式
     * @return
     * @throws UnsupportedEncodingException
     */
    public static byte[] encrypt(String plain, String key) throws UnsupportedEncodingException {
        if (key.length() != 32) throw new ArrayIndexOutOfBoundsException("密钥key长度必须是32字符");
        byte[] k = HexByte.hex2bin(key);
        byte[] po = plain.getBytes("utf-8");
        byte[] p;
        if (po.length % 16 != 0) {
            p = new byte[(po.length / 16 + 1) * 16];
        } else {
            p = new byte[po.length];
        }
        System.arraycopy(po, 0, p, 0, po.length);
        return encrypt(p, k);
    }

    /**
     * 反复加密1000000次
     *
     * @param plain
     * @param key
     * @return
     */
    public static int[] encryptMillion(int[] plain, int[] key) {
        int[] plainData = Arrays.copyOf(plain, plain.length);
        int rk[] = calcRk(key);
//		System.out.println(Integer.toHexString(rk[0]));
//		System.out.println(Integer.toHexString(rk[1]));
//		System.out.println(Integer.toHexString(rk[2]));
//		System.out.println(Integer.toHexString(rk[3]));
        for (int j = 0; j < 1000000; j++) {
            for (int i = 0; i < ROUND; i++) {
                roundFun(plainData, rk[i]);
            }
            reverse(plainData);
        }
        return plainData;
    }

    /**
     * 解密
     *
     * @param cipher 密文
     * @param key    密钥
     * @return 明文
     */
    public static int[] decrypt(int[] cipher, int[] key) {
        int[] cipherData = Arrays.copyOf(cipher, cipher.length);
        int rk[] = calcRk(key);
        for (int i = 0; i < ROUND; i++) {
            roundFun(cipherData, rk[31 - i]);
        }
        reverse(cipherData);
        return cipherData;
    }

    public static byte[] decrypt(byte[] cipher, byte[] key) {
        if (cipher.length % 16 != 0) throw new ArrayIndexOutOfBoundsException("密文plain长度必须是16的倍数");
        if (key.length != 16) throw new ArrayIndexOutOfBoundsException("密钥key长度必须是8字节");
        int[] cipherInt = new int[4];
        int[] keyInt = HexByte.byte2ArrayInt(key, true);
        int pos = 0;
        int len = cipher.length;
        byte input[] = new byte[16];
        List<Integer> list = new ArrayList<Integer>();
        while (len >= 16) {
            input = Arrays.copyOfRange(cipher, pos, pos + 16);
            cipherInt = HexByte.byte2ArrayInt(input, true);
            int out[] = decrypt(cipherInt, keyInt);
            list.add(out[0]);
            list.add(out[1]);
            list.add(out[2]);
            list.add(out[3]);
            len -= 16;
            pos += 16;
        }
        byte[] plain = new byte[list.size() * 4];
        for (int i = 0; i < list.size(); i++) {
            byte[] o = HexByte.int2byte(list.get(i), true);
            plain[4 * i + 0] = o[0];
            plain[4 * i + 1] = o[1];
            plain[4 * i + 2] = o[2];
            plain[4 * i + 3] = o[3];
        }
        return plain;
    }

    /**
     * 反复解密1000000次
     *
     * @param cipher 密文
     * @param key    密钥
     * @return 明文
     */
    public static int[] decryptMillion(int[] cipher, int[] key) {
        int[] cipherData = Arrays.copyOf(cipher, cipher.length);
        int rk[] = calcRk(key);
        for (int j = 0; j < 1000000; j++) {
            for (int i = 0; i < ROUND; i++) {
                roundFun(cipherData, rk[31 - i]);
            }
            reverse(cipherData);
        }
        return cipherData;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println(Integer.toBinaryString(0xa3b1bac6)); // 10100011101100011011101011000110
        System.out.println(Integer.toBinaryString(rotateLeft(0xa3b1bac6, 2))); // 10001110110001101110101100011010
        System.out.println(rotateLeft(0xa3b1bac6, 2)); // -1899566310
        System.out.println(Integer.toHexString(substitute(0x00010203)));
        int[] plain = {0x01234567, 0x89abcdef, 0xfedcba98, 0x76543210};
        int[] key = {0x01234567, 0x89abcdef, 0xfedcba98, 0x76543210};
        int[] cipher = encrypt(plain, key);
        System.out.println("------------------");
        System.out.println(Integer.toHexString(cipher[0]));
        System.out.println(Integer.toHexString(cipher[1]));
        System.out.println(Integer.toHexString(cipher[2]));
        System.out.println(Integer.toHexString(cipher[3]));

        int[] plain1 = decrypt(cipher, key);
        System.out.println("------------------");
        System.out.println(Integer.toHexString(plain1[0]));
        System.out.println(Integer.toHexString(plain1[1]));
        System.out.println(Integer.toHexString(plain1[2]));
        System.out.println(Integer.toHexString(plain1[3]));
        System.out.println("------------------");
//		int[] cipher1={0,0,0,0};
//		for (int i=0;i<1000000;i++) {
//			int[] key2={0x01234567, 0x89abcdef, 0xfedcba98,0x76543210};
//			cipher1=encrypt(plain2,key2);
//			plain2=cipher1;
//		}

        int[] cipher1 = encryptMillion(plain, key);
        System.out.println(Integer.toHexString(cipher1[0]));
        System.out.println(Integer.toHexString(cipher1[1]));
        System.out.println(Integer.toHexString(cipher1[2]));
        System.out.println(Integer.toHexString(cipher1[3]));
        System.out.println("------------------");
        int[] plain2 = decryptMillion(cipher1, key);
        System.out.println(Integer.toHexString(plain2[0]));
        System.out.println(Integer.toHexString(plain2[1]));
        System.out.println(Integer.toHexString(plain2[2]));
        System.out.println(Integer.toHexString(plain2[3]));
        System.out.println("------------------");

        byte[] plainb = {0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xab, (byte) 0xcd, (byte) 0xef, (byte) 0xfe, (byte) 0xdc, (byte) 0xba, (byte) 0x98, 0x76, 0x54, 0x32, 0x10};
        byte[] keyb = {0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xab, (byte) 0xcd, (byte) 0xef, (byte) 0xfe, (byte) 0xdc, (byte) 0xba, (byte) 0x98, 0x76, 0x54, 0x32, 0x10};
        byte[] cipher2 = encrypt(plainb, keyb);
//        System.out.println(MD5.byte2hex(cipher2));
        System.out.println("------------------");
        byte[] p2 = decrypt(cipher2, keyb);
//        System.out.println(MD5.byte2hex(p2));
        System.out.println("------------------");

        String keys = "0123456789abcdeffedcba9876543210";
        byte[] cipher3 = encrypt("a", keys);
//        System.out.println(MD5.byte2hex(cipher3));
        System.out.println("------------------");


        byte b = (byte) 0xa3;
        short s = 0xa3;
        System.out.println(Integer.toBinaryString(b)); // 11111111111111111111111110100011
        System.out.println(Integer.toBinaryString(s)); // 10100011
        System.out.println(Integer.toHexString(b)); // ffffffa3
        System.out.println(Integer.toHexString(s)); // a3
    }

}

