package com.example.pubsub.service;

import com.example.pubsub.config.NettyConfig;
import com.example.pubsub.matcher.AttrMatcher;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liuxun
 */
@Service
public class PushServiceImpl implements PushService{
    @Override
    public void pushMsgToOne(String userId, String msg) {
        ConcurrentHashMap<String, Channel> userChannelMap = NettyConfig.getUserChannelMap();
        Channel channel = userChannelMap.get(userId);
        channel.writeAndFlush(new TextWebSocketFrame(msg));
    }

    @Override
    public void pushMsgToAll(String msg) {
        NettyConfig.getChannelGroup().writeAndFlush(new TextWebSocketFrame(msg));
    }

    @Override
    public void pushMsgOnConditionMap(String msg, Map<String, Object> map, boolean matchAll) {
        NettyConfig.getChannelGroup().writeAndFlush(new TextWebSocketFrame(msg), new AttrMatcher(map,matchAll));
    }
}
