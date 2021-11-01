package com.example.pubsub.config;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liuxun
 */
public class NettyConfig {

    /**
     * 定义一个channel组，管理所有的channel
     */
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 存放用户与channel的对应信息，用于给指定的用户发送消息
     */
    private static ConcurrentHashMap<String, Channel> userChannelGroup = new ConcurrentHashMap<>();

    private NettyConfig(){}

    /**
     * 获取channel组
     * @return
     */
    public static ChannelGroup getChannelGroup() {
        return channelGroup;
    }

    /**
     * 获取用户channelMap集合
     * @return
     */
    public static ConcurrentHashMap<String, Channel> getUserChannelMap() {
        return userChannelGroup;
    }
}
