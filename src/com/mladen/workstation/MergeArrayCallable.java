package com.mladen.workstation;

import java.util.concurrent.Callable;

public class MergeArrayCallable implements Callable<long[]> {

	private long[] array;
	private long[] mergedArray;

	public MergeArrayCallable(long[] array) {
		super();
		this.array = array;
	}

	@Override
	public long[] call() throws Exception {
		// TODO Auto-generated method stub
		if (array.length == 10000) {
			mergeRegular();
		} else {
			mergeIrregular();
		}
		;
		return null;
	}

	private void mergeRegular() {
		// regular (round number) of array length
	}

	private void mergeIrregular() {
		// irregular !(round number) of array length
	}
}
