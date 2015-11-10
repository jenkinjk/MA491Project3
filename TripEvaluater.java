import java.util.PriorityQueue;


public class TripEvaluater {

	public static void main(String[] args) {
		Trip t = new Trip("Oar", "5/12", "5/22");
		Trip t2 = new Trip("Motor", "5/23", "6/4");
		Trip t3 = new Trip("Oar", "6/2", "6/8");
		Trip t4 = new Trip("Oar", "7/12", "7/18");
		PriorityQueue<Trip> trips = new PriorityQueue<Trip>(new myComparator());
		trips.add(t);
		trips.add(t2);
		trips.add(t3);
		trips.add(t4);
		OptimalityCalculator te = new OptimalityCalculator(trips);
		System.out.println("Trip optimality is: " + te.calcValue(1, 1, 1, 1, 1));
	}

}
