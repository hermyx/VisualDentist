package fundation;

public class Payment {
	
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
		return "Payment [paymentNo=" + paymentNo + ", paymentAmt=" + paymentAmt
				+ ", paymentDate=" + paymentDate + "]";
	}
	
	
	
}
