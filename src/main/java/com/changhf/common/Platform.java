/*
 *@Project: GZJK
 *@Author: bin
 *@Date: 2015年4月20日
 *@Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.changhf.common;

/**
 * @ClassName: Platform
 * @Description: 平台枚举
 * @author zengbin
 * @date 2016年11月09日 下午7:11:37
 */
public enum Platform {
    NEWCITY_APP("NEWCITY_APP", "新城市手机app"),

    NEWCITY_CMS("NEWCITY_CMS", "新城市cms"),

    NEWCITY_WEB("NEWCITY_WEB", "新城市web");

    private String platName;

    private String platDesc;

    private Platform(String platName, String platDesc) {
        this.platName = platName;
        this.platDesc = platDesc;
    }

    public static Platform getPlatform(String platName) {
        for (Platform platform : Platform.values()) {
            if (platform.getPlatName().equals(platName)) {
                return platform;
            }
        }
        return null;
    }

    public String getPlatName() {
        return platName;
    }

    public void setPlatName(String platName) {
        this.platName = platName;
    }

    public String getPlatDesc() {
        return platDesc;
    }

    public void setPlatDesc(String platDesc) {
        this.platDesc = platDesc;
    }
}
