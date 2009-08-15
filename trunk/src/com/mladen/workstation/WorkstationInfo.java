package com.mladen.workstation;

import java.io.Serializable;

public class WorkstationInfo implements Serializable {
	
	private int port;
	private String workstationId;
	
	public WorkstationInfo(int port, String workstationId) {
		super();
		this.port = port;
		this.workstationId = workstationId;
	}

	public int getPort() {
		return port;
	}

	public String getWorkstationId() {
		return workstationId;
	}
	
	
}
