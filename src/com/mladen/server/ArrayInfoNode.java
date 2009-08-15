package com.mladen.server;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

// synchronized 
public class ArrayInfoNode {
	private String arrayId; 
	private long[] array;
	private List<long[]> sortedParts; // this is being sent to workstation when finished
	private AtomicBoolean sortingFinished;
	
	private int stepsCompleted;	// number of steps completed?
	private int chunkCount;
	{
		sortedParts = new ArrayList<long[]>();
		sortingFinished = new AtomicBoolean(false);
		stepsCompleted = 0;
		chunkCount = 0;
	}
	
	public ArrayInfoNode(String arrayId, long[] array) {
		super();
		this.arrayId = arrayId;
		this.array = array;
	}

	public String getArrayId() {
		return arrayId;
	}
	
	public synchronized void addSortedPart(long[] sortedPart) {
		sortedParts.add(sortedPart);
	}
	
	public long[] getArray() {
		return array;
	}

	public synchronized List<long[]> getSortedParts() {
		return sortedParts;
	}

	public boolean isSortingFinished() {
		return sortingFinished.get();
	}
	
	public synchronized long[] getNextChunk() {
		int startingIndex = chunkCount * 5000;
		int endingIndex = Math.min(array.length - 1, (chunkCount + 1) * 5000);
		chunkCount++;
		long[] destArray = new long[endingIndex - startingIndex];
		System.arraycopy(array, startingIndex, destArray, 0, endingIndex - startingIndex);
		return destArray;
	}
	
	public synchronized boolean hasMoreChunks() {
		return chunkCount * 5000 < array.length; 
	}
	
	// add sorted part method (synchronized)
		// add sorted part to list
		// check if finished ( compare with length of unsorted array )
		// mark as finished
	
	// check status 
	// merge finished (synchronized)
	
	// set sorted data (synchronized) 
	
}
