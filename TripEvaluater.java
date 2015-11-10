import java.util.PriorityQueue;


public class TripEvaluater {

	public static void main(String[] args) {
		Trip t = new Trip("Oar", "5/12", "5/22");
		Trip t2 = new Trip("Motor", "5/23", "6/4");
		Trip t3 = new Trip("Oar", "6/2", "6/14");
		Trip t5 = new Trip("Oar", "6/4","6/16");
		Trip t6 = new Trip("Motor", "6/6", "6/13");
		Trip t4 = new Trip("Oar", "7/12", "7/18");
		Trip t7 = new Trip("Motor", "5/20", "5/28");
		PriorityQueue<Trip> trips = new PriorityQueue<Trip>(new myComparator());
		trips.add(t);
		trips.add(t2);
		trips.add(t4);
		trips.add(t5);
		trips.add(t6);
		trips.add(t3);
		trips.add(t7);
		OptimalityCalculator te = new OptimalityCalculator(trips,.5,.25,.125,.125);
		te.optimize();
	}

}
