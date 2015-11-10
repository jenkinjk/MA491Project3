import java.util.Comparator;


public class myComparator implements Comparator<Trip> {

	@Override
	public int compare(Trip o1, Trip o2) {
		return -o1.getContact()+o2.getContact();
	}

}
