package cathedral;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import fundation.Invoice;
import fundation.MyDate;
import fundation.Patient;
import fundation.Procedure;

import java.awt.*;
import java.awt.event.*;

public class ProPanel extends JPanel{
	public VisualDentist visual;
	
	public ProPanel(VisualDentist vd){
		super();
		visual=vd;
		setLayout(new BoxLayout(this,1));		
		//////////////// Infos Pan //////////////////////////////
		JPanel infosPan = new JPanel();
		JTextField procname = new JTextField(10);
		JLabel procn = new JLabel("Name of the procedure : ");
		Dimension def = new Dimension(400,20);
		procname.setPreferredSize(def);
		procname.setSize(def);
		//String s = "Default Procedure Name";
		//procname.setText(s);
		infosPan.add(procn);
		infosPan.add(procname);
		JLabel procc = new JLabel("Cost of the procedure : ");
		JTextField proccost = new JTextField(10);
		proccost.setPreferredSize(def);
		//s = "Default Procedure Cost";
		//proccost.setText(s);
		proccost.setSize(def);
		infosPan.add(procc);
		infosPan.add(proccost);
		//infosPan.setBackground(Color.GREEN);
		//setPreferredSize(new Dimension(1000, 20));
		infosPan.setVisible(true);
		add(infosPan);
		/////////////////////////////////////////////////////////
		
		////////////////Button Pan /////////////////////////////// 
		JPanel buttPan = new JPanel();
		JButton addP = new JButton("Add Procedure");
		buttPan.add(addP);
		JButton update = new JButton("Update Procedure");
		buttPan.add(update);
		JButton del = new JButton("Delete Procedure");
		buttPan.add(del);
		buttPan.setVisible(true);
		add(buttPan);
		/////////////////////////////////////////////////////////
		
		////////////// List Pan /////////////////////////////////
		JPanel listPan = new JPanel();
		listPan.setLayout(new BorderLayout());
		String[] columnNames = {"Procedure Name",
                "Procedure Cost", "ID"};
		// Construct on load !
		String[][] data = {
				{"X-Ray", "250"},
			    {"Filling", "200"},
			    {"Extraction", "250"},
			    {"Dentures", "300"},
			    {"Apicoectomie", "400"}
			};
		for(int i=0; i<data.length;i++){
			Procedure p = new Procedure(data[i][0],
        			Double.parseDouble(data[i][1]));
        	visual.addProcedure(p);
		}
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
		table.removeColumn(table.getColumnModel().getColumn(2));
		//listPan.setBackground(Color.GREEN);
		listPan.setVisible(true);
		add(listPan);
		//////////////////////////////////////////////////////////
		update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				//Exception Handling ArrayIndexOutOfBoundException
				if(row!=-1){
					if(!procname.getText().equals("")){
						table.setValueAt(procname.getText(), row, 0);
					}
					if(!proccost.getText().equals("")){
						table.setValueAt(proccost.getText(), row, 1);
					}
					//Exception here ?
					visual.updateProcedure(
							Integer.parseInt((String)table.getModel().getValueAt(row, 2))
							,procname.getText(), Double.parseDouble(proccost.getText()));
				}
			}
		});
		addP.addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent e){
		        	DefaultTableModel model = (DefaultTableModel) table.getModel();
		        	model.addRow(new String[] {procname.getText(),proccost.getText()});
		        	MyDate today = new MyDate();
		        	today.getToToday();
		        	//Exception for string to double
		        	Procedure p = new Procedure(procname.getText(),
		        			Double.parseDouble(proccost.getText()));
		        	visual.addProcedure(p);
		        	System.out.println(p.getProcNo());
		        }
		});
		del.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e){
	        	DefaultTableModel model = (DefaultTableModel) table.getModel();
	        	//Exception Handling ArrayIndexOutOfBoundException
	        	model.removeRow(table.getSelectedRow());	
	        }
		});

		setVisible(true);	
	}
}