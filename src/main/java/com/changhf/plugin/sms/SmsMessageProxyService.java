package com.changhf.plugin.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.changhf.utils.StringUtils;

/**
 * SmsMessageProxyService
 * @author liMengbiao
 *
 */
@Service
public class SmsMessageProxyService {
	private static final Logger logger = LoggerFactory.getLogger(SmsMessageProxyService.class);

    private static SmsClient client = null;
    private String vendorName = "aipai";

    /**
     * sendSMS
     * @param mobile
     * @param content
     * @param sn
     * @param pwd
     * @return
     */
    public String sendSMS(String mobile, String content, String sn, String pwd) {
        if (StringUtils.isEmpty(vendorName)) {
            throw new IllegalArgumentException("vendorRoot is null");
        }
        String result = null;
        if ("aipai".equals(vendorName)) {
            client = new SmsAiPaiMessageClient(sn, pwd);
            result = client.sendSMS(mobile, content, "0", "", "", "");
        } else if("otherVendor".equals(vendorName)){
        	System.out.println("-----something need you do------");
        } else {
            //打印日志
            logger.error("厂商不存在");
        }
        return result;
    }

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

   
}
