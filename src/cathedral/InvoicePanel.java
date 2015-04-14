package cathedral;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import fundation.Invoice;
import fundation.MyDate;
import fundation.Patient;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class InvoicePanel extends JPanel{
	public VisualDentist visual;

	public InvoicePanel(VisualDentist vd){
		super();
		visual=vd;
		setLayout(new BoxLayout(this, 1));
		
		////////////////Button Pan /////////////////////////////// 
		JPanel buttPan = new JPanel();
		JButton updatePat = new JButton("Update Patients");
		buttPan.add(updatePat);
		//JButton updateProc = new JButton("Update Procedures");
		//buttPan.add(updateProc);
		JButton create = new JButton("Create new Invoice");
		buttPan.add(create);
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
		
		////////////////Patients Pan //////////////////////////////
		JPanel patPan = new JPanel();
		
		String[] colNamePat = {"Patient Name",
		       "Patient Phone Number"};
		Object[][] dataPat = {
			};
		JTable tablePat = new JTable(new DefaultTableModel(dataPat, colNamePat));
		JScrollPane scrollPat = new JScrollPane(tablePat);
		scrollPat.setPreferredSize(new Dimension(400,100));
		tablePat.setFillsViewportHeight(true);
		patPan.add(scrollPat, BorderLayout.NORTH);
		
		patPan.setVisible(true);
		subPan.add(patPan);
		/////////////////////////////////////////////////////////
		
		//////////////// Invoice Pan /////////////////////////////// 
		JPanel invPan = new JPanel();
		String[] colNameInv = {"Date",
	            "isPaid", "Amount Due", "ID"};
		Object[][] dataInv = {
			};
		JTable tableInv = new JTable(new DefaultTableModel(dataInv, colNameInv){
			@Override
			public boolean isCellEditable(int row, int column) {
		    return false;
		    }
		});
		JScrollPane scrollInv = new JScrollPane(tableInv);
		scrollInv.setPreferredSize(new Dimension(400,100));
		tableInv.setFillsViewportHeight(true);
		invPan.add(scrollInv, BorderLayout.NORTH);
		subPan.add(invPan);
		/////////////////////////////////////////////////////////
		add(subPan);
		
		updatePat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) tablePat.getModel();
				int rowCount = model.getRowCount();
				//Remove rows one by one from the end of the table
				for (int i = rowCount - 1; i >= 0; i--) {
				    model.removeRow(i);
				}
				Vector<String> pats = new Vector<String>(0);
				System.out.println(visual.getPatient());
				for(Patient pat : visual.getPatient()){
					System.out.println(pat.getPatientName());
					pats.add(pat.getPatientName());
					pats.add(pat.getPatientPhone());
				}
				System.out.println(pats.toString());
	        	model.addRow(pats);
			}
		});

		create.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) tableInv.getModel();
	        	//Patient p = visual.getPatient(tablePat.getModel().getValueAt(tablePat.getSelectedRow(),3));
	        	//visual.addPatient(p);
	        	model.addRow(new String[] {});
	        	//System.out.println(p.getPatientNo());
			}
		});
		setVisible(true);	
	}
}