import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VisualDentist {
	private MainApplication app;

	public VisualDentist(){
		app = new MainApplication();
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setTitle("Visual Dentist");
		frame.setSize(JFrame.MAXIMIZED_VERT,JFrame.MAXIMIZED_HORIZ);
		
		frame.setLocationRelativeTo(null);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setVisible(true);
		//mainPanel.setBackground(Color.BLACK);
		
		Container contentPane = frame.getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		//Full Screen
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		System.out.println(width);
		
		PatPanel patPanel = new PatPanel();
		//patPanel.setPreferredSize(new Dimension((int)(width/2), (int)(height*0.65)));

		ProPanel proPanel = new ProPanel();
		//proPanel.setPreferredSize(new Dimension((int)(width/2), (int)(height*0.65)));
		
		JPanel payPanel = new PayManPanel();
		//payPanel.setPreferredSize(new Dimension((int)width, (int)(height*0.35)));
		
		contentPane.add(patPanel, BorderLayout.WEST);
		contentPane.add(proPanel, BorderLayout.EAST);
		contentPane.add(payPanel, BorderLayout.SOUTH);
		
		frame.setVisible(true);
		
	}
	
	public static void main(String[] args) {  
		new VisualDentist();
	} 
	
}
