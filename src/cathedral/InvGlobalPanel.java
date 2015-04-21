package cathedral;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;

import fundation.*;

import java.awt.BorderLayout;
import java.util.Vector;

public class InvGlobalPanel extends JPanel {
	private static final long serialVersionUID = -5330061009380955998L;
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
			
			JLabel payLab = new JLabel("Now enter and add the the payment:");
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
	
	//Update the proc and pay in order the patient or invoice has changed
	public void updatePan(int no){
		Patient pat = visual.getPatient(visual.getPatPanel().getCurrentPatientNo());
    	Invoice i = pat.getInvoice(no);
    	Vector<Payment> v = i.getP_paymentList();
    	Vector<Procedure> v2 = i.getP_procList();
		payPan.updatePay(v);
		payPan.updateProc(v2);
	}
}
