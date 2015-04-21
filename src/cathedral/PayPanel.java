package cathedral;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import fundation.Invoice;
import fundation.MyDate;
import fundation.Patient;
import fundation.Payment;
import fundation.Procedure;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class PayPanel extends JPanel{
	private static final long serialVersionUID = -775143490271160762L;
	public VisualDentist visual;
	public InvGlobalPanel invGlob;
	private JTable table;
	private JTable tableProc;
	private JList<String> listProc;
	private int currentListProcNo;
	
	public <visual> PayPanel(VisualDentist vd, InvGlobalPanel igp){
		super();
		visual=vd;
		invGlob = igp;
		currentListProcNo = -1;
		setLayout(new BoxLayout(this,1));		
		//////////////// Infos Pan //////////////////////////////
		JPanel infosPan = new JPanel();
		JTextField payDate = new JTextField(10);
		JLabel payD = new JLabel("Date of the payment : ");
		Dimension def = new Dimension(400,20);
		payDate.setPreferredSize(def);
		payDate.setSize(def);
		//String s = "Default Procedure Name";
		//procname.setText(s);
		infosPan.add(payD);
		infosPan.add(payDate);
		JLabel payA = new JLabel("Amount of the procedure : ");
		JTextField payAmount = new JTextField(10);
		payAmount.setText("0.0");
		MyDate today = new MyDate();
		today.getToToday();
		payDate.setText(today.toString());
		payDate.setSize(def);
		payAmount.setPreferredSize(def);
		infosPan.add(payA);
		infosPan.add(payAmount);
		//infosPan.setBackground(Color.GREEN);
		//setPreferredSize(new Dimension(1000, 20));
		infosPan.setVisible(true);
		add(infosPan);
		/////////////////////////////////////////////////////////
		
		////////////////Button Pan /////////////////////////////// 
		JPanel buttPan = new JPanel();
		JButton updateProc = new JButton("Update Procedures");
		buttPan.add(updateProc);
		JButton addProc = new JButton("Add Procedure to Invoice");
		buttPan.add(addProc);
		JButton delProc = new JButton("Delet Procedure from Invoice");
		buttPan.add(delProc);
		JButton addPay = new JButton("Add Payment to Invoice");
		buttPan.add(addPay);
		JButton delPay = new JButton("Delete Payment from Invoice");
		buttPan.add(delPay);
		buttPan.setVisible(true);
		add(buttPan);
		/////////////////////////////////////////////////////////
		
		////////////// List Pan /////////////////////////////////
		JPanel listPan = new JPanel();
		
			// Panel with the table and the list of procedures
			JPanel procPan = new JPanel();
			procPan.setLayout(new BorderLayout());
			JLabel select = new JLabel("Please select the procedure among the list :");
			procPan.add(select, BorderLayout.PAGE_START);
			DefaultListModel<String> listModel=new DefaultListModel<String>();
			for(Procedure proc: visual.getProcedure()){
				listModel.addElement(proc.getProcName());
			}
			listProc = new JList<String>();
			listProc.setModel(listModel);
			DefaultListCellRenderer renderer =  (DefaultListCellRenderer)listProc.getCellRenderer();  
			renderer.setHorizontalAlignment(JLabel.CENTER);  
			listProc.setBorder(new LineBorder(Color.BLACK));
			listProc.setMaximumSize(new Dimension(400, 130));
			procPan.add(listProc, BorderLayout.CENTER);
			
			procPan.setVisible(true);
			listPan.add(procPan);
		
			JPanel tabProcPan = new JPanel();
			tabProcPan.setLayout(new BorderLayout());
			String[] columnNamesProc = {"Procedure Name",
		               "Procedure Cost", "ID"};
			Object[][] dataProc = {
				};
			tableProc = new JTable(new DefaultTableModel(dataProc, columnNamesProc) {
				private static final long serialVersionUID = -7729723834826859596L;

				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			});
			JScrollPane scrollPaneProc = new JScrollPane(tableProc);
			scrollPaneProc.setPreferredSize(new Dimension(400,130));
			tableProc.setFillsViewportHeight(true);
			tabProcPan.add(scrollPaneProc, BorderLayout.NORTH);
			tableProc.removeColumn(tableProc.getColumnModel().getColumn(2));
			tabProcPan.setVisible(true);
			listPan.add(tabProcPan);
			
			// The panel with the payments
			JPanel tabPan = new JPanel();
			tabPan.setLayout(new BorderLayout());
			String[] columnNames = {"Payment Date",
		               "Payment Amount", "ID"};
			Object[][] data = {
				};
			table = new JTable(new DefaultTableModel(data, columnNames) {
				private static final long serialVersionUID = 3624857536438249140L;

				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			});
			JScrollPane scrollPane = new JScrollPane(table);
			scrollPane.setPreferredSize(new Dimension(400,130));
			table.setFillsViewportHeight(true);
			tabPan.add(scrollPane, BorderLayout.NORTH);
			table.removeColumn(table.getColumnModel().getColumn(2));
			tabPan.setVisible(true);
			listPan.add(tabPan);
			
		listPan.setVisible(true);
		add(listPan);
		////////////////////////////////////////////////////////////////////
		
		/////////////////////////// Action //////////////////////////////////
		updateProc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultListModel<String> listModel = (DefaultListModel<String>) listProc.getModel();
		        listModel.removeAllElements();
				for(Procedure proc: visual.getProcedure()){
					listModel.addElement(proc.getProcName());
				}
				
			}
		});
		addProc.addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent e){
		        	int invNo = invGlob.getInvPan().getCurrentInvoiceNo();
		        	if(listProc.getSelectedIndex()!=-1 && invNo != -1){
		        		DefaultTableModel model = (DefaultTableModel) tableProc.getModel();
		        		Procedure p = visual.getProcedure(currentListProcNo);
			        	Patient pat = visual.getPatient(visual.getPatPanel().getCurrentPatientNo());
			        	Invoice i = pat.getInvoice(invNo);
			        	i.addProc(p);
			        	invGlob.getInvPan().updateInv(i);
			        	model.addRow(visual.getProPanel().getRow(currentListProcNo));
	        		} else if(invNo == -1) {
	        			JOptionPane.showMessageDialog(visual.getFrame(),"Please, select an Invoice !");
	        		} else {
	        			JOptionPane.showMessageDialog(visual.getFrame(),"Please, select a Procedure !");
	        		}
		        }
		});
		
		addPay.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e){
	        	if(invGlob.getInvPan().getCurrentInvoiceNo()!=-1){
					DefaultTableModel model = (DefaultTableModel) table.getModel();
					//Exception not good format
					Payment p = new Payment(Double.parseDouble(payAmount.getText()), 
							new MyDate(payDate.getText()));
		        	int invNo = invGlob.getInvPan().getCurrentInvoiceNo();
		        	Patient pat = visual.getPatient(visual.getPatPanel().getCurrentPatientNo());
		        	Invoice i = pat.getInvoice(invNo);
		        	i.addPayment(p);
		        	invGlob.getInvPan().updateInv(i);
		        	model.addRow(new String[] { p.getPaymentDate().toString(),
		        			p.getPaymentAmt()+"",p.getPaymentNo()+""});
	        	} else {
	        		JOptionPane.showMessageDialog(visual.getFrame(),"Please, select an Invoice !");
	        	}
	        }
		});
		
		delProc.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e){
	        	int row = tableProc.getSelectedRow();
	        	if(row != -1){
		        	DefaultTableModel modelProc = (DefaultTableModel) tableProc.getModel();
		        	//Exception Handling ArrayIndexOutOfBoundException
		        	Patient p = visual.getPatient(visual.getPatPanel().getCurrentPatientNo());
		        	int invNo = invGlob.getInvPan().getCurrentInvoiceNo();
		        	p.deleteProc(invNo, Integer.parseInt((String)modelProc.getValueAt(row, 2)));
		        	//modelProc.removeRow(row);
		        	invGlob.getInvPan().updateInv(p.getP_invoiceList());
	        	} else {
	        		JOptionPane.showMessageDialog(visual.getFrame(),"Please, select an Procedure!");
	        	}
	        }
		});
		
		delPay.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e){
	        	int row = table.getSelectedRow();
	        	if(row != -1){
		        	DefaultTableModel model = (DefaultTableModel) table.getModel();
		        	//Exception Handling ArrayIndexOutOfBoundException
		        	Patient p = visual.getPatient(visual.getPatPanel().getCurrentPatientNo());
		        	int invNo = invGlob.getInvPan().getCurrentInvoiceNo();
		        	p.deletePayment(invNo, Integer.parseInt((String)model.getValueAt(row, 2)));
		        	System.out.println(row);
		        	model.removeRow(row);
		        	invGlob.getInvPan().updateInv(p.getP_invoiceList());
	        	} else {
	        		JOptionPane.showMessageDialog(visual.getFrame(),"Please, select a Payment!");
	        	}
	        }
		});

		listProc.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {
	        	if(!event.getValueIsAdjusting()){
	        		if(listProc.getSelectedIndex()==-1){
	        			currentListProcNo = -1;
	        			invGlob.getPayPan().deleteAll();
	        		} else {
			            currentListProcNo = visual.getProcNo((String)listProc.getSelectedValue());
	        		}
	        	}
	        }
	    });
		
		tableProc.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {
	        	if(!event.getValueIsAdjusting()){
				}
	        }
	    });
		/////////////////////////////////////////////////////////////////////////////
		
		setVisible(true);	
	}
	
	// Updates the payments according to the vector in parameters
	public void updatePay(Vector<Payment> v){
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		for(int i = model.getRowCount() - 1; i > -1; i--){
		// Exception Handling ArrayIndexOutOfBoundException
		model.removeRow(i);
		}
		for(Payment p : v){
			String[] rowData = {p.getPaymentDate().toString(),
        			p.getPaymentAmt()+"",p.getPaymentNo()+""};
			model.addRow(rowData);
		}
	}
	
	// Updates the procedures according to the vector in parameters
	public void updateProc(Vector<Procedure> v){
		DefaultTableModel modelProc = (DefaultTableModel) tableProc.getModel();
		for(int i = modelProc.getRowCount() - 1; i > -1; i--){
		// Exception Handling ArrayIndexOutOfBoundException
		modelProc.removeRow(i);
		}
		for(Procedure p : v){
			modelProc.addRow(visual.getProPanel().getRow(p.getProcNo()));
		}
	}
	
	public void deleteAll(){
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		for(int i = model.getRowCount() - 1; i > -1; i--){
			// Exception Handling ArrayIndexOutOfBoundException
			model.removeRow(i);
		}
		DefaultTableModel modelProc = (DefaultTableModel) tableProc.getModel();
		for(int i = modelProc.getRowCount() - 1; i > -1; i--){
			// Exception Handling ArrayIndexOutOfBoundException
			modelProc.removeRow(i);
		}
	}
}