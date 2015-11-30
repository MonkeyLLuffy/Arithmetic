package test;

public class QuickSort {

		 public static void main(String[] args) {
		  int [] array = {12, 23, 43, 14, 24, 65, 34, 75, 21, 34 };
		  quickSort(array, 0, array.length - 1);
		  printArray(array);
		 }
		 /**
		  * 先按照数组为数据原型写出算法，再写出扩展性算法。数组{49,38,65,97,76,13,27}
		  * 数组是对象，对象传的内存地址，修改形参会传回实参
		  * */
		 public static void quickSort(int[]n ,int left,int right){
		  int pivot;
		  if (left < right) {
		   //pivot作为枢轴，较之小的元素在左，较之大的元素在右
		   pivot = partition(n, left, right);

			System.out.println("partion "+pivot);
		   //对左右数组递归调用快速排序，直到顺序完全正确
		   quickSort(n, left, pivot - 1);
		   quickSort(n, pivot + 1, right);
		   printArray(n);
		  }
		 }
		 //打印数组
		 private static void printArray(int[] array) {
			// TODO Auto-generated method stub
			 
			 for (int i = 0; i < array.length; i++) {
				   System.out.print(array[i]+" ");
				  }
			 System.out.println();
			
		}
		public static int partition(int[]n ,int left,int right){
		  int pivotkey = n[left];
		  //枢轴选定后永远不变，最终在中间，前小后大
		  while (left < right) {
		   while (left < right && n[right] >= pivotkey) 
			   --right;
		   //将比枢轴小的元素移到低端，此时right位相当于空，等待低位比pivotkey大的数补上
		   n[left] = n[right];
		   while (left < right && n[left] <= pivotkey) 
			   ++left;
		   //将比枢轴大的元素移到高端，此时left位相当于空，等待高位比pivotkey小的数补上
		   n[right] = n[left];
		  }
		  
		  
		  //当left == right，完成一趟快速排序，此时left位相当于空，等待pivotkey补上
		  n[left] = pivotkey;
		  return left;
		 }
}
