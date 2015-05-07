package fundation;

import java.io.Serializable;
import java.util.Comparator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Payment")
@XmlType(propOrder = { "paymentDate", "paymentAmt", "paymentNo" })
@XmlAccessorType(XmlAccessType.FIELD)
public class Payment implements Comparable<Patient>, Comparator<Patient>, Serializable {

	private static final long serialVersionUID = -9164901709065404734L;

	public Payment(){
		
	}
	
	private static int No = 0;
	
	private int paymentNo;
	private double paymentAmt;
	private MyDate paymentDate;
	
	public Payment(double a, MyDate d){
		paymentNo = No++;
		paymentAmt = a;
		paymentDate = d;
	}
	
	public Payment(double a, int day, int month){
		paymentNo = No++;
		paymentAmt = a;
		paymentDate = new MyDate(day,month);
	}
	
	public int getPayment() {
		return paymentNo;
	}
	public double getPaymentAmt() {
		return paymentAmt;
	}
	public void setPaymentAmt(double paymentAmt) {
		this.paymentAmt = paymentAmt;
	}
	public MyDate getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(MyDate paymentDate) {
		this.paymentDate = paymentDate;
	}
	@Override
	public String toString() {
		return "Payment made at the "+paymentDate+" of "+paymentAmt+" euros";
	}

	public int getPaymentNo() {
		return paymentNo;
	}

	public static int getNo() {
		return No;
	}

	public static void setNo(int no) {
		No = no;
	}

	@Override
	public int compare(Patient p1, Patient p2) {
		int ret = 1;
		if (p1.moneyDue() == p2.moneyDue()){
			ret = 0;
		}
		if (p1.moneyDue() > p2.moneyDue()){
			ret = -1;
		}
		return ret;
	}

	@Override
	public int compareTo(Patient p) {
		int ret = 1;
		if (paymentAmt == p.moneyDue()){
			ret = 0;
		}
		if (paymentAmt > p.moneyDue()){
			ret = -1;
		}
		return ret;
	}

	public void setPaymentNo(int paymentNo) {
		this.paymentNo = paymentNo;
	}
	
	
	
}
