package cathedral;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import fundation.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PatPanel extends JPanel {
	public VisualDentist visual;
	
	public PatPanel(VisualDentist vd){
		super();
		visual=vd;
		setLayout(new BoxLayout(this,1));		
		//////////////// Infos Pan //////////////////////////////
		JPanel infosPan = new JPanel();
		JTextField patname = new JTextField(10);
		JLabel patn = new JLabel("Name of the patient : ");
		Dimension def = new Dimension(400,20);
		patname.setPreferredSize(def);
		patname.setSize(def);
		//String s = "Default Procedure Name";
		//procname.setText(s);
		infosPan.add(patn);
		infosPan.add(patname);
		JLabel patad = new JLabel("Address of the patient : ");
		JTextField patAddr = new JTextField(10);
		patAddr.setPreferredSize(def);
		//s = "Default Procedure Cost";
		//proccost.setText(s);
		patAddr.setSize(def);
		infosPan.add(patad);
		infosPan.add(patAddr);
		JLabel patnu = new JLabel("Phone number of the patient : ");
		JTextField patNum = new JTextField(10);
		patNum.setPreferredSize(def);
		//s = "Default Procedure Cost";
		//proccost.setText(s);
		patNum.setSize(def);
		infosPan.add(patnu);
		infosPan.add(patNum);
		//infosPan.setBackground(Color.GREEN);
		//setPreferredSize(new Dimension(1000, 20));
		infosPan.setVisible(true);
		add(infosPan);
		/////////////////////////////////////////////////////////
		
		////////////////Button Pan /////////////////////////////// 
		JPanel buttPan = new JPanel();
		JButton addP = new JButton("Add Patient");
		buttPan.add(addP);
		JButton update = new JButton("Update Patient");
		buttPan.add(update);
		JButton delete = new JButton("Delete Patient");
		buttPan.add(delete);
		buttPan.setVisible(true);
		add(buttPan);
		/////////////////////////////////////////////////////////
		
		////////////// List Pan /////////////////////////////////
		JPanel listPan = new JPanel();
		listPan.setLayout(new BorderLayout());
		String[] columnNames = {"Patient Name", "Patient Address",
                "Patient Phone Number", "ID"};
		// Construct with loaded data ?
		String[][] data = {
			};
		//data et title sont toujours nos tableaux d'objets !
		JTable table = new JTable(new DefaultTableModel(data, columnNames){
			@Override
			public boolean isCellEditable(int row, int column) {
		    return false;
		    }
		});
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(400,200));
		table.setFillsViewportHeight(true);
		listPan.add(scrollPane, BorderLayout.NORTH);
		table.removeColumn(table.getColumnModel().getColumn(3));
		//listPan.setBackground(Color.GREEN);
		listPan.setVisible(true);
		add(listPan);
		//////////////////////////////////////////////////////////
		update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				//Exception Handling ArrayIndexOutOfBoundException
				if(row!=-1){
					if(!patname.getText().equals("")){
						table.setValueAt(patname.getText(), row, 0);
					}
					if(!patNum.getText().equals("")){
						table.setValueAt(patAddr.getText(), row, 1);
					}
					if(!patNum.getText().equals("")){
						table.setValueAt(patNum.getText(), row, 2);
					}
					//Exception here ?
					visual.updatePatient(Integer.parseInt((String)table.getModel().getValueAt(row, 3))
							, patname.getText(), patAddr.getText(), patNum.getText());
				}
			}
		});
		addP.addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent e){
		        	DefaultTableModel model = (DefaultTableModel) table.getModel();
		        	
		        	MyDate today = new MyDate();
		        	today.getToToday();
		        	Patient p = new Patient(patname.getText(),patAddr.getText(),patNum.getText(),
		        			new Invoice(today));
		        	visual.addPatient(p);
		        	model.addRow(new String[] {patname.getText(),patAddr.getText(),patNum.getText(),p.getPatientNo()+""});
		        	System.out.println(p.getPatientNo());
		        }
		});
		delete.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e){
	        	DefaultTableModel model = (DefaultTableModel) table.getModel();
	        	//Exception Handling ArrayIndexOutOfBoundException
	        	visual.deletePatient(Integer.parseInt((String)table.getModel().getValueAt(table.getSelectedRow(),3)));
	        	//Exception Handling ArrayIndexOutOfBoundException
	        	model.removeRow(table.getSelectedRow());	
	        }
		});
		
		setVisible(true);	
	}
}
