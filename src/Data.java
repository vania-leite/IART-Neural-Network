import java.util.ArrayList;


public class Data {

	private int local;
	private ArrayList<Double> input;
	
	public Data(int loc, ArrayList<Double> inp){
		local=loc;
		input=inp;
	}
	
	public int getLocal(){
		return local;
	}
	public ArrayList<Double> getTargetValues(){
		
		ArrayList<Double> temp = new ArrayList<Double>();
		for(int i=0;i<33;i++){
			if(i==local-1){
				temp.add(1.0);
			}
			else{
				temp.add(0.0);
			}
		}
		return temp;
		
	}
	public ArrayList<Double> getInput(){
		return input;
	}
	
	public String toString(){
		String s="LOCATION: " + local + " DATA: ";
		for(int i=0;i<68;i++){
			s+=input.get(i);
		}
		return s;
	}
}
