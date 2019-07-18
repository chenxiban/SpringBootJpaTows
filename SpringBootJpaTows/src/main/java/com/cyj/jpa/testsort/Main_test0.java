package com.cyj.jpa.testsort;

import java.util.Scanner;

public class Main_test0 {
	public static void main(String args[]) {
		Scanner scan = new Scanner(System.in);
		System.out.println("(小顶堆)请输入堆大小：");
		int m = scan.nextInt();
		Test0 test = new Test0(m);
		int[] a = { 16, 4, 5, 9, 1, 10, 11, 12, 13, 14, 15, 2, 3, 6, 7, 8 };
		for (int i = 0; i < a.length; i++) {
			test.addToSmall(a[i]);
		}
		test.printSmall();
		test.del();
		test.printSmall();
		scan.close();
	}
}
