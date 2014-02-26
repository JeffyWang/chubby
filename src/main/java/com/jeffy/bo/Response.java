package com.jeffy.bo;

import java.io.Serializable;

import com.jeffy.constants.MessageConstants;

public class Response implements Serializable {
	private static final long serialVersionUID = -4538451335221843800L;
	private String code;
	private String message;
	
	private Object data = "";

	public Response() {
		this(MessageConstants.CODE, MessageConstants.MESSAGE);
	}

	public Response(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "[code=" + code + ", message=" + message + "]";
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
