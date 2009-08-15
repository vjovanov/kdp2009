package com.mladenice.message;

import java.net.InetAddress;

public class ReceivedMessage extends MessageImpl {
	
	private InetAddress senderAddress;
	
	public ReceivedMessage(CommandType command, Object data, String id, InetAddress senderAddress) {
		super(command, data, id);
		// TODO Auto-generated constructor stub
		this.senderAddress = senderAddress; 
	}
	
	public ReceivedMessage(Message message, InetAddress senderAddress) {
		super(message.getCommand(), message.getData(), message.getId());
		this.senderAddress = senderAddress;
	}

	public InetAddress getSenderAddress() {
		return senderAddress;
	}
	
	
}
