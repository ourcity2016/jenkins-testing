package loan.ppcat.godsky.realtime.netty.util;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ObjectEncoder extends MessageToByteEncoder {

    @Override
    protected void encode(ChannelHandlerContext ctx, Object object, ByteBuf out) throws Exception {
        byte[] datas = loan.ppcat.godsky.realtime.netty.util.ByteObjConverter.objectToByte(object);
        out.writeBytes(datas);
        ctx.flush();
    }
}
