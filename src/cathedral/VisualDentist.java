package cathedral;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;

import fundation.*;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Vector;

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
		
		JMenuBar menuB = new JMenuBar();
		JMenu menu = new JMenu("Options");
		menu.setMnemonic(KeyEvent.VK_A);
		menu.getAccessibleContext().setAccessibleDescription(
		        "You can choose the different screens");
		menuB.add(menu);
		
		//Menu Items
		JMenuItem menuItemMaint = new JMenuItem("Maintenance Screen",
                new ImageIcon("tool.png"));
		menuItemMaint.setMnemonic(KeyEvent.VK_B);
		menu.add(menuItemMaint);
		
		JMenuItem menuItemManag = new JMenuItem("Payment Manager Screen",
                new ImageIcon("dol.png"));
		menuItemManag.setMnemonic(KeyEvent.VK_0);
		menu.add(menuItemManag);
		
		frame.setJMenuBar(menuB);
		
		PatPanel patPanel = new PatPanel(this);
		//patPanel.setPreferredSize(new Dimension((int)(width/2), (int)(height*0.65)));

		ProPanel proPanel = new ProPanel(this);
		//proPanel.setPreferredSize(new Dimension((int)(width/2), (int)(height*0.65)));
		
		JPanel payPanel = new InvoiceManagerPanel(this);
		//payPanel.setPreferredSize(new Dimension((int)width, (int)(height*0.35)));
		
		JPanel manager = new JPanel(new BorderLayout());
		manager.add(patPanel, BorderLayout.NORTH);
		manager.add(payPanel, BorderLayout.SOUTH);
		
		JPanel cards = new JPanel(new CardLayout());
		cards.add(manager, "manager");
		cards.add(proPanel, "maintenance");
		
		CardLayout cardLay = (CardLayout)(cards.getLayout());
		
		contentPane.add(cards);
		
		menuItemMaint.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				cardLay.show(cards, "maintenance");
			}
		});
		
		menuItemManag.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				cardLay.show(cards, "manager");
			}
		});
		
		frame.setVisible(true);
		
	}
	
	public Vector<Patient> getPatient(){
		return app.getPatientList();
	}
	
	public Patient getPatient(int n){
		return app.getPatient(n);
	}
	
	public void addPatient(Patient p){
		app.addPatient(p);
	}

	public void updatePatient(int no, String n, String ad, String num){
		app.updatePatient(no, n, ad, num);
	}
	
	public void deletePatient(int n){
		app.deletePatient(n);
	}
	
	public Vector<Procedure> getProcedure(){
		return app.getProcedureList();
	}
	
	public void addProcedure(Procedure p){
		app.addProcedure(p);
	}
	
	public void updateProcedure(int no, String n, double c){
		app.updateProcedure(no, n, c);
	}
	
	public void deleteProcedure(int n){
		app.deletePatient(n);
	}
	
	public static void main(String[] args) {  
		new VisualDentist();
	} 
	
}