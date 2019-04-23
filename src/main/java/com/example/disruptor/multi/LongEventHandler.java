package com.example.disruptor.multi;

import com.lmax.disruptor.EventHandler;

public class LongEventHandler implements EventHandler<LongEvent> {
    @Override
    public void onEvent(LongEvent longEvent, long l, boolean b) throws Exception {
        System.out.println(String.format("value:%s ",longEvent.getValue()));
    }
}
