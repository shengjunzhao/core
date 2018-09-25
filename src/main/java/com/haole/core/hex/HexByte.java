package com.haole.core.hex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by shengjunzhao on 2017/8/27.
 */
public class HexByte {

    static final int DEFAULT_IO_THREADS = Runtime.getRuntime()
            .availableProcessors() * 2;

    public static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * 二进制转字符串
     *
     * @param b
     * @return
     */
    public static String byte2hex(byte[] b) {
        StringBuffer sb = new StringBuffer();
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0xFF));
            if (stmp.length() == 1)
                sb.append("0" + stmp);
            else
                sb.append(stmp);
        }
        return sb.toString().toUpperCase();
    }

    public static byte[] hex2bin(String hex) {
        if (hex.length() % 2 != 0) throw new ArrayIndexOutOfBoundsException("16进制字符必须是2的倍数");
        int len = hex.length() / 2;
        byte[] b = new byte[len];
        char[] hexChars = hex.toUpperCase().toCharArray();
        for (int i = 0; i < len; i++) {
            b[i] = (byte) (charToByte(hexChars[2 * i]) << 4 | charToByte(hexChars[2 * i + 1]));
        }
        return b;
    }

    /**
     * @param b
     * @param bigEnd 高位在前
     * @return
     */
    public static int byte2int(byte[] b, boolean bigEnd) {
        if (bigEnd) {
            return ((b[0] << 24) & 0xff000000) | ((b[1] << 16) & 0x00ff0000) ^ ((b[2] << 8) & 0x0000ff00) ^ (b[3] & 0x000000ff);
        } else {
            return ((b[3] << 24) & 0xff000000) | ((b[2] << 16) & 0x00ff0000) ^ ((b[1] << 8) & 0x0000ff00) ^ (b[0] & 0x000000ff);
        }
    }

    public static int[] byte2ArrayInt(byte[] b, boolean bigEnd) {
        byte input[] = new byte[4];
        List<Integer> list = new ArrayList<Integer>();
        int pos = 0;
        int len = b.length;
        while (len >= 4) {
            input = Arrays.copyOfRange(b, pos, pos + 4);
            list.add(byte2int(input, bigEnd));
            len -= 4;
            pos += 4;
        }
        int out[] = new int[list.size()];
        for (int i = 0; i < list.size(); i++)
            out[i] = list.get(i).intValue();
        return out;
    }

    public static byte[] int2byte(int i, boolean bigEnd) {
        byte[] out = new byte[4];
        if (bigEnd) {
            out[0] = (byte) ((i >>> 24) & 0xff);
            out[1] = (byte) ((i >>> 16) & 0xff);
            out[2] = (byte) ((i >>> 8) & 0xff);
            out[3] = (byte) (i & 0xff);
        } else {
            out[3] = (byte) ((i >>> 24) & 0xff);
            out[2] = (byte) ((i >>> 16) & 0xff);
            out[1] = (byte) ((i >>> 8) & 0xff);
            out[0] = (byte) (i & 0xff);
        }
        return out;
    }

    public static byte[] arrayInt2byte(int i[], boolean bigEnd) {
        int len = i.length;
        byte[] out = new byte[len * 4];
        for (int j = 0; j < len; j++) {
            if (bigEnd) {
                out[4 * j + 0] = (byte) ((i[j] >>> 24) & 0xff);
                out[4 * j + 1] = (byte) ((i[j] >>> 16) & 0xff);
                out[4 * j + 2] = (byte) ((i[j] >>> 8) & 0xff);
                out[4 * j + 3] = (byte) (i[j] & 0xff);
            } else {
                out[4 * j + 3] = (byte) ((i[j] >>> 24) & 0xff);
                out[4 * j + 2] = (byte) ((i[j] >>> 16) & 0xff);
                out[4 * j + 1] = (byte) ((i[j] >>> 8) & 0xff);
                out[4 * j + 0] = (byte) (i[j] & 0xff);
            }
        }
        return out;
    }

    public static long byte2long(byte[] b, boolean bigEnd) {
        if (bigEnd) {
            return ((b[0] << 56) & 0xff00000000000000l) | ((b[1] << 48) & 0x00ff000000000000l)
                    | ((b[2] << 40) & 0x0000ff0000000000l) | ((b[3] << 32) & 0x000000ff00000000l)
                    | ((b[4] << 24) & 0x00000000ff000000l) | ((b[5] << 16) & 0x0000000000ff0000l)
                    | ((b[6] << 8) & 0x000000000000ff00l) | (b[7] & 0x00000000000000ffl);
        } else {
            return ((b[7] << 56) & 0xff00000000000000l) | ((b[6] << 48) & 0x00ff000000000000l)
                    | ((b[5] << 40) & 0x0000ff0000000000l) | ((b[4] << 32) & 0x000000ff00000000l)
                    | ((b[3] << 24) & 0x00000000ff000000l) | ((b[2] << 16) & 0x0000000000ff0000l)
                    | ((b[1] << 8) & 0x000000000000ff00l) | (b[0] & 0x00000000000000ffl);
        }
    }
}
