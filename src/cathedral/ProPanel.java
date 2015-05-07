package cathedral;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import database.DBCreator;
import database.DBQuery;
import fundation.Procedure;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

public class ProPanel extends JPanel{

	private static final long serialVersionUID = -4468931122345721848L;
	public VisualDentist visual;
	private JTable table;
	
	public ProPanel(VisualDentist vd) throws SQLException{
		super();
		visual=vd;
		DBCreator.makeCon();
		DBQuery.makeCon();
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
		buttPan.setVisible(true);
		add(buttPan);
		/////////////////////////////////////////////////////////
		
		////////////// List Pan /////////////////////////////////
		JPanel listPan = new JPanel();
		listPan.setLayout(new BorderLayout());
		String[] columnNames = {"ID", "Procedure Name",
                "Procedure Cost"};
		String statement = "select * from proc";
		ResultSet rs = DBQuery.executeQuery(statement);
		ResultSetMetaData rsmd = DBQuery.getMetadata(statement);
		int columnCount = rsmd.getColumnCount();
		int nbLines = 0;
		while(rs.next()){
			nbLines++;
		}
		rs = DBQuery.executeQuery(statement);
		String[][] data = new String[nbLines][3];
		int k = 0;
		while(rs.next()){
			for(int i=0; i<columnCount;i++){
				data[k][i]=rs.getString(i+1);
			}
			int test = Integer.parseInt(data[k][0]);
			//In case of previous deletions
			if(k>0 && test!=(Integer.parseInt(data[k-1][0])+1)){
				Procedure.setNo(test+1);
			}
			//In case of previous deletions, for the first row
			if(k==0 && test!=0){
				Procedure.setNo(test+1);
			}
			Procedure p = new Procedure(data[k][1],
	       			Double.parseDouble(data[k][2]));
	       	visual.addProcedure(p);
	       	if(visual.getProcNo(data[k][1])!=Procedure.getNo()-1){
				System.out.println("Proc ID on load mismatch");
			}
			k++;
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
		table.removeColumn(table.getColumnModel().getColumn(0));
		listPan.setVisible(true);
		add(listPan);
		//////////////////////////////////////////////////////////
		
		/////////////////////// Actions /////////////////////////
		update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				try{
					String procNo = (String)table.getModel().getValueAt(row, 0);
					if(!procname.getText().equals("")){
						table.setValueAt(procname.getText(), row, 0);
						String statement = "update proc set pName = '"+procname.getText()+"' where pid = "+procNo;
						DBCreator.exec(statement);
					}
					if(!proccost.getText().equals("")){
						table.setValueAt(proccost.getText(), row, 1);
						String statement = "update proc set pCost = "+proccost.getText()+" where pid = "+procNo;
						DBCreator.exec(statement);
					}
					visual.updateProcedure(
							Integer.parseInt((String)table.getModel().getValueAt(row, 0))
							,procname.getText(), Double.parseDouble(proccost.getText()));
				} catch(ArrayIndexOutOfBoundsException exc) {
					JOptionPane.showMessageDialog(visual.getFrame(),"Please, select a Procedure!");
				} catch(NumberFormatException exc) {
					JOptionPane.showMessageDialog(visual.getFrame(),"Please, enter a valid cost!");
				} catch(SQLException exc) {
					JOptionPane.showMessageDialog(visual.getFrame(),"Database issue, please contact maintenance!");
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
		        	model.addRow(new String[] {p.getProcNo()+"",procname.getText(),proccost.getText()});
		        	String statement = "insert into proc (pID, pName, pCost) values "
		        			+ "("+p.getProcNo()+", '"+procname.getText()+"', "+proccost.getText()+")";
		        	DBCreator.exec(statement);
		        	} catch(SQLException exc){
		        		JOptionPane.showMessageDialog(visual.getFrame(),"Database issue, please refer to an admninistrator!");
		        	} catch(Exception exc){
		        		JOptionPane.showMessageDialog(visual.getFrame(),"Please, enter a valid number!");
		        	}
		        }
		});
		
		del.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e){
	        	int row = table.getSelectedRow();
	        	try{
		        	DefaultTableModel model = (DefaultTableModel) table.getModel();
		        	visual.deleteProcedure(Integer.parseInt((String)model.getValueAt(row, 0)));
		        	String procNo = (String)table.getModel().getValueAt(row, 0);
		        	String statement = "delete from proc where pid = "+procNo;
		        	DBCreator.exec(statement);
		        	model.removeRow(row);
	        	} catch(ArrayIndexOutOfBoundsException exc) {
	        		JOptionPane.showMessageDialog(visual.getFrame(),"Please, select a Procedure!");
				} catch(SQLException exc) {
					JOptionPane.showMessageDialog(visual.getFrame(),"Database issue, please contact maintenance!");
				}
	        }
		});
		
		////////////////////////////////////////////////////////////////////////////////////////
		setVisible(true);	
	}
	
	// Get the row of the nth procedure in an array
	public String[] getRow(int noProc){
		String[] ret = new String[3];
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		for(int i = model.getRowCount() - 1; i > -1; i--){
			if(Integer.parseInt((String)model.getValueAt(i, 0))==noProc){
				ret[2]=(String)model.getValueAt(i, 0);
				ret[0]=(String)model.getValueAt(i, 1);
				ret[1]=(String)model.getValueAt(i, 2);
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
			String[] rowData = {p.getProcNo()+"", p.getProcName(), p.getProcCost()+""};
			model.addRow(rowData);
		}
		
	}
}