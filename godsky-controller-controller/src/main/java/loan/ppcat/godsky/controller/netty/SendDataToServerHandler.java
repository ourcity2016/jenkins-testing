package loan.ppcat.godsky.controller.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import loan.ppcat.godsky.controller.listener.CheckDeviceStatusThread;
import loan.ppcat.godsky.controller.model.Controller;
import loan.ppcat.godsky.controller.model.HeartBeat;
import org.apache.log4j.Logger;

import java.util.Map;

public class SendDataToServerHandler extends ChannelInboundHandlerAdapter {
    private Logger logger = Logger.getLogger(SendDataToServerHandler.class);
    private Map<Integer, Controller> controllerMap = CheckDeviceStatusThread.getControllerMap();
    private static int flag = -1;
    private static long current = 0;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            current += NettyGodSkyClient.getSendMillTimes();
            if (event.state() == IdleState.WRITER_IDLE) {
                if (current > 1000) {
                    ctx.channel().writeAndFlush(new HeartBeat());
                    current = 0;
                }
                Controller controller = controllerMap.get(1);
                int currentStatus = CheckDeviceStatusThread.isIsChangedStatus();
                if (currentStatus != flag) {
                    logger.debug("Send data time to Server");
                    logger.debug(currentStatus + "===========" + flag);
                    flag = currentStatus;
                    ctx.channel().writeAndFlush(controller);
                }
            }
        }
    }


}