package Graphics;

/*
Java Swing, 2nd Edition
By Marc Loy, Robert Eckstein, Dave Wood, James Elliott, Brian Cole
ISBN: 0-596-00408-7
Publisher: O'Reilly 
*/
//SimpleFileChooser.java
//A simple file chooser to see what it takes to make one of these work.
//
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class FileChooser extends JFrame {
	
	public FileChooser() {
		//super("File Chooser Test Frame");
	}
	
	public String createFileChooser() {
		 
		 //setSize(350, 200);
		 setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		 //Container c = getContentPane();
		 //c.setLayout(new FlowLayout());
		 
		 String filelist = null;
		 
		 JButton openButton = new JButton("Choose the file for the matrix");
		
		 File f = new File("C:\\Users\\cristioara\\workspace");
		 
		 // Create a file chooser that opens up as an Open dialog
		 JFileChooser chooser = new JFileChooser();
		 chooser.setCurrentDirectory(f);
		 chooser.setMultiSelectionEnabled(true);
		 int option = chooser.showOpenDialog(FileChooser.this);
		 if (option == JFileChooser.APPROVE_OPTION) {
			 File[] sf = chooser.getSelectedFiles();
			 if (sf.length > 0) filelist = sf[0].getName();
			 	for (int i = 1; i < sf.length; i++) {
			 		filelist += ", " + sf[i].getName();
			    }
			  }
			 return filelist;
	}

}