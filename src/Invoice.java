import java.util.Vector;


public class Invoice {
	
	private static int No = 0;
	
	private int invoiceNo;
	private Date date;
	private Vector<Payment> p_paymentList;
	private Vector<Procedure> p_procList;
	private boolean isPaid;
	
	public Invoice(Date d){
		invoiceNo = No++;
		date = d;
		p_paymentList = new Vector<Payment>(0);
		p_procList = new Vector<Procedure>(0);
		isPaid = (totPayment()>=totProc());
	}
	
	public Invoice(int day, int month){
		invoiceNo = No++;
		date = new Date(day, month);
		p_paymentList = new Vector<Payment>(0);
		p_procList = new Vector<Procedure>(0);
	}
	
	public int getInvoiceNo(){
		return invoiceNo;
	}
	
	public Vector<Payment> getP_paymentList() {
		return p_paymentList;
	}
	
	public Vector<Procedure> getP_procList() {
		return p_procList;
	}
	
	//Get the total of the Payments
	public int totPayment(){
		int ret = 0;
		for(Payment p : p_paymentList){
			ret+=p.getPaymentAmt();
		}
		return ret;
	}
	
	//Get the total of the costs of the Procedures
	public int totProc(){
		int ret = 0;
		for(Procedure p : p_procList){
			ret+=p.getProcCost();
		}
		return ret;
	}
	
	public boolean addPayment(Payment p){
		boolean ret = p_paymentList.add(p);
		isPaid = (totPayment()>=totProc());
		return ret;
	}
	
	public boolean addProc(Procedure p){
		boolean ret = p_procList.add(p);
		isPaid = (totPayment()>=totProc());
		return ret;
		
	}
	
	public Date getDate(){
		return date;
	}

	public boolean isPaid() {
		return isPaid;
	}
}
