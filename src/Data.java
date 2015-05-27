
public class Data {

	int local;
	float[] input;
	
	public Data(int loc, float[] inp){
		local=loc;
		input=inp;
	}
	
	public int getLocal(){
		return local;
	}
	
	public float[] getInput(){
		return input;
	}
	
	public String toString(){
		String s="LOCATION: " + local + " DATA: ";
		for(int i=0;i<68;i++){
			s+=input[i];
		}
		return s;
	}
}
