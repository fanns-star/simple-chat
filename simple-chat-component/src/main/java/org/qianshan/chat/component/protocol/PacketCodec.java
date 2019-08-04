package org.qianshan.chat.component.protocol;

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息的编码解码器
 */
public class PacketCodec {

    private static final Map<Byte, Class> payloadMap = new HashMap<Byte, Class>();
    static {
        payloadMap.put(PayloadTypeEnum.LOGIN.getType(), LoginPayload.class);
        payloadMap.put(PayloadTypeEnum.SEND_MESSAGE.getType(), SendMessagePayload.class);
        payloadMap.put(PayloadTypeEnum.RECEIVE_MESSAGE.getType(), ReceiveMessagePayload.class);
        payloadMap.put(PayloadTypeEnum.ERROR.getType(), ErrorPayload.class);
    }

    /**
     * 解码
     * @param byteBuf
     * @return
     */
    public static Packet decode(ByteBuf byteBuf){
        short header = byteBuf.readUnsignedByte();
        boolean needReply = (header & 1) == 1 ? true : false;
        boolean isRequest = (header & 2) == 2 ? true : false;
        byte type = (byte) (header >> 2);
        long packetId = byteBuf.readUnsignedInt();

        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);

        Payload payload = JSONObject.parseObject(bytes, payloadMap.get(type));
        Packet packet = new Packet(type, isRequest, needReply, packetId, payload);
        return packet;
    }

    /**
     * 编码
     * @param packet
     * @param byteBuf
     * @return
     */
    public static ByteBuf encode(Packet packet, ByteBuf byteBuf){

        int payloadLength = 0;
        String payloadStr = null;
        if (packet.getPayload() != null){
            payloadStr = JSONObject.toJSONString(packet.getPayload());
            payloadLength = payloadStr.getBytes().length;
        }

        byteBuf.writeInt(payloadLength);
        short header = (short) ((short) packet.getType() << 2);
        if (packet.isRequest()){
            header = (short) (header | 2);
        }

        if (packet.isNeedReply()){
            header = (short) (header | 1);
        }

        byteBuf.writeByte(header);
        byteBuf.writeInt((int) packet.getPacketId());
        if (payloadStr != null){
            byteBuf.writeBytes(payloadStr.getBytes());
        }

        return byteBuf;
    }

//    public static void main(String[] args) {
//        ByteBuf byteBuf = UnpooledByteBufAllocator.DEFAULT.buffer();
//        SendMessagePayload payload = new SendMessagePayload("all", "hello");
//        Packet packet = new Packet(PayloadTypeEnum.SEND_MESSAGE.getType(), true, true, 1, payload);
//        PacketCodec.encode(packet, byteBuf);
//        byteBuf.readUnsignedInt();
//        System.out.println(PacketCodec.decode(byteBuf));
//    }

}
