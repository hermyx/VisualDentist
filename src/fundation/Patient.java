package fundation;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Vector;

import javax.xml.bind.annotation.*;


@XmlRootElement(name = "Patient")
@XmlType(propOrder = { "patientName", "patientNo", "patientAdd", "patientPhone", "p_invoiceList" })
@XmlAccessorType(XmlAccessType.FIELD)
public class Patient implements Comparable<Patient>, Comparator<Patient>, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4861046926252963428L;

	private static int No = 0;
	
	private int patientNo;
	private String patientName;
	private String patientAdd;
	private String patientPhone;
	@XmlElementWrapper(name = "p_invoiceList")
	@XmlElement(name = "invoice")
	private Vector<Invoice> p_invoiceList;

	public Patient(){
		
	}
	
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
	
	public Patient(String n, String a, String ph, Invoice i){
		patientNo = No++;
		patientName = n;
		patientAdd = a;
		patientPhone = ph;
		p_invoiceList = new Vector<Invoice>(0);
		this.addInvoice(i);
	}
	
	public Patient(String n, String a, String ph){
		patientNo = No++;
		patientName = n;
		patientAdd = a;
		patientPhone = ph;
		p_invoiceList = new Vector<Invoice>(0);
	}

	@Override
	public String toString() {
		String ret = patientName;
		ret+="\n\tInvoices :\n";
		for(int i=0;i<p_invoiceList.size();i++){
			ret+="\t"+i+")"+p_invoiceList.get(i).toString()+"\n";
		}
		return ret;
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
	public boolean invoiceIsIn(MyDate d){
		for (Invoice i : p_invoiceList){
			if(i.getDate().equals(d)){
				return true;
			}
		}
		return false;
	}
	
	//Get the Invoice made at the date "d"
	public Invoice getInvoice(MyDate d){
		for (Invoice i : p_invoiceList){
			if(i.getDate().equals(d)){
				return i;
			}
		}
		return null;
	}
	
	public Invoice getInvoice(int no){
		for (Invoice i : p_invoiceList){
			if(i.getInvoiceNo() == no){
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
	
	public void deletePayment(int Invno, int Payno){
		getInvoice(Invno).deletePayment(Payno);
	}
	
	public void deleteProc(int Invno, int Procno){
		getInvoice(Invno).deleteProc(Procno);
	}
	
	public void deleteInv(int no){
		Invoice inv = null;
		for (Invoice i: p_invoiceList){
			if(i.getInvoiceNo() == no){
				inv=i;
			}
		}
		p_invoiceList.remove(inv);
	}
	
	//Returns true if the Patient has Paid all of his Procedures
	public boolean hasPaid(){
		boolean ret = true;
		for(Invoice i : p_invoiceList){
			ret&=i.isPaid();
		}
		return ret;
	}

	public static int getNo() {
		return No;
	}

	public static void setNo(int no) {
		No = no;
	}

	@Override
	public int compare(Patient pat1, Patient pat2) {
		return pat1.getPatientName().compareTo(pat2.getPatientName());
	}

	@Override
	public int compareTo(Patient pat) {
		return patientName.compareTo(pat.getPatientName());
	}
	
	public int moneyDue(){
		int moneyDue=0;
		for(Invoice i:getP_invoiceList()){
			moneyDue-=i.totPayment();
			moneyDue+=i.totProc();
		}
		return moneyDue;
	}
	
	public void setPatientNo(int no){
		patientNo=no;
	}
	
}
