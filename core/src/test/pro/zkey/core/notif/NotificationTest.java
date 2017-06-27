package pro.zkey.core.notif;

import org.junit.Test;
import pro.zkey.core.notif.impl.NotificationImpl;
import pro.zkey.core.notif.pojos.MessageDto;

import static org.junit.Assert.*;

/**
 * Created by ps on 2017/6/23.
 */
public class NotificationTest {
    Notification notification = new NotificationImpl();
    @Test
    public void publish() throws Exception {
        notification.publish(new MessageDto(),
                (b,a)->System.out.print(a)
        );
        assertTrue( true );
    }

}