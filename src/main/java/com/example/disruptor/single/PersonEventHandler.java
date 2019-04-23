package com.example.disruptor.single;

import com.lmax.disruptor.EventHandler;

public class PersonEventHandler implements EventHandler<PersonEvent> {

    public PersonEventHandler() {
    	//  DataSendHelper.start();
    }

    @Override
    public void onEvent(PersonEvent event, long sequence, boolean endOfBatch)
            throws Exception {
        Person person = event.getPerson();
        System.out.println(String.format("名字：%s， 年龄：%s， 性别：%s， 电话：%s ", person.getName(), person.getAge(), person.getGender(), person.getMobile()));
    }

}
