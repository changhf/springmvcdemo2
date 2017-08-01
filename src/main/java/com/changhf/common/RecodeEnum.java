package com.changhf.common;

public enum RecodeEnum {
	INSERT_ERROR(-1, "数据库新增失败");

	private Integer code;
	private String msg;

	private RecodeEnum(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
}
