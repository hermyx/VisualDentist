package cathedral;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import fundation.Procedure;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Vector;

public class ProPanel extends JPanel{

	private static final long serialVersionUID = -4468931122345721848L;
	public VisualDentist visual;
	private JTable table;
	
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
		JButton update = new JButton("Update Current Procedure");
		buttPan.add(update);
		JButton del = new JButton("Delete Current Procedure");
		buttPan.add(del);
		JButton save = new JButton("Save current list into default one");
		buttPan.add(save);
		buttPan.setVisible(true);
		add(buttPan);
		/////////////////////////////////////////////////////////
		
		////////////// List Pan /////////////////////////////////
		JPanel listPan = new JPanel();
		listPan.setLayout(new BorderLayout());
		String[] columnNames = {"Procedure Name",
                "Procedure Cost", "ID"};
		String[][] data = null;
		//This code is building the default list of Procedure from a file
		try{
			String filePath = "./procOnLoad.txt";
			BufferedReader buff = new BufferedReader(new FileReader(filePath));
			String line;
			int nbLine = 0;
			while ((line = buff.readLine()) != null) {
				nbLine++;
			}
			buff.close();
			try{
				buff = new BufferedReader(new FileReader(filePath));
				data = new String[nbLine][3];
				int i = 0;
				while ((line = buff.readLine()) != null) {
					String[] lineSplited = line.split(":");
					data[i][0]=lineSplited[0];
					data[i][1]=lineSplited[1];
					Procedure p = new Procedure(data[i][0],
			       			Double.parseDouble(data[i][1]));
					data[i][2]=p.getProcNo()+"";
			       	visual.addProcedure(p);
			       	i++;
				}
				buff.close();
			} catch (Exception exc) {
				System.out.println("Erreur Reading of the procOnLoad File 1 : --" + exc.toString());
			}
		} catch (Exception exc){
			System.out.println("Erreur Reading of the procOnLoad File 2 : --" + exc.toString());
		}
		table = new JTable(new DefaultTableModel(data, columnNames){
			private static final long serialVersionUID = 5561942088260181172L;
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
		listPan.setVisible(true);
		add(listPan);
		//////////////////////////////////////////////////////////
		
		/////////////////////// Actions /////////////////////////
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
				else {
					JOptionPane.showMessageDialog(visual.getFrame(),"Please, select a Procedure!");
				}
			}
		});
		addP.addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent e){
		        	DefaultTableModel model = (DefaultTableModel) table.getModel();
		        	try{
		        	Procedure p = new Procedure(procname.getText(),
		        			Double.parseDouble(proccost.getText()));
		        	visual.addProcedure(p);
		        	System.out.println(p.getProcNo());
		        	model.addRow(new String[] {procname.getText(),proccost.getText(),p.getProcNo()+""});
		        	}
		        	catch(Exception exc){
		        		JOptionPane.showMessageDialog(visual.getFrame(),"Please, enter a valid number!");
		        	}
		        }
		});
		
		del.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e){
	        	int row = table.getSelectedRow();
	        	try{
		        	DefaultTableModel model = (DefaultTableModel) table.getModel();
		        	//Exception Handling ArrayIndexOutOfBoundException
		        	visual.deleteProcedure(Integer.parseInt((String)model.getValueAt(row, 2)));
		        	model.removeRow(row);
	        	} catch(Exception exc) {
	        		JOptionPane.showMessageDialog(visual.getFrame(),"Please, select a Procedure!");
	        	}
	        }
		});
		
		
		
		save.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e){
	        	try{
	        		PrintWriter writer = new PrintWriter("procOnLoad.txt", "UTF-8");
	        		DefaultTableModel model = (DefaultTableModel) table.getModel();
	        		int j = model.getRowCount();
	        		for(int i = 0; i < j; i++){
	        			writer.println(model.getValueAt(i, 0)+":"+model.getValueAt(i, 1));
	        		}
	        		writer.close();
	        		JOptionPane.showMessageDialog(visual.getFrame(),"Saving successful!");
	        	} catch(Exception exc) {
	        		System.out.println("Couldn't save the file of procedures");
	        	}
	        }
		});
		///////////////////////////////////////////////////////////
		setVisible(true);	
	}
	
	// Get the row of the nth procedure in an array
	public String[] getRow(int noProc){
		String[] ret = new String[3];
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		for(int i = model.getRowCount() - 1; i > -1; i--){
			if(Integer.parseInt((String)model.getValueAt(i, 2))==noProc){
				ret[0]=(String)model.getValueAt(i, 0);
				ret[1]=(String)model.getValueAt(i, 1);
				ret[2]=(String)model.getValueAt(i, 2);
			}
		}
		return ret;
	}
	
	//updates the table of procedures according to the vector in parameters
	public void updateProc(Vector<Procedure> v){
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		for(int i = model.getRowCount() - 1; i > -1; i--){
		// Exception Handling ArrayIndexOutOfBoundException
		model.removeRow(i);
		}
		for(Procedure p : v){
			String[] rowData = {p.getProcName(), p.getProcCost()+"", p.getProcNo()+""};
			model.addRow(rowData);
		}
		
	}
}