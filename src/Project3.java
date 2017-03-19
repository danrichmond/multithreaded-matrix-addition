import java.io.*;
import java.util.*;

/**
 * Multithreaded program that reads in two matrices from files
 * then adds them.
 * 
 * @author Danny Richmond
 */
public class Project3 {

	public static void main(String[] args) {
		
		// Create three matrices of size 1000x10
		double[][] matrix1 = new double[1000][10];
		double[][] matrix2 = new double[1000][10];
		double[][] matrix3 = new double[1000][10];
		
		// Create scanner to read in bigMatrix1.txt
		Scanner inFile = null;
		try {
			inFile = new Scanner(new File("bigMatrix1.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		// Fill matrix1 with doubles from bigMatrix1.txt
		int row = 0;
		while(inFile.hasNextDouble()) {
			for(int column = 0; column < 10; column++) {
				matrix1[row][column] = inFile.nextDouble();
			}
			row++;
		}
		
		// Change file scanner to bigMatrix2.txt
		try {
			inFile = new Scanner(new File("bigMatrix2.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		// Fill matrix2 with doubles from bigMatrix2.txt
		row = 0;
		while(inFile.hasNextDouble()) {
			for(int column = 0; column < 10; column++) {
				matrix2[row][column] = inFile.nextDouble();
			}
			row++;
		}
		
		// Start threads
		Thread thrd = new Thread(new Summation(matrix1, matrix2));
		thrd.start();
		System.out.println(thrd.getId());
		try {
			thrd.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		// Print threads
		
		// Print first and last rows of summation matrix
		
	}

}

class Summation implements Runnable {
	private double[][] matrix1;
	private double[][] matrix2;
	private double[][] matrix3 = new double[1000][10];
	private Sum sumValue;
	
	public Summation(double[][] matrix1, double[][] matrix2) {
		this.matrix1 = matrix1;
		this.matrix2 = matrix2;
	}

	@Override
	public void run() {
		double sum = 0;
		// Adding to rows and columns correctly??
		for(int row = 0; row < 1000; row++) {
			for(int column = 0; column < 10; column++) {
				sum = matrix1[row][column] + matrix2[row][column];
				matrix3[row][column] = sum;
			}
		}
		sumValue.setSum(matrix3);
	}
	
	public double[][] getSummation() {
		// How to get this matrix3 to the one in main
		return matrix3;
	}
}



// Is this class necessary??
class Sum {
	private double[][] summation;
	
	public double[][] getSum() {
		return summation;
	}
	
	public void setSum(double[][] sum) {
		this.summation = sum;
	}
}