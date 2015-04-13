import javax.swing.*;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.awt.event.*;

public class ProcManPanel extends JPanel{

	public ProcManPanel(){
		super();
		setLayout(new BoxLayout(this, 1));
		
		////////////////Button Pan /////////////////////////////// 
		JPanel buttPan = new JPanel();
		JButton updatePat = new JButton("Update Patients");
		buttPan.add(updatePat);
		JButton updateProc = new JButton("Update Procedures");
		buttPan.add(updateProc);
		JButton addPtP = new JButton("Add Procedure to Patient");
		buttPan.add(addPtP);
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
			    {"Toto", "0642666669"},
			    {"Tutu", "0660066006"},
			};
		JTable tablePat = new JTable(dataPat, colNamePat);
		JScrollPane scrollPat = new JScrollPane(tablePat);
		scrollPat.setPreferredSize(new Dimension(400,200));
		tablePat.setFillsViewportHeight(true);
		patPan.add(scrollPat, BorderLayout.NORTH);
		
		patPan.setVisible(true);
		subPan.add(patPan);
		/////////////////////////////////////////////////////////
		
		//////////////// Procedure Pan /////////////////////////////// 
		JPanel procPan = new JPanel();
		String[] colNameProc = {"Procedure Name",
	            "Procedure Cost"};
		Object[][] dataProc = {
			    {"X-Ray", "250"},
			    {"Filling", "200"},
			};
		JTable tableProc = new JTable(dataProc, colNameProc);
		JScrollPane scrollProc = new JScrollPane(tableProc);
		scrollProc.setPreferredSize(new Dimension(400,200));
		tableProc.setFillsViewportHeight(true);
		procPan.add(scrollProc, BorderLayout.NORTH);
		subPan.add(procPan);
		/////////////////////////////////////////////////////////
		add(subPan);

		setVisible(true);	
	}
}