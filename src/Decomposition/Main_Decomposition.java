package Decomposition;

import Graphics.FileChooser;
import Decomposition.Decomposition_Helper;

public class Main_Decomposition {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*se alege fisierul*/
		FileChooser sfc = new FileChooser();
		sfc.setVisible(true);
		String fileName = sfc.createFileChooser();
		System.out.println("--------------Problem 1--------------");
		System.out.println("Ati ales fisierul " + fileName);		
		
		if (fileName != null) {
			Decomposition_Helper m = new Decomposition_Helper(fileName);
			//m.SingularValueDecomposition();
			
			System.out.println();
			System.out.println("--------------Problem 2--------------");
			
			m.computeEigenValue(5001);
		}
	}
}