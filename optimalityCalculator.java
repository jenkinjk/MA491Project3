import java.util.ArrayList;


public class OptimalityCalculator {
	
	private ArrayList<Trip> tripPlan; 

	public OptimalityCalculator(ArrayList<Trip> trips) {
		this.tripPlan = trips;
	}

	public double calcValue(double alpha, double beta, double gamma, double delta, double epsilon){
		//Amount of time on the river should be taken into account
		return alpha * calcNumber() - beta * calcContact() + gamma* calcSpeedDiversity() + delta*calcTypeDiversity() - epsilon*calcTimeOnRiver();
	}

	private double calcTimeOnRiver() {
		// TODO Auto-generated method stub
		return 0;
	}

	private double calcTypeDiversity() {
		int paddles =0, motor = 0;
		for(Trip t: tripPlan){
			if(t.getType().equals("Oar")){
				paddles=paddles+1;
			}else
				motor=motor+1;
		}
		double ratio = Math.min(paddles/motor, motor/paddles);
		return ratio;
	}

	private double calcSpeedDiversity() {
		ArrayList<Integer> lengths = new ArrayList<Integer>();
		for(Trip t: tripPlan){
			lengths.add(t.calcLength());
		}
		return calcDiversity(lengths);
	}

	private double calcDiversity(ArrayList<Integer> lengths) {
		//Simple test to see how many standard devs the max is, as is the min.
		int max = findMax(lengths), min = findMin(lengths);
		double stdMax = (max-getMean(lengths))/getStdDev(lengths);
		double stdMin = (min - getMean(lengths))/getStdDev(lengths);
		double result = 1/stdMax + 1/stdMin;
		return result;
	}
	
	private int findMax(ArrayList<Integer> lengths) {
		int max = 0;
		for(int i: lengths){
			if(i>max)
				max = i;
		}
		return max;
	}

	private int findMin(ArrayList<Integer> lengths) {
		int min = 20;
		for(int i: lengths){
			if(i<min)
				min = i;
		}
		return min;
	}

	double getMean(ArrayList<Integer> lengths)
    {
        double sum = 0.0;
        for(double a : lengths)
            sum += a;
        return sum/lengths.size();
    }

    double getVariance(ArrayList<Integer> lengths)
    {
        double mean = getMean(lengths);
        double temp = 0;
        for(double a :lengths)
            temp += (mean-a)*(mean-a);
        return temp/lengths.size();
    }

    double getStdDev(ArrayList<Integer> lengths)
    {
        return Math.sqrt(getVariance(lengths));
    }

	private double calcContact() {
		int overlap = 0;
		for(Trip t: tripPlan){
			for(Trip t2: tripPlan){
				if(!(t.equals(t2))){
					overlap = overlap + calcOverlap (t, t2);
				}
			}
		}
		return overlap;
	}

	private int calcOverlap(Trip t, Trip t2) {
		return 0;
	}

	private double calcNumber() {
		return tripPlan.size();
	}
	
}
