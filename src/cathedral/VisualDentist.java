package cathedral;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


import fundation.*;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Vector;

public class VisualDentist {
	private MainApplication app;
	private JFrame frame;
	private InvGlobalPanel invGlobPanel;
	private ProPanel proPanel;
	private PatPanel patPanel;
	
	public VisualDentist(){
		app = new MainApplication();
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setTitle("Visual Dentist");
		frame.setSize(JFrame.MAXIMIZED_VERT,JFrame.MAXIMIZED_HORIZ);
		frame.setLocationRelativeTo(null);
		
		Container contentPane = frame.getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		//For the full screen
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		System.out.println(width);
		
		// Creation of the menu bar
		JMenuBar menuB = new JMenuBar();
		JMenu menu = new JMenu("Options");
		menu.setMnemonic(KeyEvent.VK_A);
		menu.getAccessibleContext().setAccessibleDescription(
		        "You can choose the different screens or create the reports");
		menuB.add(menu);
		
		// Creation of the menu's items
		JMenuItem menuItemManag = new JMenuItem("Payment Manager Screen",
                new ImageIcon("dol.png"));
		menuItemManag.setMnemonic(KeyEvent.VK_0);
		menu.add(menuItemManag);
		
		JMenuItem menuItemMaint = new JMenuItem("Maintenance Screen",
                new ImageIcon("tool.png"));
		menuItemMaint.setMnemonic(KeyEvent.VK_B);
		menu.add(menuItemMaint);
		
		menu.addSeparator();
		
		JMenuItem menuAllPatReport = new JMenuItem("Make a report for all Patients",
                new ImageIcon("page.png"));
		menuAllPatReport.setMnemonic(KeyEvent.VK_0);
		menu.add(menuAllPatReport);
		
		JMenuItem menuNotPaidPatReport = 
				new JMenuItem("Make a report for Patients who hadn't paid for six month",
		                new ImageIcon("page.png"));
		menuNotPaidPatReport.setMnemonic(KeyEvent.VK_0);
		menu.add(menuNotPaidPatReport);
		
		// We create the main panels of the application
		
		frame.setJMenuBar(menuB);
		patPanel = new PatPanel(this);
		proPanel = new ProPanel(this);		
		invGlobPanel = new InvGlobalPanel(this);
		
		// This code is for managing the Card Layout
		JPanel manager = new JPanel(new BorderLayout());
		manager.add(patPanel, BorderLayout.NORTH);
		manager.add(invGlobPanel, BorderLayout.SOUTH);
		
		JPanel cards = new JPanel(new CardLayout());
		cards.add(manager, "manager");
		cards.add(proPanel, "maintenance");
		
		CardLayout cardLay = (CardLayout)(cards.getLayout());
		
		contentPane.add(cards);
		
		
		////////////////////////// Actions //////////////////////////
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
		
		menuAllPatReport.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				PrintWriter writer;
				try {
					writer = new PrintWriter("reportAllPatient.txt", "UTF-8");
					writer.println("This is the list of all the patients in the database "+
							"and all their informations :\n");
					Patient c = new Patient();
					Vector<Patient> sortedList = app.getPatientList();
					Collections.sort(sortedList,c);
					for(int i = 0; i < sortedList.size(); i++){
						writer.print(i+" - ");
						writer.println(sortedList.get(i).toString());
	        		}
	        		writer.close();
	        		JOptionPane.showMessageDialog(getFrame(),"Report made at reportAllPatient.txt!");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		});
		
		menuNotPaidPatReport.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				PrintWriter writer;
				try {
					writer = new PrintWriter("reportNotPaidPatient.txt", "UTF-8");
					writer.println("This is the list of all the patients that owe money "+
							"but haven't made a payment in 6 months :\n");
					Payment c = new Payment	();
					Vector<Patient> sortedList = app.getPatientList();
					Collections.sort(sortedList,c);
					Vector<Patient> temp = sortedList;
					for(int i = 0; i < temp.size(); i++){
						if(sortedList.get(i).moneyDue()<=0){
							sortedList.remove(i);
						}
	        		}
					for(int i = 0; i < sortedList.size(); i++){
						writer.print(i+" - ");
						writer.println(sortedList.get(i).toString()+" This patient owes : "
						+sortedList.get(i).moneyDue()+" euros\n");
	        		}
	        		writer.close();
	        		JOptionPane.showMessageDialog(getFrame(),"Report made at reportNotPaidPatient.txt!");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		});
		
		////////////////////////////////////////////////////////////////
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
		app.deleteProcedure(n);
	}
	
	public void updateInv(int no){
		Patient p = getPatient(no);
		Vector<Invoice> v = p.getP_invoiceList();
		invGlobPanel.updateInv(v);
	}

	public InvGlobalPanel getInvGlobPanel() {
		return invGlobPanel;
	}

	public ProPanel getProPanel() {
		return proPanel;
	}

	public PatPanel getPatPanel() {
		return patPanel;
	} 
	
	public JFrame getFrame(){
		return frame;
	}
	
	public int getProcNo(String name){
		return app.getProc(name).getProcNo();
	}
	
	public void addInv(Invoice i){
		int pNo = patPanel.getCurrentPatientNo();
		if(pNo != -1){
			app.getPatient(pNo).addInvoice(i);
		}
	}
	
	public Procedure getProcedure(int no){
		return app.getProc(no);
	}
	
	public MainApplication getApp() {
		return app;
	}
	
	public static void main(String[] args) {  
		new VisualDentist();
	}

	public void setApp(MainApplication app) {
		this.app = app;
	}
	
	public void updateAll(){
		patPanel.updatePat(app.getPatientList());
		proPanel.updateProc(app.getProcedureList());
		app.updateNos();
	}

}
