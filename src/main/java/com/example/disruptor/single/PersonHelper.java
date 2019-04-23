package com.example.disruptor.single;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PersonHelper {
    private static PersonHelper instance;
    private static final int BUFFER_SIZE = 256;

    private static boolean inited = false;
    /**
     * ringBuffer的容量，必须是2的N次方
     */

    private RingBuffer<PersonEvent> ringBuffer;
    private BatchEventProcessor<PersonEvent> batchEventProcessor;

    private Disruptor<PersonEvent> disruptor;

    public PersonHelper() {
        ExecutorService executor = Executors.newCachedThreadPool();

        disruptor = new Disruptor<PersonEvent>(PersonEvent.EVENT_FACTORY,
                BUFFER_SIZE, executor, ProducerType.SINGLE,
                new YieldingWaitStrategy());

        EventHandler<PersonEvent> eventHandler = new PersonEventHandler();
        disruptor.handleEventsWith(eventHandler);
        disruptor.start();
    }


    /**
     * 启动消费者线程，实际上调用了AudioDataEventHandler中的onEvent方法进行处理
     */
    public static void start() {
        instance = new PersonHelper();
        inited = true;
    }

    /**
     * 停止
     */
    public static void shutdown() {
        if (!inited) {
            throw new RuntimeException("EncodeHelper还没有初始化！");
        } else {
            instance.doHalt();
        }
    }

    private void doHalt() {
        batchEventProcessor.halt();
    }

    private void doProduce(Person person) {
        ringBuffer=disruptor.getRingBuffer();
        //获取下一个序号
        long sequence = ringBuffer.next();

        //写入数据
        disruptor.get(sequence).setPerson(person);

        //通知消费者该资源可以消费了
        ringBuffer.publish(sequence);
    }

    /**
     * 生产者压入生产数据
     *
     * @param data
     */
    public static void produce(Person person) {
        if (!inited) {
            throw new RuntimeException("EncodeHelper还没有初始化！");
        } else {
            instance.doProduce(person);
        }
    }

}
