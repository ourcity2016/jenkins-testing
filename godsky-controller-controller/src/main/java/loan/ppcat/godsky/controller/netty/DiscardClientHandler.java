package loan.ppcat.godsky.controller.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import loan.ppcat.godsky.controller.listener.CheckDeviceStatusThread;
import loan.ppcat.godsky.controller.model.Controller;
import org.apache.log4j.Logger;

import java.net.InetSocketAddress;

public class DiscardClientHandler extends ChannelInboundHandlerAdapter {
    private NettyGodSkyClient dataTrasferClient;
    private Logger logger = Logger.getLogger(DiscardClientHandler.class);
    private final String HEADER = "###@@@!!!";
    private final String CMD = "GETINITDATA";

    public DiscardClientHandler(NettyGodSkyClient _dataTrasferClient) {
        dataTrasferClient = _dataTrasferClient;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            RealTimeBusinessDataFilter.doDataFilter(msg);
            InetSocketAddress internetAddress = (InetSocketAddress) ctx.channel().localAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        Controller controller = CheckDeviceStatusThread.getControllerMap().get(1);
        ctx.channel().writeAndFlush(controller);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        try {
            ctx.close();
        } catch (Exception e) {
        }
        logger.error("Connection has exception , close connection and try to reconnecting !", cause);
        dataTrasferClient.startToServer();
    }

}