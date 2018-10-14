package loan.ppcat.godsky.controller.util;

public class BytesToNewByte {
    public static byte[] newBytes(byte[] bytes1, byte[] bytes2) {
        byte[] bytes = new byte[bytes1.length + bytes2.length];
        int i = 0;
        for (byte bt : bytes1) {
            bytes[i] = bt;
        }
        for (byte bt : bytes2) {
            bytes[i] = bt;
        }
        return bytes;
    }
}
