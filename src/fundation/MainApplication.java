package fundation;
import java.util.Vector;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "fundation")

public class MainApplication {
	
	@XmlElementWrapper(name = "patientList")
	// XmlElement sets the name of the entities
	@XmlElement(name = "Patient")
	private Vector<Patient> patientList;
	@XmlElementWrapper(name = "prList")
	@XmlElement(name = "Procedure")
	private Vector<Procedure> procedureList;
	
	//Build an empty Main Application
	public MainApplication(){
		patientList = new Vector<Patient>(0);
		procedureList = new Vector<Procedure>(0);
	}
	
	// Build a Main Application already filled
	public MainApplication(Vector<Patient> pl, Vector<Procedure> procl){
		patientList = pl;
		procedureList = procl;
	}
	
	// Add a Patient
	public void addPatient(Patient p){
		patientList.add(p);
	}
	
	public void updatePatient(int no, String n, String ad, String num){
		for(Patient p : patientList){
			if(p.getPatientNo()==no){
				p.setPatientName(n);
				p.setPatientAdd(ad);
				p.setPatientPhone(num);
			}
		}
	}
	
	public void deletePatient(int n){
		Patient pat = null;
		for(Patient p : patientList){
			if(p.getPatientNo()==n){
				pat=p;
			}
		}
		patientList.remove(pat);
	}
	
	public Patient getPatient(int n){
		//Initialization in case the search don't work
		for(Patient p : patientList){
			if(p.getPatientNo()==n){
				return p;
			}
		}
		//Exception
		return null;
	}
	
	//Add a Procedure
	public void addProcedure(Procedure p){
		procedureList.add(p);
	}
	
	public void updateProcedure(int no, String n, double c){
		for(Procedure p : procedureList){
			if(p.getProcNo()==no){
				p.setProcName(n);
				p.setProcCost(c);
			}
		}
	}
	
	public void deleteProcedure(int n){
		Procedure proc = null;
		for(Procedure p : procedureList){
			if(p.getProcNo()==n){
				proc=p;
			}
		}
		procedureList.remove(proc);
	}
	
	//Test if the Patient with name "name" is in the main app
	public boolean patientisIn(String name){
		for (Patient p : patientList){
			if(p.getPatientName().equals(name)){
				return true;
			}
		}
		return false;
	}
	
	//Get the Patient with name "name"
	public Patient getPatient(String name){
		for (Patient p : patientList){
			if(p.getPatientName().equals(name)){
				return p;
			}
		}
		return null;
	}
	
	//Test if the Procedure with name "name" is in the main app
	public boolean procisIn(String name){
		for (Procedure p : procedureList){
			if(p.getProcName().equals(name)){
				return true;
			}
		}
		return false;
	}
	
	//Get the Procedure with name "name"
	public Procedure getProc(String name){
		for (Procedure p : procedureList){
			if(p.getProcName().equals(name)){
				return p;
			}
		}
		return null;
	}
	
	//Get the Procedure with name "name"
		public Procedure getProc(int no){
			for (Procedure p : procedureList){
				if(p.getProcNo() == no){
					return p;
				}
			}
			return null;
		}
	
	public Vector<Patient> getPatientList() {
		return patientList;
	}

	public Vector<Procedure> getProcedureList() {
		return procedureList;
	}
	
	public void updateNos(){
		for(int i=0;i<patientList.size();i++){
			Patient pat = patientList.get(i);
			Patient.setNo(Math.max(Patient.getNo(), pat.getPatientNo()+1));
			Vector<Invoice> listInv = pat.getP_invoiceList();
			for (int j=0; j<listInv.size();j++){
				Invoice inv = listInv.get(j);
				Invoice.setNo(Math.max(Invoice.getNo(), inv.getInvoiceNo()+1));
				Vector<Payment> listPay = inv.getP_paymentList();
				for (int k=0; k<listPay.size();k++){
					Payment pay = listPay.get(k);
					Payment.setNo(Math.max(Payment.getNo(), pay.getPaymentNo()+1));
				}
			}
		}
		for(int i=0;i<procedureList.size();i++){
			Procedure proc = procedureList.get(i);
			Procedure.setNo(Math.max(Procedure.getNo(), proc.getProcNo()+1));
		}
	}
	
	/*public static void main(String[] args){
		//Creating the main application
		MainApplication mainapp = new MainApplication();
		//Adding the procedures
		Procedure proc1 = new Procedure("X-Ray", 50);
		mainapp.addProcedure(proc1);
		Procedure proc2 = new Procedure("Filling", 50);
		mainapp.addProcedure(proc2);
		Procedure proc3 = new Procedure("Crown", 200);
		mainapp.addProcedure(proc3);
		//Are all procedures here ?
		System.out.println("All procedures are here ? "+
				(mainapp.procisIn("X-Ray")||mainapp.procisIn("Filling")||
						mainapp.procisIn("Crown")) );		
		//Adding invoices
		Invoice iP1 = new Invoice(01,01);
		Invoice iP21 = new Invoice(02,01);
		Invoice iP22 = new Invoice(11,02);
		Invoice iP3 = new Invoice(11,02);
		//Adding payments
		Payment pay1 = new Payment(50, 02, 01);
		Payment pay21 = new Payment(40, 05, 01);
		Payment pay22 = new Payment(210, 05, 01);
		Payment pay3 = new Payment(210, 01, 03);
		Payment pay4 = new Payment(50, 02, 03);
		//Adding the patients
		Patient p1 = new Patient("p1","42 street toto","0665355570", iP1, proc1, pay1);
		System.out.println("Has p1 paid all of his procedures ? "+p1.hasPaid());
		mainapp.addPatient(p1);
		Patient p2 = new Patient("p2","43 street toto","0665355571", iP21, proc1, pay21);
		p2.addInvoice(iP22);
		p2.addProc(proc3,iP22);
		System.out.println("Has p2 paid all of his procedures ? "+p2.hasPaid());
		mainapp.addPatient(p2);
		Patient p3 = new Patient("p3","44 street toto","0665355572", iP3, proc2, pay3);
		p3.addProc(proc3,iP3);
		System.out.println("Has p3 paid all of his procedures ? "+p3.hasPaid());
		p3.addPayment(pay4, iP3);
		System.out.println("Has p3 paid all of his procedures ? "+p3.hasPaid());
		mainapp.addPatient(p3);
		//Are all patients here ?
		System.out.println("All patients are here ? "+
				(mainapp.patientisIn("p1")||mainapp.patientisIn("p2")||
						mainapp.patientisIn("p3")) );
	}*/

}
