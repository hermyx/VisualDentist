package fundation;
import java.util.Vector;


public class MainApplication {
	
	private Vector<Patient> patientList;
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
		for(Patient p : patientList){
			if(p.getPatientNo()==n){
				patientList.remove(p);
			}
		}
	}
	
	public Patient getPatient(int n){
		//Initialization in case the search don't work
		Patient ret=new Patient("Not","in","the database",new Invoice(1,1));
		for(Patient p : patientList){
			if(p.getPatientNo()==n){
				ret=p;
			}
		}
		return ret;
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
		for(Procedure p : procedureList){
			if(p.getProcNo()==n){
				procedureList.remove(p);
			}
		}
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
	
	public Vector<Patient> getPatientList() {
		return patientList;
	}

	public Vector<Procedure> getProcedureList() {
		return procedureList;
	}
	
	public static void main(String[] args){
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
	}

}
