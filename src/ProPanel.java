import javax.swing.*;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.awt.event.*;

public class ProPanel extends JPanel{

	public ProPanel(){
		super();
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
		scrollPane.setPreferredSize(new Dimension(400,200));
		table.setFillsViewportHeight(true);
		listPan.add(scrollPane, BorderLayout.NORTH);
		//listPan.setBackground(Color.GREEN);
		listPan.setVisible(true);
		add(listPan);
		//////////////////////////////////////////////////////////
		del.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		addP.addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent e){
		        	
		        }
		});

		setVisible(true);	
	}
}