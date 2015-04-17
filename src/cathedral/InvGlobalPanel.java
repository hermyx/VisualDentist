package cathedral;
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

import fundation.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Vector;

public class InvGlobalPanel extends JPanel {
	public VisualDentist visual;
	private PayPanel payPan;
	private InvPanel invPan;

	public InvGlobalPanel(VisualDentist vd){
		super();
		visual=vd;
		setLayout(new BorderLayout());
		////////////////Infos Pan ////////////////////////////////
		JPanel infosPan = new JPanel();
		infosPan.setLayout(new BorderLayout());
		//JLabel separ = new JLabel("_________________________________________________________ ");
		JLabel title = new JLabel("Manage your patient's invoices, their procedures and their payments : ");
		//infosPan.add(separ, BorderLayout.PAGE_START);
		infosPan.add(title, BorderLayout.WEST);
		infosPan.setVisible(true);
		add(infosPan, BorderLayout.PAGE_START);
		////////////////////////////////////////////////////////////
		
		
		////////////////Manager Pan //////////////////////////////////
		JPanel manPan = new JPanel();
		manPan.setLayout(new BoxLayout(manPan,1));
		
			JLabel selLab = new JLabel("Select the current Patient and create your invoices:");
			manPan.add(selLab);
		
			invPan = new InvPanel(visual, this);
			manPan.add(invPan);
			
			JLabel payLab = new JLabel("Now enter add the the payment:");
			manPan.add(payLab);
			
			////////////// List Pan /////////////////////////////////
			JPanel listPan = new JPanel();
			payPan = new PayPanel(visual, this);
			listPan.add(payPan);
			listPan.setVisible(true);
			manPan.add(listPan);
			//////////////////////////////////////////////////////////				
					
		manPan.setVisible(true);
		add(manPan,BorderLayout.CENTER);
		////////////////////////////////////////////////////////////
		
		setVisible(true);	
	}

	public PayPanel getPayPan() {
		return payPan;
	}

	public InvPanel getInvPan() {
		return invPan;
	}
	
	public void updateInv(Vector<Invoice> v){
		invPan.updateInv(v);
	}
	
	public void updatePan(int no){
		Patient pat = visual.getPatient(visual.getPatPanel().getCurrentPatientNo());
    	Invoice i = pat.getInvoice(no);
    	Vector<Payment> v = i.getP_paymentList();
    	Vector<Procedure> v2 = i.getP_procList();
		payPan.updatePay(v);
		payPan.updateProc(v2);
	}
}
