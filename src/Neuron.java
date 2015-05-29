import java.util.ArrayList;
import java.util.Random;

public class Neuron {

	private ArrayList<Connection> inputFrom;
	private double inputValue;
	private float biasWeight;
	private double output;
	private boolean input;
	private double delta;

	public Neuron(boolean input) {
		delta=0;
		output = 0;
		if (input) {
			this.input = true;
		} else {
			inputFrom = new ArrayList<Connection>();
			Random rd = new Random();
			biasWeight = rd.nextFloat() * 2 - 1;

			this.input = false;
		}
	}

	public void calculateOutput() {

		if (input) {
			
			output=inputValue;
			output += biasWeight * Network.BIAS;
			output = Network.sigmoid(output);

		} else {
			for (Connection connection : inputFrom) {

				output += connection.getWei() * connection.getOri().getOutput();

			}
			output += biasWeight * Network.BIAS;
			output = Network.sigmoid(output);

		}
		
	}
	public void addBiasWeight(double f){
		biasWeight+=f;
	}

	public void addCon(Connection con) {
		inputFrom.add(con);
	}

	public void addInptVal(double f) {
		inputValue = f;
	}

	public double getOutput() {
		return output;
	}
	
	public ArrayList<Connection> getCon(){
		return inputFrom;
	}
	public void setDelta(double del){
		this.delta=del;
	}
	public double getDelta(){
		return delta;
	}
}
