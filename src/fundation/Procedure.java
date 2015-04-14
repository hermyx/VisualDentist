package fundation;

public class Procedure {
	private static int No = 0;
	
	private int procNo;
	private String procName;
	private double procCost;
	
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

}
