package fundation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Procedure")
//If you want you can define the order in which the fields are written
//Optional
@XmlType(propOrder = { "procName", "procCost", "procNo"})
@XmlAccessorType(XmlAccessType.FIELD)
public class Procedure {
	private static int No = 0;
	
	private int procNo;
	private String procName;
	private double procCost;
	
	public Procedure(){
		
	}
	
	public Procedure(String s, double d){
		procNo = No++;
		procName=s;
		procCost=d;
	}

	public int getProcNo() {
		return procNo;
	}
	public String getProcName() {
		return procName;
	}
	public void setProcName(String procName) {
		this.procName = procName;
	}
	public double getProcCost() {
		return procCost;
	}
	public void setProcCost(double procCost) {
		this.procCost = procCost;
	}

	public String toString() {
		return "Procedure [procNo=" + procNo + ", procName=" + procName
				+ ", procCost=" + procCost + "]";
	}

	public static int getNo() {
		return No;
	}

	public static void setNo(int no) {
		No = no;
	}

}
