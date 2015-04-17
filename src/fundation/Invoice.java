package fundation;
import java.util.Vector;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Invoice")
//If you want you can define the order in which the fields are written
//Optional
@XmlType(propOrder = { "date", "invoiceNo", "isPaid", "p_paymentList", "p_procList" })
@XmlAccessorType(XmlAccessType.FIELD)
public class Invoice {
	
	private static int No = 0;
	
	private int invoiceNo;
	private MyDate date;
	@XmlElementWrapper(name = "p_paymentList")
	@XmlElement(name = "Payment")
	private Vector<Payment> p_paymentList;
	@XmlElementWrapper(name = "p_procList")
	@XmlElement(name = "Procedure")
	private Vector<Procedure> p_procList;
	private boolean isPaid;
	
	public Invoice(){
			
	}
	
	public Invoice(MyDate d){
		invoiceNo = No++;
		date = d;
		p_paymentList = new Vector<Payment>(0);
		p_procList = new Vector<Procedure>(0);
		isPaid = (totPayment()>=totProc());
	}
	
	public Invoice(int day, int month){
		invoiceNo = No++;
		date = new MyDate(day, month);
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
	
	public void deletePayment(int no){
		Payment p = null;
		for (Payment pay: p_paymentList){
			if(pay.getPaymentNo() == no){
				p=pay;
			}
		}
		p_paymentList.remove(p);
	}
	
	public boolean addProc(Procedure p){
		boolean ret = p_procList.add(p);
		isPaid = (totPayment()>=totProc());
		return ret;
		
	}
	
	public void deleteProc(int no){
		Procedure p = null;
		for (Procedure pay: p_procList){
			if(pay.getProcNo() == no){
				p=pay;
			}
		}
		p_procList.remove(p);
	}
	
	public MyDate getDate(){
		return date;
	}

	public boolean isPaid() {
		return isPaid;
	}

	public static int getNo() {
		return No;
	}

	public static void setNo(int no) {
		No = no;
	}
}
