import java.util.Vector;


public class Patient {
	
	private static int No = 0;
	
	private int patientNo;
	private String patientName;
	private String patientAdd;
	private String patientPhone;
	private Vector<Invoice> p_invoiceList;

	
	public Patient(String n, String a, String ph, Invoice i, Procedure proc, Payment pay){
		patientNo = No++;
		patientName = n;
		patientAdd = a;
		patientPhone = ph;
		p_invoiceList = new Vector<Invoice>(0);
		this.addInvoice(i);
		this.addProc(proc, i);
		this.addPayment(pay, i);
	}

	@Override
	public String toString() {
		return "Patient [patientNo=" + patientNo + ", patientName=" + patientName
				+ ", patientAdd=" + patientAdd + ", patientPhone="
				+ patientPhone + "]";
	}

	public int getPatientNo() {
		return patientNo;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getPatientAdd() {
		return patientAdd;
	}
	public void setPatientAdd(String patientAdd) {
		this.patientAdd = patientAdd;
	}
	public String getPatientPhone() {
		return patientPhone;
	}
	public void setPatientPhone(String patientPhone) {
		this.patientPhone = patientPhone;
	}
	
	public Vector<Invoice> getP_invoiceList() {
		return p_invoiceList;
	}
	
	//Get the Invoice made at the date "d"
	public boolean invoiceIsIn(Date d){
		for (Invoice i : p_invoiceList){
			if(i.getDate().equals(d)){
				return true;
			}
		}
		return false;
	}
	
	//Get the Invoice made at the date "d"
	public Invoice getInvoice(Date d){
		for (Invoice i : p_invoiceList){
			if(i.getDate().equals(d)){
				return i;
			}
		}
		return null;
	}

	public boolean addPayment(Payment p, Invoice i){
		if(invoiceIsIn(i.getDate())){
			return getInvoice(i.getDate()).addPayment(p);
		}
		return false;
	}
	
	public boolean addProc(Procedure p, Invoice i){
		if(invoiceIsIn(i.getDate())){
			return getInvoice(i.getDate()).addProc(p);
		}
		return false;
	}
	
	public boolean addInvoice(Invoice i){
		return p_invoiceList.add(i);
	}
	
	//Returns true if the Patient has Paid all of his Procedures
	public boolean hasPaid(){
		boolean ret = true;
		for(Invoice i : p_invoiceList){
			ret&=i.isPaid();
		}
		return ret;
	}
}
