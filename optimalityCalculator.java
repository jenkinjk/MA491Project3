import java.util.ArrayList;


public class optimalityCalculator {
	
	private ArrayList<Trip> tripPlan; 

	public float calcValue(float alpha, float beta, float gamma, float delta){
		return alpha * calcNumber() + beta * calcContact() + gamma* calcSpeedDiversity() + delta*calcTypeDiversity();
	}

	private float calcTypeDiversity() {
		int paddles =0, motor = 0;
		for(Trip t: tripPlan){
			if(t.type.equals("Oar")){
				paddles=paddles+1;
			}else
				motor=motor+1;
		}
		float ratio = Math.min(paddles/motor, motor/paddles);
		return ratio;
	}

	private float calcSpeedDiversity() {
		ArrayList<Integer> lengths = new ArrayList<Integer>();
		for(Trip t: tripPlan){
			lengths.add(t.calcLength());
		}
		return calcDiversity(lengths);
	}

	private float calcDiversity(ArrayList<Integer> lengths) {
		return 1;
	}

	private float calcContact() {
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

	private float calcNumber() {
		return tripPlan.size();
	}
	
}
