package cathedral;

import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import fundation.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Vector;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;

public class PatPanel extends JPanel {
	private static final long serialVersionUID = -2127048692662400975L;
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
		JButton saveX = new JButton("Save XML and Quit");
		buttPan.add(saveX);
		JButton loadX = new JButton("Load from XML");
		buttPan.add(loadX);
		JButton saveS = new JButton("Save Serialization and Quit");
		buttPan.add(saveS);
		JButton loadS = new JButton("Load from Serialization");
		buttPan.add(loadS);
		JButton quit = new JButton("Quit without Saving");
		buttPan.add(quit);
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
			private static final long serialVersionUID = -4898267686891186105L;
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
		//////////////////////////////////////////////////////////////
		
		/////////////////////////// Actions ///////////////////////////////
		update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				// Exception Handling ArrayIndexOutOfBoundException
				try {
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
				} catch (ArrayIndexOutOfBoundsException exc){
					JOptionPane.showMessageDialog(visual.getFrame(),"Please, select a Patient!");
				}
			}
		});
		addP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(patname.getText()==""){
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				Patient p = new Patient(patname.getText(), patAddr.getText(),
						patNum.getText());
				visual.addPatient(p);
				model.addRow(new String[] { patname.getText(),
						patAddr.getText(), patNum.getText(),
						p.getPatientNo() + "" });
				} else {
					JOptionPane.showMessageDialog(visual.getFrame(),"Give a name to your Patient before adding him!");
				}
			}
		});
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
	        	try{
		        	DefaultTableModel model = (DefaultTableModel) table.getModel();
		        	//Exception Handling ArrayIndexOutOfBoundException
		        	visual.deletePatient(Integer.parseInt((String)model.getValueAt(row, 3)));
		        	model.removeRow(row);
		        	currentPatientNo = -1 ;
	        	} catch (ArrayIndexOutOfBoundsException exc){
					JOptionPane.showMessageDialog(visual.getFrame(),"Please, select a Patient!");
				}
			}
		});
		
		saveX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
				JAXBContext context = JAXBContext.newInstance(MainApplication.class, Patient.class,
						Procedure.class, Invoice.class, Payment.class, MyDate.class);
			    Marshaller m = context.createMarshaller();
			    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			    m.marshal(visual.getApp(), new File("./save.xml"));
			    visual.getFrame().dispatchEvent(new WindowEvent(visual.getFrame(), WindowEvent.WINDOW_CLOSING));
				} catch (JAXBException exc) {
					exc.printStackTrace();
				}
			}
		});

		loadX.addActionListener(new ActionListener() {
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
				} catch (JAXBException | FileNotFoundException exc) {
					exc.printStackTrace();
				}
			}
		});
		
		saveS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				visual.getApp().serialize();
				visual.getFrame().dispatchEvent(new WindowEvent(visual.getFrame(), WindowEvent.WINDOW_CLOSING));
			}
		});
		
		loadS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				visual.getApp().unserialize();
				visual.updateAll();
			}
		});
		
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    visual.getFrame().dispatchEvent(new WindowEvent(visual.getFrame(), WindowEvent.WINDOW_CLOSING));
			}
		});

		// The action in case the selected case changes
		table.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent event) {
						if(!event.getValueIsAdjusting()){
							if(table.getSelectedRow()==-1){
			        			currentPatientNo = -1;
			        			visual.getInvGlobPanel().getInvPan().deleteAll();
			        			visual.getInvGlobPanel().getPayPan().deleteAll();
			        		} else {
								currentPatientNo = Integer.parseInt(table.getModel()
										.getValueAt(table.getSelectedRow(), 3)
										.toString());
								visual.updateInv(currentPatientNo);
			        		}
						}
					}
				}
		);
		//////////////////////////////////////////////////////////////////////
		
		setVisible(true);
	}

	public int getCurrentPatientNo() {
		return currentPatientNo;
	}
	
	//Update all the patients to the vector in parameter
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
