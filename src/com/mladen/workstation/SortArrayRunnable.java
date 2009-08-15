package com.mladen.workstation;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.Callable;

import com.mladenice.message.CommandType;
import com.mladenice.message.MessageImpl;

public class SortArrayRunnable implements Runnable {

	private long[] array;
	private String arrayId;
	private String serverAddress;
	private int port;


	public SortArrayRunnable(long[] array, String arrayId,
			String serverAddress, int port) {
		super();
		this.array = array;
		this.arrayId = arrayId;
		this.serverAddress = serverAddress;
		this.port = port;
	}


	@Override
	public void run() {
		Arrays.sort(array);
		
		try {
			Socket socket = new Socket(InetAddress.getByName(serverAddress), port);
			ObjectOutputStream out = new ObjectOutputStream(
					new BufferedOutputStream(socket.getOutputStream()));
			
			out.writeObject(new MessageImpl(CommandType.SORTING_COMPLETE, array, arrayId));
			out.flush();
			out.close();
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
		// connect to main server 
		// send sorted array back 
	}

	
}
