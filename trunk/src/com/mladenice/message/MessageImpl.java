package com.mladenice.message;

import java.io.Serializable;

public class MessageImpl implements Message, Serializable {

	private CommandType command;
	private Object data;
	private String id;
	
	public MessageImpl(CommandType command, Object data, String id) {
		super();
		this.command = command;
		this.data = data;
		this.id = id;
	}

	public CommandType getCommand() {
		return command;
	}

	public Object getData() {
		return data;
	}

	public String getId() {
		return id;
	}
}
