package cathedral;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import fundation.MyDate;

import java.awt.*;
import java.awt.event.*;

public class PayPanel extends JPanel{
	public VisualDentist visual;
	
	public <visual> PayPanel(VisualDentist vd){
		super();
		visual=vd;
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
		JLabel payAmount = new JLabel("Amount of the procedure : ");
		JTextField payA = new JTextField(10);
		payA.setPreferredSize(def);
		MyDate today = new MyDate();
		today.getToToday();
		payA.setText(today.toString());
		payA.setSize(def);
		infosPan.add(payAmount);
		infosPan.add(payA);
		//infosPan.setBackground(Color.GREEN);
		//setPreferredSize(new Dimension(1000, 20));
		infosPan.setVisible(true);
		add(infosPan);
		/////////////////////////////////////////////////////////
		
		////////////////Button Pan /////////////////////////////// 
		JPanel buttPan = new JPanel();
		JButton addP = new JButton("Add Procedure");
		buttPan.add(addP);
		JButton del = new JButton("Delete all Procedure");
		buttPan.add(del);
		buttPan.setVisible(true);
		add(buttPan);
		/////////////////////////////////////////////////////////
		
		////////////// List Pan /////////////////////////////////
		JPanel listPan = new JPanel();
		listPan.setLayout(new BorderLayout());
		String[] columnNames = {"Procedure Name",
                "Procedure Cost"};
		Object[][] data = {
			    {"X-Ray", "250"},
			    {"Filling", "200"},
			};
		JTable table = new JTable(data, columnNames);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(400,100));
		table.setFillsViewportHeight(true);
		listPan.add(scrollPane, BorderLayout.NORTH);
		//listPan.setBackground(Color.GREEN);
		listPan.setVisible(true);
		add(listPan);
		//////////////////////////////////////////////////////////
		del.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
	        	model.addRow(visual.getPatient());
			}
		});
		addP.addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent e){
		        	
		        }
		});

		setVisible(true);	
	}
}