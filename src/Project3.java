import java.io.*;
import java.util.*;

/**
 * Multithreaded program that reads in two matrices from files
 * then adds them.
 *
 * @author Danny Richmond
 */
public class Project3 {
    
    public static void main(String[] args) throws InterruptedException, FileNotFoundException {
        
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
        
        // Start threads with only one column per thread
        Sum sumObject = new Sum();
        
        // Pull out first column from each matrix
        double column1[] = new double[1000];
        double column2[] = new double[1000];
        Thread thrd = null;
        // Loop over each column
        for(int i = 0; i < 10; i++) {
            // Loop over each row and create single columns
            for(int rw = 0; rw < 1000; rw++) {
                column1[rw] = matrix1[rw][i];
                column2[rw] = matrix2[rw][i];
            }
            // Create and start thread to add the two columns
            thrd = new Thread(new Summation(column1, column2, sumObject));
            thrd.start();
            System.out.println("Thread " + i + " started");
            
            // Merge thread result with matrix3 (the summation matrix)
            for(int a = 0; a < 1000; a++) {
                matrix3[a][i] = sumObject.getSum()[a];
            }
            
        }
        
        for(int b = 0; b < 10; b++) {
            // Needs to join AFTER they have finished
            thrd.join();
            System.out.println("Thread " + b + " finished");
        }
        
        // Print first row of the summation matrix
        System.out.print("First row: ");
        for(int i = 0; i < 10; i++) {
            System.out.print(matrix3[0][i] + " ");
        }
        System.out.println();
        // Print last row of the summation matrix
        System.out.print("Last row:  ");
        for(int i = 0; i < 10; i++) {
            System.out.print(matrix3[999][i] + " ");
        }
        
        // Place entire matrix3 into text file called summationMatrix.txt
        PrintWriter out = new PrintWriter("summationMatrix.txt");
        for(int x = 0; x < 1000; x++) {
            for(int y = 0; y < 10; y++) {
                out.print(matrix3[x][y] + "  ");
            }
            out.println();
        }
    }
}

// This is the runnable class. It takes two columns, one from each big matrix, and adds them together.
class Summation implements Runnable {
    // matrix1 and matrix2 are to be added, matrix3 is the result
    private double[] matrix1;
    private double[] matrix2;
    protected double[] matrix3 = new double[1000];
    private Sum sumValue;
    
    // Constructor for Summation
    public Summation(double[] column1, double[] column2, Sum sumObject) {
        this.matrix1 = column1;
        this.matrix2 = column2;
        this.sumValue = sumObject;
    }
    
    /**
     * Runnable method. It simply adds two elements from each matrix
     * and places the result in matrix3. It then sends matrix3 to 
     * sumValue so it can be retrieved.
     */
    @Override
    public void run() {
        // Loop over all elements in the given column
        for(int i = 0; i < 1000; i++) {
            // Add the two elements and place in matrix3
            matrix3[i] = matrix1[i] + matrix2[i];
        }
        sumValue.setSum(matrix3);
    }
}

/**
 * This Sum class is used to retrieve the results 
 * in the main class from the runnable class.
 * 
 * @author Danny Richmond
 */
class Sum {
    
    private double[] summation;
    
    /**
     * Get sum
     * @return summation
     */
    public double[] getSum() {
        return summation;
    }
    
    /**
     * Set sum
     * @param sum
     */
    public void setSum(double[] sum) {
        this.summation = sum;
    }
}
