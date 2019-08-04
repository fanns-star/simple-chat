package org.qianshan.chat.client.listener;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.qianshan.chat.component.Session;
import org.qianshan.chat.component.protocol.LoginPayload;
import org.qianshan.chat.component.protocol.Packet;
import org.qianshan.chat.component.protocol.PayloadTypeEnum;
import org.qianshan.chat.component.utils.ChannelAttrUtils;

import java.util.Scanner;

public class ConnectListener implements ChannelFutureListener {
    public void operationComplete(ChannelFuture future) throws Exception {
        if (future.isSuccess()){
            new Thread(new LoginTask(future.channel())).start();
        }
    }

    class LoginTask implements Runnable{

        private Channel channel;

        public LoginTask(Channel channel) {
            this.channel = channel;
        }

        public void run() {
            Scanner scanner = new Scanner(System.in);
            String userName = null;
            String password = null;
            System.out.println("请输入用户名（1-3个字符）：");
            while (true){
                String s = scanner.nextLine();

                if (userName != null && password == null){
                    password = s;
                    break;
                }

                if (userName == null){
                    userName = s;
                    System.out.println("请输入密码（提示：万水）：");
                }
            }

            Session session = new Session(userName, (byte) 0, this.channel);
            LoginPayload loginPayload = new LoginPayload(userName, (byte) 0, password);
            Packet loginPacket = new Packet(PayloadTypeEnum.LOGIN.getType(), true, true, session.getNextPacketId(), loginPayload);
            session.sendMessage(loginPacket);

            ChannelAttrUtils.bindSession(channel, session);
        }
    }
}
