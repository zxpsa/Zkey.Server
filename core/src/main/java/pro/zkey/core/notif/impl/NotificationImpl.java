package pro.zkey.core.notif.impl;

import pro.zkey.core.notif.Handle;
import pro.zkey.core.notif.Notification;
import pro.zkey.core.notif.pojos.MessageDto;
import pro.zkey.core.notif.pojos.ResultDto;

/**
 * 通知
 * 2017-06-22 18:07:36
 * @author PS
 */
public class NotificationImpl implements Notification {

    /**
     * 发送通知
     * @param messageDto
     * @param handle
     */
    public void publish(MessageDto messageDto, Handle handle) {
        handle.excute(messageDto,new ResultDto());
    }

    /**
     * 接收通知
     * @param messageDto
     * @param handle
     */
    public void receive(MessageDto messageDto, Handle handle){
        handle.excute(messageDto,new ResultDto());
    }
}
