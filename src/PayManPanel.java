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
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PayManPanel extends JPanel {

	public PayManPanel(){
		super();
		setLayout(new BorderLayout());
		////////////////Infos Pan ////////////////////////////////
		JPanel infosPan = new JPanel();
		infosPan.setLayout(new BorderLayout());
		JLabel title = new JLabel("Payment Manager : ");
		infosPan.add(title, BorderLayout.WEST);
		infosPan.setVisible(true);
		add(infosPan, BorderLayout.PAGE_START);
		////////////////////////////////////////////////////////////
		
		
		////////////////Manager Pan //////////////////////////////////
		JPanel manPan = new JPanel();
		manPan.setLayout(new BoxLayout(manPan,1));
		
			JLabel selLab = new JLabel("Select wich patient did which procedure:");
			manPan.add(selLab);
		
			ProcManPanel submanPan = new ProcManPanel();
			manPan.add(submanPan);
			
			JLabel payLab = new JLabel("Now enter the payment:");
			manPan.add(payLab);
			
			////////////// List Pan /////////////////////////////////
			JPanel listPan = new JPanel();
			PayPanel payPan = new PayPanel();
			listPan.add(payPan);
			listPan.setVisible(true);
			manPan.add(listPan);
			//////////////////////////////////////////////////////////				
					
		manPan.setVisible(true);
		add(manPan,BorderLayout.CENTER);
		////////////////////////////////////////////////////////////
		
		
		
		
		setVisible(true);	
	}
}
