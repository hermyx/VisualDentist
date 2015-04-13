
public class Payment {
	
	private static int No = 0;
	
	private int paymentNo;
	private double paymentAmt;
	private Date paymentDate;
	
	public Payment(double a, Date d){
		paymentNo = No++;
		paymentAmt = a;
		paymentDate = d;
	}
	
	public Payment(double a, int day, int month){
		paymentNo = No++;
		paymentAmt = a;
		paymentDate = new Date(day,month);
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
	public Date getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	@Override
	public String toString() {
		return "Payment [paymentNo=" + paymentNo + ", paymentAmt=" + paymentAmt
				+ ", paymentDate=" + paymentDate + "]";
	}
	
	
	
}
