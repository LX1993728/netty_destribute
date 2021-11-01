package com.example.pubsub.handler;

import io.netty.channel.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author  liuxun
 * 用于检测channel心跳的handler
 *
 */

@Slf4j
@Component
@ChannelHandler.Sharable
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        //判断evt是否是IdleStateEvent(用于触发用户事件，包含读空闲/写空闲/读写空闲)
        if(evt instanceof IdleStateEvent){
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            String cid = ctx.channel().id().asLongText();
            if(idleStateEvent.state() == IdleState.READER_IDLE){
                log.info(cid + "\t进入读空闲...");
            }else if(idleStateEvent.state() == IdleState.WRITER_IDLE){
                log.info(cid + "\t进入写空闲...");
            }else if(idleStateEvent.state() == IdleState.ALL_IDLE){
                log.info(cid + "\t进入读写空闲...");

                Channel channel = ctx.channel();
                //关闭无用channel，避免浪费资源
                channel.close();
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error(cause.getMessage());
        super.exceptionCaught(ctx, cause);
    }
}



/**
 * 什么是心跳检测
 * 心跳机制是定时发送一个自定义的结构体(心跳包)，让对方知道自己还活着，以确保连接的有效性的机制。
 * 在WebSocket中即判断套接字是否已经与服务器断开，无法使用，此时要清理服务器的该套接字进程以免浪费资源。
 * 心跳包就是客户端定时发送简单的信息给服务器端告诉它还在正常运行。
 *
 * 实例
 * 比如针对客户端每个连接，服务器都会接收并存入一个容器进行统一管理。
 * 客户端的正常结束，服务器会自动清理。
 * 但某些特殊情况下，服务器无法识别。比如打开飞行模式后，关闭客户端，关闭飞行模式，重新打开客户端，
 * 此时容器中并没有清理客户端，而此时又创建了一个客户端连接。
 */