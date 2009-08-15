package com.mladen.workstation;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

import com.mladenice.message.CommandType;
import com.mladenice.message.MessageImpl;

public class ReportToMainServerRunnable implements Runnable {

	private String workstationId;
	private String mainServerAddress; 
	private int mainServerPort;
	private int port; 
	
	
	
	public ReportToMainServerRunnable(String workstationId,
			String mainServerAddress, int mainServerPort, int port) {
		super();
		this.workstationId = workstationId;
		this.mainServerAddress = mainServerAddress;
		this.mainServerPort = mainServerPort;
		this.port = port;
	}



	@Override
	public void run() {
		try {
			Socket socket = new Socket(InetAddress.getByName(mainServerAddress), mainServerPort);
			ObjectOutputStream out = new ObjectOutputStream(
					new BufferedOutputStream(socket.getOutputStream()));
			
			out.writeObject(new MessageImpl(CommandType.REPORT_PROCESSING_STATION, new WorkstationInfo(port, workstationId), Long.toString(new Random().nextLong())));
			out.flush();
			out.close();
		} catch (UnknownHostException e) {
			// TODO Error recovery to GUI
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Error recovery 
			e.printStackTrace();
		}
	}

}
