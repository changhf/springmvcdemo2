package com.changhf.service;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

/**
 * com.changhf.service.AbstractTest
 *
 * @author <a href="mailto:linlong.ll@alibaba-inc.com">根宝</a>
 * @version V1.0.0
 * @since 2017-07-26
 */
@ContextConfiguration(value = { "classpath*:/spring.xml","classpath*:spring/spring-mvc.xml" })
public class AbstractTest extends AbstractTestNGSpringContextTests {
}