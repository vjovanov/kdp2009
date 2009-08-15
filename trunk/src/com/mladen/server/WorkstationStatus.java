package com.mladen.server;

import java.net.InetAddress;

public class WorkstationStatus {
	private InetAddress address;
	private String workstationId;
	private long expiryTime;
	private int port;
	
	public WorkstationStatus(InetAddress address, String workstationId, int port,
			long expiryTime) {
		super();
		this.address = address;
		this.workstationId = workstationId;
		this.expiryTime = expiryTime;
		this.port = port;
	}

	public long getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(long expiryTime) {
		this.expiryTime = expiryTime;
	}

	public int getPort() {
		return port;
	}

	public InetAddress getAddress() {
		return address;
	}

	public String getWorkstationId() {
		return workstationId;
	}
}
