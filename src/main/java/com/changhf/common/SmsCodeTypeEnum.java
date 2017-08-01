package com.changhf.common;

public enum SmsCodeTypeEnum {
	USER_REGISTER_TYPE(1, "用户注册"), FIND_PWD_TYPE(2, "找回密码");

	private Integer codeType;
	private String description;

	private SmsCodeTypeEnum(Integer codeType, String description) {
		this.codeType = codeType;
		this.description = description;
	}
	public static SmsCodeTypeEnum getEnumByCodeType(Integer codeType){
		for (SmsCodeTypeEnum codeTypeEnum : SmsCodeTypeEnum.values()) {
			if(codeTypeEnum.getCodeType()==codeType){
				return codeTypeEnum;
			}
		}
		return null;
	}
	public Integer getCodeType() {
		return codeType;
	}

	public void setCodeType(Integer codeType) {
		this.codeType = codeType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
