import java.util.Comparator;



public class myComparator2 implements Comparator<Trip> {

	@Override
	public int compare(Trip o1, Trip o2) {
		if(o1.getStartMonth()!=o2.getStartMonth()){
			return o1.getStartMonth()-o2.getStartMonth();
		}
		else{
			return o1.getStartDay() - o2.getStartDay();
		}
	}

}
