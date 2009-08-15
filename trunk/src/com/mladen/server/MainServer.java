package com.mladen.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.mladen.workstation.ReportToMainServerRunnable;
import com.mladen.workstation.WorkstationInfo;
import com.mladenice.message.CommandType;
import com.mladenice.message.Message;
import com.mladenice.message.ReceivedMessage;
import com.mladenice.message.TaskType;

public class MainServer extends Thread {
	private int port;
	private ServerSocket serverSocket;
	private boolean initialized;

	private ConcurrentMap<String, WorkstationStatus> activeWorkstations;
	private ConcurrentMap<String, ArrayInfoNode> jobs;
	private ScheduledThreadPoolExecutor serverCleanupExecutor;
	private ExecutorService serverJobsExecutor;

	{
		jobs = new ConcurrentHashMap<String, ArrayInfoNode>();
	}

	public MainServer(int port) {
		this.port = port;
	}

	public void initialize() {
		try {
			serverSocket = new ServerSocket(port);
			activeWorkstations = new ConcurrentHashMap<String, WorkstationStatus>();

			serverCleanupExecutor = new ScheduledThreadPoolExecutor(1);
			serverCleanupExecutor.scheduleAtFixedRate(new WorkstationCleanup(
					activeWorkstations), 1, 2, TimeUnit.SECONDS);

			// DZO thread pool executor
			serverJobsExecutor = new ThreadPoolExecutor(5, 20, 120,
					TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
			// serverJobsExecutor.scheduleAtFixedRate(new MainServerSendTask(),
			// 1, 2, TimeUnit.SECONDS);

			this.setDaemon(true);
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
			ReceivedMessage message = null;
			try {
				Socket requestSocket = serverSocket.accept(); // TODO timeout
				ObjectInputStream messageInput = new ObjectInputStream(
						new BufferedInputStream(requestSocket.getInputStream()));
				message = new ReceivedMessage((Message) messageInput
						.readObject(), requestSocket.getInetAddress());

				// requestSocket.close();
				// send to processing queue
			} catch (ClassNotFoundException cnfe) {
				// TODO log
			} catch (IOException e) {
				// TODO (JM) log
			}
			processMessage(message);
		}
	}

	private void processMessage(ReceivedMessage message) {
		if (message != null) {
			CommandType type = message.getCommand();
			switch (type) {
			case REQUEST_SORT: {
				// create info node
				long[] unsortedArray = (long[]) message.getData();
				ArrayInfoNode node = new ArrayInfoNode(message.getId(), unsortedArray);
				jobs.put(node.getArrayId(), node);
				
				// send tasks to sub servers here from another process
				for (;node.hasMoreChunks();) {
					WorkstationStatus workstationStatus = getBestWorkstation();
					serverJobsExecutor.execute(new MainServerSendTask(node.getArrayId(), node.getNextChunk(),
							workstationStatus.getPort(), workstationStatus.getAddress(), TaskType.SORT));
				}

				break;
			}
			case REPORT_PROCESSING_STATION: {
				WorkstationInfo info = (WorkstationInfo) message.getData();
				activeWorkstations.put(info.getWorkstationId(),
						new WorkstationStatus(message.getSenderAddress(), info
								.getWorkstationId(), info.getPort(),
								System.currentTimeMillis() + 100000));
				System.out.println("Reporting workstation: "
						+ info.getWorkstationId());
				break;
			}
			
			case SORTING_COMPLETE: {
				jobs.get(message.getId()).addSortedPart((long[]) message.getData());
				System.out.println(Arrays.toString((long[]) message.getData()));
				break;
			}
			// array sorted
				// get array info
				// store sorted part

				//
				//
				// request sorting (client)
				// fetch array info node (by id)
				// if finished pass the data back
				// else
				// return progress ( number of sorted nodes )
			case REQUEST_JOB_STATUS: {
				break;
			}

				// array merged
				// ArrayInfoNode set sorted data (mark as finished)
			}
		}
	}
	
	private WorkstationStatus getBestWorkstation() {
		// what if there are no workstations  
		for (WorkstationStatus status : activeWorkstations.values()) {
			return status;
		}
		return null;
	}

}
