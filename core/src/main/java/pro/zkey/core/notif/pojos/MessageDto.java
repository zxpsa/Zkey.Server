package pro.zkey.core.notif.pojos;

import java.util.Date;

/**
 * 消息传输对象
 */
public class MessageDto {
    /**
     * 单次消息编号
     */
    private String msgCode;

    /**
     * 命令编号
     */
    private String code;

    /**
     * 消息来源的用户Id
     */
    private String sendUserId;

    /**
     * 接收消息的用户Id
     */
    private String receiveUserId;

    /**
     * 消息内容
     */
    private String text;
    /**
     * 消息发送时间
     */
    private Date sendTime;
    /**
     * 消息接受时间
     */
    private Date receiveTime;
}
