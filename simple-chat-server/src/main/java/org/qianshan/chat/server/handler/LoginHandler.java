package org.qianshan.chat.server.handler;

import io.netty.channel.*;
import org.qianshan.chat.component.exception.ChatException;
import org.qianshan.chat.component.exception.CodeEnum;
import org.qianshan.chat.component.protocol.*;
import org.qianshan.chat.component.Session;
import org.qianshan.chat.component.utils.StringUtils;
import org.qianshan.chat.server.SessionManager;
import org.qianshan.chat.component.utils.ChannelAttrUtils;

/**
 * 登录handler
 */
@ChannelHandler.Sharable
public class LoginHandler extends ChannelInboundHandlerAdapter {

    private static final String LOGIN_SUCCESS_TEMPLATE = "用户【%s】登录成功";

    public static final LoginHandler INSTANCE = new LoginHandler();

    private LoginHandler() {
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Packet packet = (Packet) msg;
        if (packet.getPayload() instanceof LoginPayload){
            LoginPayload loginPayload = (LoginPayload) packet.getPayload();

            //检查登录信息
            checkLogin(loginPayload, packet.getPacketId());

            Packet response = new Packet(PayloadTypeEnum.LOGIN_ACK.getType(), false, false, packet.getPacketId(), null);
            ctx.channel().writeAndFlush(response);

            ChannelAttrUtils.login(ctx.channel());
            Session session = new Session(loginPayload.getUserName(), loginPayload.getGender(), ctx.channel());
            //绑定session
            ChannelAttrUtils.bindSession(ctx.channel(),session);
            //保存session
            SessionManager.addSession(session);

            System.out.println(String.format(LOGIN_SUCCESS_TEMPLATE, session.getUserName()));
            ctx.channel().pipeline().remove(this);
        }else {
            ctx.channel().close();
        }
    }

    /**
     * 检查登录信息
     * @param loginPayload
     * @param packetId
     * @throws ChatException
     */
    private void checkLogin(LoginPayload loginPayload, long packetId) throws ChatException {
        if (StringUtils.isBlank(loginPayload.getUserName())){
            throw new ChatException(CodeEnum.NULL_USER_NAME.getCode(), packetId);
        }

        if (StringUtils.codePointCount(loginPayload.getUserName()) > 3){
            throw new ChatException(CodeEnum.USER_NAME_TOO_LONG.getCode(), packetId);
        }

        Session session = SessionManager.getSession(loginPayload.getUserName());
        if (session != null){
            throw new ChatException(CodeEnum.USER_NAME_EXIST.getCode(), packetId);
        }

        if (!StringUtils.equals(loginPayload.getPassword(), "qianshan")){
            throw new ChatException(CodeEnum.PASSWORD_ERROR.getCode(), packetId);
        }
    }

}
