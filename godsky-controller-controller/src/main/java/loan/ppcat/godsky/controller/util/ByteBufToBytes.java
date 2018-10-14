package loan.ppcat.godsky.realtime.netty.util;

import io.netty.buffer.ByteBuf;

import java.util.UUID;

public class ByteBufToBytes {
    public byte[] read(ByteBuf datas) {
        byte[] bytes = new byte[datas.readableBytes()];
        datas.readBytes(bytes);
        return bytes;
    }

    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().toString());
    }
}
