package com.mladen;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

import com.mladenice.message.CommandType;
import com.mladenice.message.MessageImpl;

public class Client {
	private String serverAddress;
	private int port;

	public Client(String serverAddress, int port) {
		super();
		this.serverAddress = serverAddress;
		this.port = port;
	}
	
	// making and sending test array for sorting
	public void sendSortRequest() {
		// generate array
		long[] unsortedArray = new long[10000];
		Random random = new Random(System.currentTimeMillis());
		for (int i = 0; i < 10000; i ++) {
			unsortedArray[i] = random.nextLong();
		}
		
		// send sort request to server
		try {
			Socket socket = new Socket(InetAddress.getByName(serverAddress), port);
			ObjectOutputStream out = new ObjectOutputStream(
					new BufferedOutputStream(socket.getOutputStream()));
			
			out.writeObject(new MessageImpl(CommandType.REQUEST_SORT, unsortedArray, Long.toString(new Random().nextLong())));
			out.flush();
			out.close();
//			System.out.println("Client:" + new ObjectInputStream(new BufferedInputStream(socket.getInputStream())).readObject().toString());
		} catch (UnknownHostException e) {
			// TODO Error recovery to GUI
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Error recovery 
			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
			// TODO Remove, will be needed for 2 way communication
//			e.printStackTrace();
		}
	}
}
