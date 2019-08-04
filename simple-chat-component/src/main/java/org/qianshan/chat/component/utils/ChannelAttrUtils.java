package org.qianshan.chat.component.utils;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import org.qianshan.chat.component.Session;

public class ChannelAttrUtils {

    private static final AttributeKey<Boolean> LOGIN_KEY = AttributeKey.valueOf("LOGIN");
    private static final AttributeKey<Session> SESSION_KEY = AttributeKey.valueOf("SESSION");

    /**
     * 设置channel登录成功
     * @param channel
     */
    public static void login(Channel channel){
        channel.attr(LOGIN_KEY).set(true);
    }

    /**
     * 绑定session到channel
     * @param channel
     * @param session
     */
    public static void bindSession(Channel channel,Session session){
        channel.attr(SESSION_KEY).set(session);
    }

    /**
     * 获取channel里面的session
     * @param channel
     * @return
     */
    public static Session getSession(Channel channel){
        return channel.attr(SESSION_KEY).get();
    }

    /**
     * 判断channel是否登录
     * @param channel
     * @return
     */
    public static boolean hasLogin(Channel channel){
        return channel.attr(LOGIN_KEY).get() == null ? false : channel.attr(LOGIN_KEY).get();
    }

    /**
     * 获取channel对应用户的用户名
     * @param channel
     * @return
     */
    public static String getUserName(Channel channel){
        Session session = channel.attr(SESSION_KEY).get();
        if (session != null){
            return session.getUserName();
        }

        return null;
    }

}
