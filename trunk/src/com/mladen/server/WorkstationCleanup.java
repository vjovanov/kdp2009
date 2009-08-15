package com.mladen.server;

import java.util.concurrent.ConcurrentMap;

public class WorkstationCleanup implements Runnable {
	
	private ConcurrentMap<String, WorkstationStatus> activeWorkstations;
	
	public WorkstationCleanup(
			ConcurrentMap<String, WorkstationStatus> activeWorkstations) {
		super();
		this.activeWorkstations = activeWorkstations;
	}



	@Override
	public void run() {
		
		for (ConcurrentMap.Entry<String, WorkstationStatus> workstationStatus : activeWorkstations.entrySet()) {
			if (workstationStatus.getValue().getExpiryTime() > System.currentTimeMillis()) {
				activeWorkstations.remove(workstationStatus.getKey());
			}
		}	
	}	
}
