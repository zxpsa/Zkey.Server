package pro.zkey.core.notif;


import pro.zkey.core.notif.pojos.MessageDto;

/**
 * 通知
 * 2017-06-22 18:07:36
 * @author PS
 */
public interface Notification {

    /**
     * 发送通知
     * @param messageDto
     * @param handle
     */
    void publish(MessageDto messageDto, Handle handle);

    /**
     * 接收通知
     * @param messageDto
     * @param handle
     */
    void receive(MessageDto messageDto, Handle handle);
}
