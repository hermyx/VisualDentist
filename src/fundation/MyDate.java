package fundation;

import java.util.Calendar;

public class MyDate {
	private int day;
	private int month;

	public MyDate(int d, int m){
		day=d;
		month=m;
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
	
	public void stringToDate(String s){
		String[] parts = s.split("/");
		String d = parts[0]; 
		int day = Integer.parseInt(d);
		String m = parts[1];
		int month = Integer.parseInt(m);
		this.day=day;
		this.month=month;
	}
	
	public static void main(String args[]){
		MyDate d = new MyDate(0,0);
		d.getToToday();
		System.out.println(d.toString());
		d.stringToDate("20/06");
		System.out.println(d.toString());
		
	}
	
}
