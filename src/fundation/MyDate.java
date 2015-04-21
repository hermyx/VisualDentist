package fundation;
import java.io.Serializable;
import java.util.Calendar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "MyDate")
@XmlType(propOrder = { "day", "month" })
@XmlAccessorType(XmlAccessType.FIELD)
public class MyDate implements Serializable{
	
	private static final long serialVersionUID = -3591859654326841558L;
	private int day;
	private int month;

	public MyDate(int d, int m){
		day=d;
		month=m;
	}
	
	/**
	 * @param s : String in the format day/month
	 */
	public MyDate(String s){
		String[] parts = s.split("/");
		String d = parts[0]; 
		int day = Integer.parseInt(d);
		String m = parts[1];
		int month = Integer.parseInt(m);
		this.day=day;
		this.month=month;
	}
	
	public MyDate(){
		day=1;
		month=1;
	}
	
	public boolean isRealDate(){
		return (day > 0 && day <= 31 && month > 0 && month <= 12 );
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}
	
	public void getToToday(){
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH);
		this.day=day;
		this.month=month+1;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MyDate other = (MyDate) obj;
		if (day != other.day)
			return false;
		if (month != other.month)
			return false;
		return true;
	}
	
	@Override
	public String toString(){
		return ""+this.day+"/"+this.month+"";
	}
	
	
}
