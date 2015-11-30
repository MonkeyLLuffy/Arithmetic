package test;

import java.util.Scanner;

public class ListOfPrimeNumbers {

	/**
	 * @param args
	 *            �õ�һ�������ڵĵ������б�
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("������һ��������");
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		int[] primeNumbers = findPrimeNumberList(n);
		for (int i = 0; i < primeNumbers.length; i++) {
			if (primeNumbers[i]!=0) {
				System.out.println(i+" "+primeNumbers[i]);
			}
		}

	}

	public static int[] findPrimeNumberList(int n) {
		int[] A = new int[n+1];
		int[] L = new int[n];
		for (int p = 2; p < n; p++) {// ��n���ڵ�������ֵ������Ԫ��
			A[p] = p;
		}

		for (int p = 2; p < Math.sqrt(n); p++) {// С�ڿ����ŵ�n
			System.out.print("sqrt:"+Math.sqrt(p));
			int j = 0;
			if (A[p] != 0) {
				j = p * p;
			}
			while (j < n) {
				A[j] = 0;
				j = j + p;
			}

		}
		int j = 0;
		for (int i = 2; i < A.length; i++) {
			if (A[i] != 0) {
				L[j] = A[i];
				j++;
			}
		}

		return L;

	}

}
