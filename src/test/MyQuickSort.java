package test;

public class MyQuickSort {
	public static void main(String[] args) {

		int array[] = { 12, 23, 43, 14, 24, 65, 34, 75, 21, 34 };
		/*
		 * System.out.println("����ǰ�����飺"); printArray(array);
		 */

		quickSort(array, 0, array.length - 1);
		/*
		 * System.out.println("����������:" );
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
		while ((i-1) < j) {// i<j�����������array[i],array[j]
			
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
		// left>right�����,���� �����array[j],��������һ��
		if (i >= j) {
			array[left] = array[j];
			array[j] = p;
		}
		return j;

	}

	// ��ӡ����
	private static void printArray(int[] array) {
		// TODO Auto-generated method stub

		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + " ");
		}
		System.out.println();

	}

}
