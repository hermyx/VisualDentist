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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PatPanel extends JPanel {

	public PatPanel(){
		super();
		setLayout(new BoxLayout(this,1));		
		//////////////// Infos Pan //////////////////////////////
		JPanel infosPan = new JPanel();
		JTextField procname = new JTextField(10);
		JLabel procn = new JLabel("Name of the patient : ");
		Dimension def = new Dimension(400,20);
		procname.setPreferredSize(def);
		procname.setSize(def);
		//String s = "Default Procedure Name";
		//procname.setText(s);
		infosPan.add(procn);
		infosPan.add(procname);
		JLabel procc = new JLabel("Phone number of the patient : ");
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
		JButton addP = new JButton("Add Patient");
		buttPan.add(addP);
		JButton del = new JButton("Update Patient");
		buttPan.add(del);
		buttPan.setVisible(true);
		add(buttPan);
		/////////////////////////////////////////////////////////
		
		////////////// List Pan /////////////////////////////////
		JPanel listPan = new JPanel();
		listPan.setLayout(new BorderLayout());
		String[] columnNames = {"Patient Name",
                "Patient Phone Number"};
		Object[][] data = {
			    {"Toto", "0642666669"},
			    {"Tutu", "0660066006"},
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
