package com.changhf.plugin.sms;

/**
 * SmsClient
 * @author liMengbiao
 *
 */
public abstract class SmsClient {

    /**
     * SmsClient
     * @param mobile
     * @param content
     * @param ext
     * @param stime
     * @param rrid
     * @param msgfmt
     * @return
     */
    public abstract String sendSMS(String mobile, String content, String ext, String stime, String rrid, String msgfmt);

}
