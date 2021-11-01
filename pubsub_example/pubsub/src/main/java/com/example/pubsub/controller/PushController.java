package com.example.pubsub.controller;

import com.example.pubsub.service.PushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liuxun
 */
@RestController
public class PushController {
    @Autowired
    private PushService pushService;

    /**
     * 推送给所有用户
     * @param msg
     */
    @PostMapping("/pushAll")
    public void pushToAll(@RequestParam("msg") String msg){
        pushService.pushMsgToAll(msg);
    }
    /**
     * 推送给指定用户
     * @param userId
     * @param msg
     */
    @PostMapping("/pushOne")
    public void pushMsgToOne(@RequestParam("userId") String userId,@RequestParam("msg") String msg){
        pushService.pushMsgToOne(userId,msg);
    }

    @PostMapping("/pushIOS")
    public void pushMsgToOne(@RequestParam("msg") String msg){
        Map<String, Object> map = new HashMap<>();
        map.put("devType", "ios");
        pushService.pushMsgOnConditionMap(msg, map, true);
    }
}
