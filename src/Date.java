
public class Date {
	private int day;
	private int month;

	public Date(int d, int m){
		day=d;
		month=m;
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Date other = (Date) obj;
		if (day != other.day)
			return false;
		if (month != other.month)
			return false;
		return true;
	}
	
}
