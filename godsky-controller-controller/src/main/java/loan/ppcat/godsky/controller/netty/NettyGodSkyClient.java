package loan.ppcat.godsky.controller.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class NettyGodSkyClient {
    private Logger logger = Logger.getLogger(NettyGodSkyClient.class);
    private static int port;
    private static String host;
    private EventLoopGroup workerGroup = null;
    private Bootstrap bootstrap = null;
    private NettyGodSkyClient dataTrasferClient;
    private static int sendMillTimes = 1;
    private static boolean currentStatus = false;

    public static int getPort() {
        return port;
    }

    public static String getHost() {
        return host;
    }

    public static void setCurrentStatus(boolean currentStatus) {
        NettyGodSkyClient.currentStatus = currentStatus;
    }

    public static boolean isCurrentStatus() {
        return currentStatus;
    }

    public static int getSendMillTimes() {
        return sendMillTimes;
    }

    public static void setSendMillTimes(int sendMillTimes) {
        sendMillTimes = sendMillTimes;
    }

    public NettyGodSkyClient getDataTrasferClient() {
        return dataTrasferClient;
    }

    public void setDataTrasferClient(NettyGodSkyClient dataTrasferClient) {
        this.dataTrasferClient = dataTrasferClient;
    }

    private NettyGodSkyClient() {

    }

    public NettyGodSkyClient(String host, int port) {
        this.port = port;
        this.host = host;
    }


    public void initBootstrap() {
        Properties properties = new Properties();
        try {
            properties.load(NettyGodSkyClient.class.getResourceAsStream("/server.properties"));

        } catch (IOException e) {
        }
        host = properties.getProperty("server-host");
        port = Integer.parseInt(properties.get("server-port").toString());
        sendMillTimes = Integer.parseInt(properties.get("server-sendMillTimes").toString());
        workerGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(workerGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast("ping", new IdleStateHandler(0, sendMillTimes, 0, TimeUnit.MILLISECONDS));
                ch.pipeline().addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                ch.pipeline().addLast("frameEncoder", new LengthFieldPrepender(4));
                ch.pipeline().addLast(new loan.ppcat.godsky.realtime.netty.util.ObjectEncoder());
                ch.pipeline().addLast(new loan.ppcat.godsky.realtime.netty.util.ObjectDecoder());
                DiscardClientHandler discardClientHandler = new DiscardClientHandler(dataTrasferClient);
                ch.pipeline().addLast(discardClientHandler);
                ch.pipeline().addLast(new SendDataToServerHandler());
            }
        });
    }

    public void startToServer() {
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
        initBootstrap();
        connecting();
    }

    public void connecting() {
        new Thread(new ConnectingThread()).start();
    }

    class ConnectingThread implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                try {
                    setCurrentStatus(false);
                    ChannelFuture f = bootstrap.connect(host, port).sync();
                    setCurrentStatus(true);
                    f.channel().closeFuture().sync();
                     break;
                } catch (Exception e) {
                    logger.error("ing reconnecting...");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e1) {
                    }
                      continue;
                }
                 }
            } finally {
                workerGroup.shutdownGracefully();
            }
        }
    }
}
