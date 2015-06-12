package org.ma.entity;
import java.util.Arrays;

public class FindTest {

	public static int[] init(){
		int[] a = new int[10000];
		for(int i = 0 ; i< a.length; i++){
			a[i] = 1+ (int)(Math.random()*500);
		}
		Arrays.sort(a);
		return a;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] a = init();
		/**
		 *  100 -200
		 */
		int min = 100,max = 200;
		
		

	}
	private static void printArr(int[] a) {
		for(int i = 0 ; i< a.length; i++){
			System.out.println(a[i]); 
		}
		
	}

}
