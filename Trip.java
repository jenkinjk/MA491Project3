import java.util.HashMap;



public class Trip {

	private String type;
	private int startMonth;
	private int startDay;
	private int endMonth;
	private int endDay;
	private HashMap<Integer, Integer> monthMap;
	private int speed;
	private int contact = 0;
	
	public Trip(String type, String startDate, String endDate){
		this.type = type;
		this.startMonth = Integer.valueOf(startDate.substring(0, startDate.lastIndexOf('/')));
		this.setEndMonth(Integer.valueOf(endDate.substring(0, endDate.lastIndexOf('/'))));
		this.startDay = Integer.valueOf(startDate.substring(startDate.lastIndexOf('/')+1, startDate.length()));
		this.setEndDay(Integer.valueOf(startDate.substring(endDate.lastIndexOf('/')+1, endDate.length())));
		this.monthMap = new HashMap<Integer, Integer>();
		this.monthMap.put(4, 30);
		this.monthMap.put(5, 31);
		this.monthMap.put(6, 30);
		this.monthMap.put(7, 31);
		this.monthMap.put(8, 31);
		this.monthMap.put(9, 30);
		this.monthMap.put(10, 31);
		if(type.equals("Oar"))
			speed = 4;
		else
			speed = 8;
	}

	public Integer calcLength() {
		if(getEndMonth() == startMonth)
			return getEndDay()-startDay;
		else
			return monthMap.get(getEndMonth()) + getEndDay() - startDay;
	}

	public String getType() {
		return this.type;
	}

	public double getSpeedPerDay() {
		double result = 225/speed;
		result = result/calcLength();
		return result;
	}

	public String getEndDate(Trip second) {
		return this.getEndMonth()+":"+this.getEndDay();
	}

	public boolean endsBefore(String endDate) {
		int endMonth = Integer.valueOf(endDate.substring(0, endDate.lastIndexOf(":")));
		int endDay = Integer.valueOf(endDate.substring(endDate.lastIndexOf(":")+1, endDate.length()));
		if(this.getEndMonth()>endMonth){
			return false;
		}if(this.getEndMonth() < endMonth){
			return true;
		}else{
			if(this.getEndDay() > endDay){
				return false;
			}else{
				return true;
			}
		}
	}

	public int getEndMonth() {
		return endMonth;
	}

	public void setEndMonth(int endMonth) {
		this.endMonth = endMonth;
	}

	public int getEndDay() {
		return endDay;
	}

	public void setEndDay(int endDay) {
		this.endDay = endDay;
	}

	public int getContact() {
		return this.contact ;
	}
	
	public void setContact(int i){
		this.contact = i;
	}

}
