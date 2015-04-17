package cathedral;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
import java.io.File;
import java.io.FileReader;
import java.util.Vector;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class PatPanel extends JPanel {
	public VisualDentist visual;
	private int currentPatientNo;
	private JTable table;

	public PatPanel(VisualDentist vd) {
		super();
		visual = vd;
		currentPatientNo = -1;
		setLayout(new BoxLayout(this, 1));
		// ////////////// Infos Pan //////////////////////////////
		JPanel infosPan = new JPanel();
		JTextField patname = new JTextField(10);
		JLabel patn = new JLabel("Name of the patient : ");
		Dimension def = new Dimension(400, 20);
		patname.setPreferredSize(def);
		patname.setSize(def);
		// String s = "Default Procedure Name";
		// procname.setText(s);
		infosPan.add(patn);
		infosPan.add(patname);
		JLabel patad = new JLabel("Address of the patient : ");
		JTextField patAddr = new JTextField(10);
		patAddr.setPreferredSize(def);
		// s = "Default Procedure Cost";
		// proccost.setText(s);
		patAddr.setSize(def);
		infosPan.add(patad);
		infosPan.add(patAddr);
		JLabel patnu = new JLabel("Phone number of the patient : ");
		JTextField patNum = new JTextField(10);
		patNum.setPreferredSize(def);
		// s = "Default Procedure Cost";
		// proccost.setText(s);
		patNum.setSize(def);
		infosPan.add(patnu);
		infosPan.add(patNum);
		// infosPan.setBackground(Color.GREEN);
		// setPreferredSize(new Dimension(1000, 20));
		infosPan.setVisible(true);
		add(infosPan);
		// ///////////////////////////////////////////////////////

		// //////////////Button Pan ///////////////////////////////
		JPanel buttPan = new JPanel();
		JButton addP = new JButton("Add Patient");
		buttPan.add(addP);
		JButton update = new JButton("Update Patient");
		buttPan.add(update);
		JButton delete = new JButton("Delete Patient");
		buttPan.add(delete);
		JButton save = new JButton("Save");
		buttPan.add(save);
		JButton load = new JButton("Load");
		buttPan.add(load);
		buttPan.setVisible(true);
		add(buttPan);
		// ///////////////////////////////////////////////////////

		// //////////// List Pan /////////////////////////////////
		JPanel listPan = new JPanel();
		listPan.setLayout(new BorderLayout());
		String[] columnNames = { "Patient Name", "Patient Address",
				"Patient Phone Number", "ID" };
		// Construct with loaded data ?
		String[][] data = {};
		// data et title sont toujours nos tableaux d'objets !
		table = new JTable(new DefaultTableModel(data, columnNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(400, 150));
		table.setFillsViewportHeight(true);
		listPan.add(scrollPane, BorderLayout.NORTH);
		table.removeColumn(table.getColumnModel().getColumn(3));
		// listPan.setBackground(Color.GREEN);
		listPan.setVisible(true);
		add(listPan);
		// ////////////////////////////////////////////////////////
		update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				// Exception Handling ArrayIndexOutOfBoundException
				if (row != -1) {
					if (!patname.getText().equals("")) {
						table.setValueAt(patname.getText(), row, 0);
					}
					if (!patNum.getText().equals("")) {
						table.setValueAt(patAddr.getText(), row, 1);
					}
					if (!patNum.getText().equals("")) {
						table.setValueAt(patNum.getText(), row, 2);
					}
					// Exception here ?
					visual.updatePatient(Integer.parseInt((String) table
							.getModel().getValueAt(row, 3)), patname.getText(),
							patAddr.getText(), patNum.getText());
				}
			}
		});
		addP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				Patient p = new Patient(patname.getText(), patAddr.getText(),
						patNum.getText());
				if (p.getPatientNo() > 2) {
					int i = 1;
				}
				visual.addPatient(p);
				model.addRow(new String[] { patname.getText(),
						patAddr.getText(), patNum.getText(),
						p.getPatientNo() + "" });
			}
		});
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
	        	if(row != -1){
		        	DefaultTableModel model = (DefaultTableModel) table.getModel();
		        	//Exception Handling ArrayIndexOutOfBoundException
		        	visual.deletePatient(Integer.parseInt((String)model.getValueAt(row, 3)));
		        	model.removeRow(row);
		        	currentPatientNo = -1 ;
	        	} else {
	        		JOptionPane.showMessageDialog(visual.getFrame(),"Please, select a Patient!");
	        	}
			}
		});
		
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
				JAXBContext context = JAXBContext.newInstance(MainApplication.class, Patient.class,
						Procedure.class, Invoice.class, Payment.class, MyDate.class);
			    Marshaller m = context.createMarshaller();
			    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			    m.marshal(visual.getApp(), new File("./save.xml"));
			    System.out.println("Output from our XML File: ");
			    Unmarshaller um = context.createUnmarshaller();
				} catch (Exception exc) {
					exc.printStackTrace();
				}
			}
		});
		
		load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
				JAXBContext context = JAXBContext.newInstance(MainApplication.class, Patient.class,
						Procedure.class, Invoice.class, Payment.class, MyDate.class);
			    Marshaller m = context.createMarshaller();
			    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			    Unmarshaller um = context.createUnmarshaller();
			    MainApplication app = (MainApplication) um.unmarshal(new FileReader("./save.xml"));
			    visual.setApp(app);
			    visual.updateAll();
				} catch (Exception exc) {
					exc.printStackTrace();
				}
			}
		});

		table.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent event) {
						if(!event.getValueIsAdjusting()){
							if(table.getSelectedRow()==-1){
			        			currentPatientNo = -1;
			        			visual.getInvGlobPanel().getInvPan().deleteAll();
			        			visual.getInvGlobPanel().getPayPan().deleteAll();
			        		} else {
			        			int i = table.getSelectedRow();
								currentPatientNo = Integer.parseInt(table.getModel()
										.getValueAt(table.getSelectedRow(), 3)
										.toString());
								visual.updateInv(currentPatientNo);
			        		}
						}
					}
				}
		);

		setVisible(true);
	}

	public int getCurrentPatientNo() {
		return currentPatientNo;
	}
	
	public void updatePat(Vector<Patient> v){
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		for(int i = model.getRowCount() - 1; i > -1; i--){
		// Exception Handling ArrayIndexOutOfBoundException
		model.removeRow(i);
		}
		for(Patient p : v){
			String[] rowData = {p.getPatientName(), p.getPatientAdd(),
					p.getPatientPhone()+"", p.getPatientNo()+""};
			model.addRow(rowData);
		}
		
	}
}
