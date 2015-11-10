import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;

public class OptimalityCalculator {

	private PriorityQueue<Trip> tripPlan;
	private double alpha;
	private double beta;
	private double gamma;
	private double delta;
	private int[] tripsOnRiverPerDay;

	public OptimalityCalculator(PriorityQueue<Trip> trips,double a, double b, double c, double d) {
		this.tripPlan = trips;
		this.alpha= a;
		this.beta=b;
		this.gamma = c;
		this.delta = d;
		this.tripsOnRiverPerDay = new int[214];
		for(int i =0; i<214; i++){
			tripsOnRiverPerDay[i]=0;
		}
		for(Trip t: trips){
			incrementTrips(t);
		}
	}
	
	private void incrementTrips(Trip t) {
		for(int i = t.getStartDateInt()-1; i< t.getEndDateInt()-2; i++){
			this.tripsOnRiverPerDay[i] = this.tripsOnRiverPerDay[i]+1;
		}
	}

	public void optimize(){
		double pre = calcValue();
		double post = pre + .1001;
		while(pre + .1 < post){
			addNew();
			removeWorst();
			pre = post;
			post = calcValue();
			if(post!=pre)
				System.out.println("The new value is: "+ post +" versus the old: "+pre);
		}
		System.out.println("The optimal value is: "+post);
		System.out.println("The schedule is: ");
		ArrayList<Trip> trips = new ArrayList<Trip>();
		trips.addAll(this.tripPlan);
		trips.sort(new myComparator2());
		for(Trip t: trips){
			System.out.println(t.getType()+"trip "+t.toString() + " starts on " + t.getStartMonth()+ "/" + t.getStartDay()+ " and ends on "+t.getEndMonth()+"/"+t.getEndDay());
		}
		for(int i: this.tripsOnRiverPerDay){
			System.out.println(i);
		}
	}

	private void addNew() {
		String type = calcType();
		int length = genLength(type);
		String window = findWindow(length);
		Trip t = new Trip(type, window.substring(0, window.lastIndexOf("-")), window.substring(window.lastIndexOf("-")+1, window.length()));
		double pre = calcValue();
		this.tripPlan.add(t);
		if(pre>calcValue()){
			this.tripPlan.remove(t);
		}
		incrementTrips(t);
	}

	private String findWindow(int length) {
		double min = 1000000000;
		String range = "";
		for(int i =0; i < 214-length; i++){
			double contacts = calcTrips(i, length);
			if(contacts < min){
				min = contacts;
				range = getDate(i)+"-"+getDate(i+length);
			}
		}
		return range;
	}

	private double calcTrips(int i, int length) {
		int total = 0;
		for(int j = i; j<length; j++){
			total += this.tripsOnRiverPerDay[j];
		}
		return total;
	}

	private String getDate(int i) {
		String result = "";
		if(i<30){
			result="4/"+(i+1);
		}else if (i < 61){
			result = "5/"+(i-29);
		}else if(i < 91){
			result = "6/"+(i-60);
		}else if(i < 122){
			result = "7/"+(i-90);
		}else if(i < 153){
			result = "8/"+(i-121);
		}else if(i < 183){
			result = "9/"+(i-152);
		}else{
			result = "10/"+(i-182);
		}
		return result;
	}

	private int genLength(String type) {
		Random rand = new Random();
		if(type.equals("Oar")){
			return rand.nextInt(12)+6;
		}else{
			return rand.nextInt(6)+6;
		}
	}

	private String calcType() {
		int oarCount =0, motorCount =0;
		for(Trip t: this.tripPlan){
			if(t.getType().equals("Oar")){
				oarCount++;
			}else{
				motorCount++;
			}
		}
		if(oarCount>motorCount)
			return "Motor";
		else
			return "Oar";
	}

	private void removeWorst() {
		PriorityQueue<Trip> temp = new PriorityQueue<Trip>(new myComparator());
		temp.addAll(tripPlan);
		double prePop = calcValue();
		Trip popped = tripPlan.poll();
		double postPop = calcValue();
		if(prePop>postPop){
			this.tripPlan.offer(popped);
		}else{
			decrementTrips(popped);
		}
	}

	private void decrementTrips(Trip t) {
		for(int i = t.getStartDateInt()-1; i< t.getEndDateInt()-2; i++){
			this.tripsOnRiverPerDay[i] = this.tripsOnRiverPerDay[i]-1;
		}
		
	}

	public double calcValue() {
		double result = alpha * calcNumber() + beta * calcContact() + gamma
				* calcSpeedDiversity() + delta * calcTypeDiversity();
		reogranizeTrips();
		return result;
	}

	private void reogranizeTrips() {
		PriorityQueue<Trip> t = new PriorityQueue<Trip>(new myComparator());
		for(Trip trip: this.tripPlan){
			t.offer(trip);
		}
		this.tripPlan = t;
		//Code to ensure that things are reogranized correctly.
//		for(Trip trip: this.tripPlan){
//			System.out.println(trip.getContact());
//		}
	}

	private double calcTypeDiversity() {
		int paddles = 0, motor = 0;
		for (Trip t : tripPlan) {
			if (t.getType().equals("Oar")) {
				paddles = paddles + 1;
			} else
				motor = motor + 1;
		}
		double ratio = Math.min(paddles / motor, motor / paddles);
		return ratio/500;
	}

	private double calcSpeedDiversity() {
		ArrayList<Integer> lengths = new ArrayList<Integer>();
		for (Trip t : tripPlan) {
			lengths.add(t.calcLength());
		}
		return calcDiversity(lengths);
	}

	private double calcDiversity(ArrayList<Integer> lengths) {
		// Simple test to see how many standard devs the max is, as is the min.
		int max = findMax(lengths), min = findMin(lengths);
		double stdMax = (max - getMean(lengths)) / getStdDev(lengths);
		double stdMin = (min - getMean(lengths)) / getStdDev(lengths);
		double result = (1 / stdMax + 1 / stdMin)/2;
		return result;
	}

	private int findMax(ArrayList<Integer> lengths) {
		int max = 0;
		for (int i : lengths) {
			if (i > max)
				max = i;
		}
		return max;
	}

	private int findMin(ArrayList<Integer> lengths) {
		int min = 20;
		for (int i : lengths) {
			if (i < min)
				min = i;
		}
		return min;
	}

	double getMean(ArrayList<Integer> lengths) {
		double sum = 0.0;
		for (double a : lengths)
			sum += a;
		return sum / lengths.size();
	}

	double getVariance(ArrayList<Integer> lengths) {
		double mean = getMean(lengths);
		double temp = 0;
		for (double a : lengths)
			temp += (mean - a) * (mean - a);
		return temp / lengths.size();
	}

	double getStdDev(ArrayList<Integer> lengths) {
		return Math.sqrt(getVariance(lengths));
	}

	private double calcContact() {
		int overlap = 0;
		for (Trip t : tripPlan) {
			t.setContact(0);
			for (Trip t2 : tripPlan) {
				if (!(t.equals(t2))) {
					int tmpOverlap = calcOverlap(t, t2);
					t.setContact(t.getContact() + tmpOverlap);
					if(!t.getType().equals(t2.getType()))
						overlap = overlap + tmpOverlap;
				}
			}
		}
		return 1/(1+(overlap/2));
	}

	private int calcOverlap(Trip t, Trip t2) {
		Trip first = first(t, t2), second;
		if (t.equals(first)) {
			second = t2;
		} else
			second = t;
		if (second.endsBefore(first.getEndDate(second)))
			return 1;
		return 0;
	}

	private Trip first(Trip t, Trip t2) {
		if (t.getStartMonth() > t2.getStartMonth()) {
			return t2;
		}
		if (t.getStartMonth() < t2.getStartMonth()) {
			return t;
		} else {
			if (t.getStartDay() > t2.getStartDay()) {
				return t2;
			} else {
				return t;
			}
		}

	}

	private double calcNumber() {
		return tripPlan.size()/1000;
	}

}
