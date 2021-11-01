package com.example.pubsub.service;

import java.util.Map;

/**
 * 具体的消息推送接口
 * @author liuxun
 */
public interface PushService {
    /**
     * 推送给指定用户
     * @param userId
     * @param msg
     */
    void pushMsgToOne(String userId,String msg);

    /**
     * 推送给所有用户
     * @param msg
     */
    void pushMsgToAll(String msg);

    /**
     * 推送给指定条件的用户
     * @param map
     * @param msg
     * @param matchAll
     */
    void pushMsgOnConditionMap(String msg,Map<String, Object> map, boolean matchAll);
}
