package com.ziyi.common.eventBus.utils;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;

import java.util.concurrent.Executor;

/**
 * @author zhy
 * @data 2022/7/7 20:22
 */
public class EventBusUtil {

    private static EventBus eventBus;
    private static AsyncEventBus asyncEventBus;
    private static Executor executor;

    public static void setExecutor(Executor executor) {
        EventBusUtil.executor = executor;
    }

    /**
     * 异步事件单例模式
     *
     * @return
     */
    private static synchronized AsyncEventBus getAsyncEventBus() {
        if (asyncEventBus == null) {
            asyncEventBus = new AsyncEventBus(executor);
        }
        return asyncEventBus;
    }

    /**
     * 同步事件单例模式
     *
     * @return
     */
    private static synchronized EventBus getEventBus() {
        if (eventBus == null) {
            eventBus = new EventBus();
        }
        return eventBus;
    }

    /**
     * 同步发送事件
     *
     * @param event
     */
    public static void post(Object event) {
        getEventBus().post(event);
    }

    /**
     * 同步发送事件
     *
     * @param event
     */
    public static void post(Object event, Object listener) {
        EventBus eventBus = getEventBus();
        eventBus.register(listener);
        eventBus.post(event);
        eventBus.unregister(listener);
    }

    /**
     * 异步发送事件
     *
     * @param event
     */
    public static void asyncPost(Object event) {
        getAsyncEventBus().post(event);
    }

    /**
     * 异步发送事件
     *
     * @param event
     */
    public static void asyncPost(Object event, Object listener) {
        AsyncEventBus eventBus = getAsyncEventBus();
        eventBus.register(listener);
        eventBus.post(event);
        eventBus.unregister(listener);
    }

    /**
     * 监听器注册
     *
     * @param listener
     */
    public static void register(Object listener) {
        getEventBus().register(listener);
        getAsyncEventBus().register(listener);
    }

}
