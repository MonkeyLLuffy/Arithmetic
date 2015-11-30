package test;

public class MyQuickSort {
	public static void main(String[] args) {

		int array[] = { 12, 23, 43, 14, 24, 65, 34, 75, 21, 34 };
		/*
		 * System.out.println("排序前的数组："); printArray(array);
		 */

		quickSort(array, 0, array.length - 1);
		/*
		 * System.out.println("排序后的数组:" );
		 * 
		 * printArray(array);
		 */

	}

	public static void quickSort(int[] array, int left, int right) {
		int partion;
		if (left < right) {
			partion = part(array, left, right);
			System.out.println("partion "+partion);
			printArray(array);
			quickSort(array, left, partion - 1);
			quickSort(array, partion + 1, right);
		}

	}

	private static int part(int[] array, int left, int right) {
		// TODO Auto-generated method stub
		int p = array[left];
		int i = left+1, j = right, temp;
		while ((i-1) < j) {// i<j的情况，交换array[i],array[j]
			
			while (p > array[i]){
				i++;
			}
			
			while (p < array[j]){
				j--;
			}
			if (i < j) {
				temp = array[i];
				array[i] = array[j];
				array[j] = temp;

			}
		}
		// left>right的情况,交换 中轴和array[j],并撤销上一次
		if (i >= j) {
			array[left] = array[j];
			array[j] = p;
		}
		return j;

	}

	// 打印数组
	private static void printArray(int[] array) {
		// TODO Auto-generated method stub

		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + " ");
		}
		System.out.println();

	}

}
