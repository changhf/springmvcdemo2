package com.changhf.service.user;

import com.changhf.common.exception.DemoBusinessException;
import com.changhf.domain.User;
import com.changhf.service.AbstractTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * @author <a href="mailto:wb-chf309549@alibaba-inc.com">常华锋</a>
 * @version V1.0.0
 * @since 2017/08/28
 */
public class UserServiceTest extends AbstractTest {
    @Autowired
    protected UserService userService;

    @Test
    public void testFindUserByMobile() {
        String mobile = "15136555611";
        Map<String, Object> userMap = userService.findUserByMobile(mobile);

        User user = (User) userMap.get("user");
        if (null == user) {
            throw new DemoBusinessException("user not exist, [mobile=%s]", mobile);
        }
        Assert.assertEquals(user.getUserName(), "changhf");
    }

}
