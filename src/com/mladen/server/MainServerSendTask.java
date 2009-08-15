package com.mladen.server;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

import com.mladenice.message.CommandType;
import com.mladenice.message.MessageImpl;
import com.mladenice.message.TaskType;

public class MainServerSendTask implements Runnable {

	private String arrayId;
	private Object data;
	// structure with all arrays?
	// workstation address info
	private int port;
	private InetAddress workstationAddress;
	private TaskType taskType;

	
	
	public MainServerSendTask(String arrayId, Object data, int port,
			InetAddress workstationAddress, TaskType taskType) {
		super();
		this.arrayId = arrayId;
		this.data = data;
		this.port = port;
		this.workstationAddress = workstationAddress;
		this.taskType = taskType;
	}



	public void run() {
		// TODO Auto-generated method stub
		// connect to sub server
		// send sort array request
		try {
			Socket socket = new Socket(workstationAddress, port);
			ObjectOutputStream out = new ObjectOutputStream(
					new BufferedOutputStream(socket.getOutputStream()));
			switch (taskType) {
			case SORT: {
				out.writeObject(new MessageImpl(CommandType.REQUEST_SORT,
					 data, arrayId));
				out.flush();
				out.close();
			}
			case MERGE: {
				out.writeObject(new MessageImpl(CommandType.REQUEST_MERGE,
						data, arrayId));
				out.flush();
				out.close();
			}
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
