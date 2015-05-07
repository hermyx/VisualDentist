package cathedral;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;







import database.DBCreator;
import database.DBQuery;
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
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Vector;

public class VisualDentist {
	private MainApplication app;
	private JFrame frame;
	private InvGlobalPanel invGlobPanel;
	private ProPanel proPanel;
	private PatPanel patPanel;
	
	
	public VisualDentist() throws SQLException{
		app = new MainApplication();
		frame = new JFrame();
		DBCreator.makeCon();
		DBQuery.makeCon();
		DBCreator.create();
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
		try {
			new VisualDentist();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setApp(MainApplication app) {
		this.app = app;
	}
	
	public void updateAll(){
		patPanel.updatePat(app.getPatientList());
		proPanel.updateProc(app.getProcedureList());
		app.updateNos();
	}
	
	public void saveDB(){
		try{
			String statement = "";
			int invLID = 0;
			int payLID = 0;
			int procLID = 0;
			for(Patient pat: app.getPatientList()){
				statement="update pat set pInvoiceList = "+invLID
						+ "where pID = "+pat.getPatientNo();
				DBCreator.exec(statement);
				for(Invoice inv: pat.getP_invoiceList()){
					int invID = inv.getInvoiceNo();
					String invDate = inv.getDate().getDay()+"/"+inv.getDate().getMonth();
					boolean invIsPaid = inv.isPaid();
					statement = "insert into inv (iID, iDate, iIsPaid, iProcedureList, iPaymentList) values "
		        			+ "("+invID+", '"+invDate+"', '"+invIsPaid+ "', "+ procLID +", "+payLID +")";
					DBCreator.exec(statement);
					statement = "insert into invl (invID, invLID) values"
							+ "("+invID+", "+invLID+")";
					DBCreator.exec(statement);
					for(Payment pay: inv.getP_paymentList()){
						int payID = pay.getPaymentNo();
						String payDate = pay.getPaymentDate().getDay()+"/"+pay.getPaymentDate().getMonth();
						double payAm = pay.getPaymentAmt();
						statement = "insert into pay (pID, pDate, pAmount) values "
			        			+ "("+payID+", '"+payDate+"', "+payAm +")";
						DBCreator.exec(statement);
						statement = "insert into payl (payID, payLID) values"
								+ "("+payID+", "+payLID+")";
						DBCreator.exec(statement);
					}
					payLID++;
					for(Procedure proc: inv.getP_procList()){
						int procID = proc.getProcNo();
						statement = "insert into procl (procID, procLID) values"
								+ "("+procID+", "+procLID+")";
						DBCreator.exec(statement);
					}
					procLID++;
				}
				invLID++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	public MainApplication loadDB(Vector<Procedure> procs){
		MainApplication app = null;
		try {
			Vector<Patient> patList = new Vector<Patient>(0);
			String statement = "select * from pat";
			ResultSet rs = DBQuery.executeQuery(statement);
			ResultSetMetaData rsmd = DBQuery.getMetadata(statement);
			int columnCount = rsmd.getColumnCount();
			//We create an array to put the result of the query in it
			//This way it's more simple to manipulate the data.
			String patient[] = new String[columnCount];
			int k=0;
			while(rs.next()){
				for(int i=0; i<columnCount;i++){
					patient[i]=rs.getString(i+1);
				}
				Patient p = new Patient(patient[1],patient[2],patient[3]);
				p.setPatientNo(Integer.parseInt(patient[0]));
				Patient.setNo(Math.max(Patient.getNo(),p.getPatientNo()));
				int invLID = Integer.parseInt(patient[4]);
				statement = "select * from inv, invL where inv.iID=invL.invID AND invL.invLID="+invLID;
				ResultSet rs2 = DBQuery.executeQuery(statement);
				ResultSetMetaData rsmd2 = DBQuery.getMetadata(statement);
				int columnCount2 = rsmd2.getColumnCount();
				String invoice[] = new String[columnCount2];
				while(rs2.next()){
					for(int i=0; i<columnCount2;i++){
						invoice[i]=rs2.getString(i+1);
					}
					MyDate dateI = null;
					try{
					dateI = new MyDate(invoice[2]);
					} catch (Exception e){
						JOptionPane.showMessageDialog(getFrame(),""
								+ "There was a problem with the Invoice's date on the loading!");
					}
					Invoice i = new Invoice(dateI);
					i.setInvoiceNo(Integer.parseInt(invoice[0]));
					Invoice.setNo(Math.max(Invoice.getNo(),i.getInvoiceNo()));
					int payLID = Integer.parseInt(invoice[3]);
					statement = "select * from pay, payL where pay.pID=payL.payID AND payL.payLID="+payLID;
					ResultSet rs3 = DBQuery.executeQuery(statement);
					ResultSetMetaData rsmd3 = DBQuery.getMetadata(statement);
					int columnCount3 = rsmd3.getColumnCount();
					String payment[] = new String[columnCount3];
					while(rs3.next()){
						for(int j=0; j<columnCount3;j++){
							payment[j]=rs3.getString(j+1);
						}
						MyDate dateP = null;
						try{
						dateP = new MyDate(invoice[2]);
						} catch (Exception e){
							JOptionPane.showMessageDialog(getFrame(),""
									+ "There was a problem with the Payment's date on the loading!");
						}
						double am = Double.parseDouble(payment[2]);
						Payment pay = new Payment(am, dateP);
						pay.setPaymentNo(Integer.parseInt(payment[0]));
						Payment.setNo(Math.max(Payment.getNo(),pay.getPaymentNo()));
						i.addPayment(pay);
					}
					int procLID = Integer.parseInt(invoice[4]);
					statement = "select * from proc, procL where proc.pID=procL.procID AND procL.procLID="+procLID;
					ResultSet rs4 = DBQuery.executeQuery(statement);
					ResultSetMetaData rsmd4 = DBQuery.getMetadata(statement);
					int columnCount4 = rsmd4.getColumnCount();
					String proc[] = new String[5];
					while(rs4.next()){
						for(int j=0; j<columnCount4;j++){
							proc[j]=rs4.getString(j+1);
						}
						i.addProc(this.app.getProc(Integer.parseInt(proc[0])));
					}
					p.addInvoice(i);
				}
				patList.add(p);
			}
			app = new MainApplication(patList, procs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return app;
	}
}
