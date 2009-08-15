package com.mladen.workstation;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.mladen.server.MainServer;
import com.mladenice.message.CommandType;
import com.mladenice.message.Message;

public class Workstation extends Thread {

	// Status
	private boolean initialized;

	// Server variables
	private int port;
	private ServerSocket serverSocket;

	// workstation ip address
	private String workstationAddress;

	// Main server variables
	private String serverAddress;
	private int serverPort;
	private int numberOfThreads;

	private ThreadPoolExecutor processingService;
	private LinkedBlockingQueue<FutureTask<long[]>> processedTasks;

	private ScheduledThreadPoolExecutor serverNotificationExecutor;

	private String workStationId;

	public Workstation(int myPort, String serverAddress, int serverPort,
			int numberOfThreads) {
		super();
		this.workStationId = UUID.randomUUID().toString();
		this.port = myPort;
		this.serverAddress = serverAddress;
		this.serverPort = serverPort;
		this.numberOfThreads = numberOfThreads;
		processingService = new ThreadPoolExecutor(1, numberOfThreads, 120,
				TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
		this.setDaemon(true);
	}

	public void initialize() {
		try {
			serverSocket = new ServerSocket(port);
			serverNotificationExecutor = new ScheduledThreadPoolExecutor(1);
			serverNotificationExecutor.scheduleAtFixedRate(
					new ReportToMainServerRunnable(workStationId,
							serverAddress, serverPort, port), 1, 10,
					TimeUnit.SECONDS);
			initialized = true;
		} catch (IOException e) {
			initialized = false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		// stop and pause
		if (!initialized) {
			throw new RuntimeException("Server not initialized");
		}
		for (;;) {
			Message message = null;
			try {
				Socket requestSocket = serverSocket.accept();
				ObjectInputStream messageInput = new ObjectInputStream(
						new BufferedInputStream(requestSocket.getInputStream()));
				message = (Message) messageInput.readObject();
			} catch (ClassNotFoundException cnfe) {
				// TODO log
			} catch (IOException e) {
				// TODO (JM) log
			}
		}
	}

	private void processMessage(Message message) {
		if (message != null) {
			CommandType type = message.getCommand();
			switch (type) {
			case REQUEST_SORT: {
				long[] unsortedArray = (long[]) message.getData();
				processingService.execute(new SortArrayRunnable(unsortedArray, message.getId(), serverAddress, serverPort));
				break;
			}
			case REQUEST_MERGE: {// request merge
				// make processing task for merging
				// after merging send data return data to main server
				long[] unsortedArray = (long[]) message.getData();
				FutureTask<long[]> mergingTask = new FutureTask<long[]>(
						new MergeArrayCallable(unsortedArray));
				// ???
				// processingService.execute(mergingTask)
				break;
			}
			}

		}
	}

	public String getWorkstationAddress() {
		return workstationAddress;
	}
}