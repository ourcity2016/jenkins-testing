package loan.ppcat.godsky.realtime.netty.util;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class ObjectDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        loan.ppcat.godsky.realtime.netty.util.ByteBufToBytes read = new loan.ppcat.godsky.realtime.netty.util.ByteBufToBytes();
        Object obj = loan.ppcat.godsky.realtime.netty.util.ByteObjConverter.byteToObject(read.read(in));
        out.add(obj);
    }
}
