package cathedral;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import fundation.Invoice;
import fundation.MyDate;
import fundation.Patient;
import fundation.Procedure;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class InvPanel extends JPanel{
	public VisualDentist visual;
	public InvGlobalPanel invGlob;
	private int currentInvoiceNo;
	private JTable table;

	public InvPanel(VisualDentist vd, InvGlobalPanel ivg){
		super();
		visual=vd;
		invGlob=ivg;
		currentInvoiceNo = -1;
		
		setLayout(new BoxLayout(this, 1));
		
		////////////////Button Pan /////////////////////////////// 
		JPanel buttPan = new JPanel();
		JButton create = new JButton("Create new Invoice");
		buttPan.add(create);
		JButton delete = new JButton("Delete selected Invoice");
		buttPan.add(delete);
		JLabel d = new JLabel("Date : ");
		buttPan.add(d);
		JTextField dateInv = new JTextField(10);
		Dimension def = new Dimension(400,20);
		dateInv.setPreferredSize(def);
		dateInv.setSize(def);
		buttPan.add(dateInv);
		MyDate today = new MyDate();
		today.getToToday();
		dateInv.setText(today.toString());
		buttPan.setVisible(true);
		add(buttPan);
		/////////////////////////////////////////////////////////
		
		
		JPanel subPan = new JPanel();
		subPan.setLayout(new FlowLayout());
		
		//////////////// Invoice Pan /////////////////////////////// 
		JPanel invPan = new JPanel();
		String[] colName = {"Date",
	            "isPaid", "Amount Due", "ID"};
		String[][] data = {
			};
		table = new JTable(new DefaultTableModel(data, colName){
			@Override
			public boolean isCellEditable(int row, int column) {
		    return false;
		    }
		});
		JScrollPane scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(400,130));
		table.setFillsViewportHeight(true);
		table.removeColumn(table.getColumnModel().getColumn(3));
		invPan.add(scroll, BorderLayout.NORTH);
		subPan.add(invPan);
		/////////////////////////////////////////////////////////
		add(subPan);

		create.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(visual.getPatPanel().getCurrentPatientNo()!=-1){
					DefaultTableModel model = (DefaultTableModel) table.getModel();
					//Exception not good format
					Invoice i = new Invoice(new MyDate(dateInv.getText()));
		        	visual.addInv(i);
		        	model.addRow(new String[] {i.getDate().toString(),i.isPaid()+"",
		        			(i.totProc()-i.totPayment())+"", i.getInvoiceNo()+""});
	        	} else {
	        		JOptionPane.showMessageDialog(visual.getFrame(),"Please, select a Patient !");
	        	}
			}
		});
		
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
	        	if(row != -1){
		        	DefaultTableModel model = (DefaultTableModel) table.getModel();
		        	//Exception Handling ArrayIndexOutOfBoundException
		        	Patient p = visual.getPatient(visual.getPatPanel().getCurrentPatientNo());
		        	p.deleteInv(Integer.parseInt((String)model.getValueAt(row, 3)));
		        	model.removeRow(row);
		        	currentInvoiceNo = -1;
	        	} else {
	        		JOptionPane.showMessageDialog(visual.getFrame(),"Please, select an Invoice!");
	        	}
			}
		});
		
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {
	        	if(!event.getValueIsAdjusting()){
	        		if(table.getSelectedRow()==-1){
	        			currentInvoiceNo = -1;
	        			invGlob.getPayPan().deleteAll();
	        		} else {
			            currentInvoiceNo = Integer.parseInt(
			            		table.getModel().getValueAt(table.getSelectedRow(), 3).toString());
			            invGlob.updatePan(currentInvoiceNo);
	        		}
	        	}
	        }
	    });
		setVisible(true);

	}
	
	public void updateInv(Vector<Invoice> v){
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		for(int i = model.getRowCount() - 1; i > -1; i--){
		// Exception Handling ArrayIndexOutOfBoundException
		model.removeRow(i);
		}
		for(Invoice i: v){
			model.addRow(new String[] {i.getDate().toString(),i.isPaid()+"",
        			(i.totProc()-i.totPayment())+"", i.getInvoiceNo()+""});
		}
		currentInvoiceNo=-1;
	}
	
	public void updateInv(Invoice inv){
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		for(int i = model.getRowCount() - 1; i > -1; i--){
			// Exception Handling
			if (Integer.parseInt((String)model.getValueAt(i,3))==inv.getInvoiceNo()){
					model.setValueAt(inv.isPaid()+"",i,1);
					model.setValueAt((inv.totProc()-inv.totPayment())+"",i,2);
			}
		}
	}

	public int getCurrentInvoiceNo() {
		return currentInvoiceNo;
	}

	public JTable getTable() {
		return table;
	}
	
	public void deleteAll(){
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		for(int i = model.getRowCount() - 1; i > -1; i--){
		// Exception Handling ArrayIndexOutOfBoundException
		model.removeRow(i);
		}
	}

}