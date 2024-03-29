package com.mladen;

public class HeapSort {
	private static int[] a;
	private static int n;

	public static void sort(int[] a0) {
		a = a0;
		n = a.length;
		heapsort();
	}

	private static void heapsort() {
		buildheap();
		while (n > 1) {
			n--;
			exchange(0, n);
			downheap(0);
		}
	}

	private static void buildheap() {
		for (int v = n / 2 - 1; v >= 0; v--)
			downheap(v);
	}

	private static void downheap(int v) {
		int w = 2 * v + 1;
		while (w < n) {
			if (w + 1 < n)
				if (a[w + 1] > a[w])
					w++;
			if (a[v] >= a[w])
				return;
			exchange(v, w);
			v = w;
			w = 2 * v + 1;
		}
	}

	private static void exchange(int i, int j) {
		int t = a[i];
		a[i] = a[j];
		a[j] = t;
	}

}
