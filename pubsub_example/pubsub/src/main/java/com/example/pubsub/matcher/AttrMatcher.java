package com.example.pubsub.matcher;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelMatcher;
import io.netty.util.AttributeKey;

import java.util.Map;

public class AttrMatcher implements ChannelMatcher {
    private Map<String, Object> conditionMap;
    private boolean matchAll;

    public AttrMatcher(Map<String, Object> conditionMap) {
        this(conditionMap, true);
    }

    public AttrMatcher(Map<String, Object> conditionMap,boolean matchAll) {
        this.conditionMap = conditionMap;
        this.matchAll = matchAll;
    }

    @Override
    public boolean matches(Channel channel) {
        if (conditionMap == null || conditionMap.isEmpty()){
            return false;
        }

        if (matchAll){
            for (Map.Entry<String, Object> entry : conditionMap.entrySet()){
                AttributeKey<Object> attrKey = AttributeKey.valueOf(entry.getKey());
                if (channel.hasAttr(attrKey)){
                    Object attrValue = channel.attr(attrKey).get();
                    if (!attrValue.equals(entry.getValue())){
                        return false;
                    }
                }else {
                    return false;
                }
            }
            return true;
        }else {
            for (Map.Entry<String, Object> entry : conditionMap.entrySet()){
                AttributeKey<Object> attrKey = AttributeKey.valueOf(entry.getKey());
                if (channel.hasAttr(attrKey)){
                    Object attrValue = channel.attr(attrKey).get();
                    if (attrValue.equals(entry.getValue())){
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
