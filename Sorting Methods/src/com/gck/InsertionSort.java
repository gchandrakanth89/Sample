package com.gck;

import java.util.Arrays;

public class InsertionSort {
	public static int[] insertionSort(int[] input) {

		int i = 1;
		while (i < input.length) {

			for (int j = 0; j < i; j++) {
				if (input[i] < input[j]) {
					// swap
					int temp = input[i];
					input[i] = input[j];
					input[j] = temp;
				}
			}
			i++;
		}

		return input;
	}

	public static void main(String[] args) {
		int a[] = { 5, 2, 4, 6, 1, 3 };
		int[] insertionSort = insertionSort(a);
		
		System.out.println(Arrays.toString(insertionSort));
	}
}
