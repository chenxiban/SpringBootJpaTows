package com.cyj.jpa.testsort;

/**
 * 随机产生20个数，并对其进行快速排序
 *
 * @author 刘永浪
 *
 */
public class Quick {
	/**
	 * 交换函数，实现数组中两个数的交换操作
	 *
	 * @param array 待操作数组
	 * @param i     交换数组的第一个下标
	 * @param j     交换数组的第二个下标
	 */
	public static void swap(int[] array, int i, int j) {
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}

	/**
	 * 分治法划分算法
	 *
	 * @param array  待操作数组
	 * @param low    划分中模块的起始地址
	 * @param height 划分中模块的结束地址
	 * @return 基准元素的位置下标
	 */
	public static int quick(int[] array, int low, int height) {
		// 设置第一个数为基准元素
		int pivot = array[low];
		// 从右向左扫描，查找第1个小于pivot的元素
		while (low < height) {
			while (low < height && array[height] >= pivot)
				height--;
			// 表示找到了小于pivot的元素
			if (low < height)
				// 交换后low执行+1操作
				swap(array, low++, height);
			// 从左向右扫描，查找第1个大于pivot的元素
			while (low < height && array[low] <= pivot)
				low++;
			// 表示找到了大于pivot的元素
			if (low < height)
				// 交换后heigh执行-1操作
				swap(array, low, height--);
		}
		// 返回基准元素最终位置下标
		return height;
	}

	/**
	 * 对array快速排序
	 *
	 * @param array  待操作数组
	 * @param low    低位
	 * @param height 高位
	 */
	public static void sort(int[] array, int low, int height) {
		// 记录划分后的基准元素所对应的位置
		int temp;
		// 仅当区间长度大于1时才须排序
		if (low < height) {
			// 对array做划分
			temp = quick(array, low, height);
			// 对左区间递归排序
			sort(array, low, temp - 1);
			// 对右区间递归排序
			sort(array, temp + 1, height);
		}
	}

	public static void main(String[] args) {
		int[] array = new int[20];
		System.out.println("脚本之家测试结果:");
		System.out.print("排序前序列:");
		for (int i = 0; i < array.length; i++) {
			// 随机产生20个0-99的整数
			array[i] = (int) (Math.random() * 100);
			System.out.print(array[i] + " ");
		}
		System.out.print("\n排序后序列:");
		sort(array, 0, array.length - 1);
		for (int i = 0; i < array.length; i++)
			System.out.print(array[i] + " ");
	}
	
}
