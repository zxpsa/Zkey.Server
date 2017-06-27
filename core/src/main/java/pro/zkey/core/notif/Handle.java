package pro.zkey.core.notif;

import pro.zkey.core.notif.pojos.MessageDto;
import pro.zkey.core.notif.pojos.ResultDto;

/**
 * 处理
 * 2017-06-23 11:31:48
 * @author PS
 */
@FunctionalInterface
public interface Handle {
    void excute(MessageDto messageDto, ResultDto resultDto);
}
