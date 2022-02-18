package frontend;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.icepdf.ri.util.PropertiesManager;
import java.io.File;//Cross platform solution to view a PDF file

import javax.swing.JScrollPane;

public class ReportViewingForm extends JFrame {
	
	private JPanel contentPane;
	private JScrollPane scrollPane;
	private SwingController control;



	ReportViewingForm() {
		try {
			File pdfFile = new File("out.pdf");
			if (pdfFile.exists()) {
				if (Desktop.isDesktopSupported()) {
						Desktop.getDesktop().open(pdfFile);
			}
				else {System.out.println("Awt Desktop is not supported!");} 
			} 
			else {
				System.out.println("File is not exists!");
			} System.out.println("Done"); }
		catch (Exception ex) {
			ex.printStackTrace();
			} 
		}
	
	public static void main(String[] args) {
		ReportViewingForm fm = new ReportViewingForm();
		fm.setVisible(true);
	}
	}

		  

