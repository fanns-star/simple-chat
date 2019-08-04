package org.qianshan.chat.client;

import org.qianshan.chat.component.Session;
import org.qianshan.chat.component.protocol.Packet;
import org.qianshan.chat.component.protocol.Payload;
import org.qianshan.chat.component.protocol.PayloadTypeEnum;
import org.qianshan.chat.component.protocol.SendMessagePayload;

import java.util.Scanner;

public class UserInput implements Runnable {

    private Session session;

    public UserInput(Session session) {
        this.session = session;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        String out = String.format("(%s)>>:",session.getUserName());
        while (true){
            System.out.print(out);
            String s = scanner.nextLine();
            if (s == null || s.equals("")){
                System.err.println("输入内容不能为空");
                continue;
            }
            Payload payload = new SendMessagePayload("all",s);
            Packet packet = new Packet(PayloadTypeEnum.SEND_MESSAGE.getType(), true, true, session.getNextPacketId(), payload);
            session.sendMessage(packet);
        }
    }
}
