import java.util.HashMap;



public class Trip {

	private String type;
	private int startMonth;
	private int startDay;
	private int endMonth;
	private int endDay;
	private HashMap<Integer, Integer> monthMap;
	
	public Trip(String type, String startDate, String endDate){
		this.type = type;
		this.startMonth = Integer.valueOf(startDate.substring(0, startDate.lastIndexOf('/')));
		this.endMonth = Integer.valueOf(endDate.substring(0, endDate.lastIndexOf('/')));
		this.startDay = Integer.valueOf(startDate.substring(startDate.lastIndexOf('/')+1, startDate.length()));
		this.endDay = Integer.valueOf(startDate.substring(endDate.lastIndexOf('/')+1, endDate.length()));
		this.monthMap = new HashMap<Integer, Integer>();
		this.monthMap.put(4, 30);
		this.monthMap.put(5, 31);
		this.monthMap.put(6, 30);
		this.monthMap.put(7, 31);
		this.monthMap.put(8, 31);
		this.monthMap.put(9, 30);
		this.monthMap.put(10, 31);
	}

	public Integer calcLength() {
		if(endMonth == startMonth)
			return endDay-startDay;
		else
			return monthMap.get(endMonth) + endDay - startDay;
	}

	public String getType() {
		return this.type;
	}

}
