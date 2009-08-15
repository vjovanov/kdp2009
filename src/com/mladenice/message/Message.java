package com.mladenice.message;

public interface Message {
	
	String getId();
	
	CommandType getCommand();
	
	Object getData();
}
