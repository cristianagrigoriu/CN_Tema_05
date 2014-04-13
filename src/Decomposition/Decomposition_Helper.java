package Decomposition;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import Jama.Matrix;
import Decomposition.RareMatrixElement;

public class Decomposition_Helper {

	private int n;
	private int p;
	private double[][] a;
	private double[][] b;
	private double[][] initialMatrix;
	private int precision;
	
	static private ArrayList<List<RareMatrixElement>> values; 
	
	public Decomposition_Helper(String fileName) {
		this.readFromFile(fileName);
	}

	public void readFromFile(String fileName){
		int i = 0, j = -1, count = 0;
		Scanner scanner;
		
		try {
			scanner = new Scanner(new File(fileName));
			while(scanner.hasNextDouble()){
				if (count == 0) {
					this.n = (int)scanner.nextDouble();
					count++;
				}
				if (count == 1) {
					this.p = (int) scanner.nextDouble();
					this.initialMatrix = new double[this.n][this.p + 1];
					count++;
					continue;
				}
				if (count == 2) {
					this.precision = scanner.nextInt();
					count++;
					continue;
				}
				if (count > 2) {
					initialMatrix[i][++j] = scanner.nextDouble();
					 if (j == this.p) {
						 i++;
						 j = -1;
					}	  
				}
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
			
		/*we get a copy of matrix A*/
		this.a = new double[this.n][this.p];
		for (i = 0; i < n; i++) {
			  for (j = 0; j < p; j++) {
				  this.a[i][j] = initialMatrix[i][j];
			  }
		}
		
		/**/
		this.b = new double[this.n][1];
		for (i = 0; i < n; i++){
				this.b[i][0] = this.initialMatrix[i][this.p];
		}
		
		this.printMatrix(this.a, "Aceasta este matricea initiala:");
		this.printMatrix(this.b, "Acesta este vectorul termenilor liberi:");
	}
	
	public void computeEigenValue(int number) {
		/*we generate the random simetric matrix*/
		this.generateRandomMatrix(number);
		
		double[] dif;
		
		double[] v = this.report(this.RandomArray(number));
		
		System.out.println(this.euclideanNorm(v));
		
		double[] w = this.multiplyRareMatrixToArray(values, v);
		double lambda = this.scalarProduct(w, v);
		int k = 0;
		
		do {
			v = this.report(w);
			w = this.multiplyRareMatrixToArray(values, v);
			lambda = this.scalarProduct(w, v);
			k++;
			
			dif = this.subtractArrays(w, this.multiplyScalarWithArray(lambda, v));
		} while(this.euclideanNorm(dif) > Math.pow(10, -this.precision) * number && k <= 100);
		
		if (k <= 100) {
			System.out.println("Valoarea proprie a sistemului este: " + lambda);
			//this.printArray(v, "Acesta este vectorul propriu asociat:");
		}
	}
	
	private double[] subtractArrays(double[] array1, double[] array2) {
		double[] result = new double[array1.length];
		
		if (array1.length == array2.length) {
			for (int i=0; i<array1.length; i++)
				result[i] = array1[i] - array2[i];
		}
		return result;
	}
	
	private double[] multiplyScalarWithArray(double number, double[] array) {
		double[] result = new double[array.length];
		
		for (int i=0; i<array.length; i++) {
			result[i] = array[i] * number;
		}
		
		return result;
	}
	
	private double scalarProduct(double[] array1, double[] array2) {
		double result = 0;
		
		if (array1.length == array2.length) {
			for (int i=0; i<array1.length; i++) {
				result += array1[i] * array2[i];
			} 
		}
		
		return result;
	}
	
	private double[] multiplyRareMatrixToArray(ArrayList<List<RareMatrixElement>> rareMatrix, double[] array) {
		double[] result = new double[array.length]; 
		int i, k;
		double sum = 0;
		
		for (i=0; i<result.length; i++) {
			sum = 0;
			/*k goes only over the elements that are not null on the current line*/
			for (k=0; k<values.get(i).size(); k++) {
					
				RareMatrixElement current = values.get(i).get(k);
				
				double e1 = current.getValue();
				double e2 = array[(int) current.getColumnIndex()];
						
				sum += e1 * e2;
			}
			//setez doar daca valoarea calculata e diferita de 0
			if (sum != 0)
				result[i] = sum;
		}
		return result;
	}
	
	private double[] report(double[] array) {
		double[] result = new double[array.length];
		double norm = this.euclideanNorm(array);
		
		for (int i=0; i<result.length; i++)
			result[i] = (double)array[i] / norm;
		
		return result;
	}
	
	private double[] RandomArray(int number) {
	    double[] randomMatrix = new double [number];

	    Random rand = new Random(); 
	    rand.setSeed(System.currentTimeMillis()); 
	    
	    for (int i = 0; i < number; i++) {    
	    	double r = rand.nextDouble() + 1; 
	        randomMatrix[i] = Math.abs(r);
	    }
	    return randomMatrix;
	}
	
	/**
	 * Computes the Euclidean norm of a given vector.
	 * @param vector
	 * @return
	 */
	private double euclideanNorm(double[] vector) {
		double euclideanNorm = 0;
		for (int i=0; i<vector.length; i++)
			euclideanNorm += vector[i] * vector[i];
		euclideanNorm = Math.sqrt(euclideanNorm);
		return euclideanNorm;
	}
	
	/**
	 * Generates a random, simetric matrix, and stores it in a special way
	 * @param number
	 * @return
	 */
	private void generateRandomMatrix(int number) {
		int i, j;
		double random = 0;
		
	    Random rand = new Random(); 
	    rand.setSeed(System.currentTimeMillis()); 
	    
	    values = new ArrayList<List<RareMatrixElement>>(number);
	    
	    /*initialize matrix -> add lists for each line*/
	    for (j=0; j<number; j++) {
			List<RareMatrixElement> newList = new ArrayList<RareMatrixElement>();
			values.add(newList);
		}
	    
	    for (i=0; i<number; i++) {
	    	/*we randomly choose the number of non null elements in a line, ~<= 10*/
	    	Integer numbersPerLine = rand.nextInt(10);
	    	for (j=0; j<numbersPerLine; j++) {
	    		/*we randomly choose the column*/
	            Integer column = rand.nextInt(number - i) + i; 
	            /*we randomly choose the value*/
	            random = rand.nextDouble();
	            this.addToList(random, i, Math.abs(column));
	            this.addToList(random, Math.abs(column), i);
	        }
	    }
	}
	
	private void addToList(double value, int row, int column) {
		boolean alreadyInList = false;
		if (Decomposition_Helper.values.size() != 0) {
			List<RareMatrixElement> l = Decomposition_Helper.values.get(row); 
			for (int i = 0; i < l.size(); i++) {
				RareMatrixElement e = (RareMatrixElement) l.get(i);
				if (e.getColumnIndex() == column) {
					e.setValue(value);
					alreadyInList = true;
				}
			}
			/**/
			if (alreadyInList == false) {
				RareMatrixElement newElement = new RareMatrixElement(value, column);
				Decomposition_Helper.values.get(row).add(newElement);
			}
		}	
	}
	
	public void SingularValueDecomposition () {
		Matrix aMatrix = new Matrix(this.a);
		Matrix bMatrix = new Matrix(this.b);
		
		Jama.SingularValueDecomposition d = new Jama.SingularValueDecomposition(aMatrix);
		double[] singValues = d.getSingularValues();
		System.out.println("Valorile singulare ale matricii sunt:");
		for (int i=0; i<singValues.length; i++)
			System.out.print(singValues[i] + " ");
		
		System.out.println();
		System.out.println("Rangul matricii a este " + aMatrix.rank() + ".");
		
		System.out.println("Numarul de conditionare al matricii este " + d.cond() + ".");
		
		Matrix result = new Matrix(this.n, this.p);
		result = aMatrix.minus(d.getU().times(d.getS()).times(d.getV().transpose()));
		double norm = result.normInf();
		System.out.println("Norma ||A - USV(t)|| este " + norm + ".");
		
		Matrix xMatrix = aMatrix.solve(bMatrix);
		System.out.println("Solutia sistemului Ax=b este:");
		double[][] xArray = xMatrix.getArray();
		for (int i=0; i<xArray.length; i++)
			System.out.print(xArray[i][0] + " ");
	}
	
	private void printValues(ArrayList<List<RareMatrixElement>> bigList) {
		/*print results*/
		System.out.println("---values---");
		int counter = 0;
		for(List<RareMatrixElement> l : bigList) {
			if (l.size() > 0) {
				System.out.println("Line " + counter + ":");
				for (int i = 0; i < l.size(); i++) {
					RareMatrixElement e = l.get(i);
					System.out.println("column: " + e.getColumnIndex() + " value: " + e.getValue() + " ");
				}
			}
			counter++;
		}
	}
	
	private void printArray(double[] array, String message) {
		System.out.println(message);
		for (int i = 0; i < array.length; i++) {
			System.out.println(i + 1 + " " + array[i]);
		}
	}
	
	private void printMatrix(double[][] matrix, String message) {
		System.out.println(message);
		for (int i = 0; i < matrix.length; i++) {
			  for (int j = 0; j < matrix[0].length; j++) {
				  System.out.print(matrix[i][j] + " ");
			  }
			  System.out.println();
		}
	}
}
