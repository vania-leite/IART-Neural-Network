package ai;
import java.util.ArrayList;
import java.util.Random;

public class Neuron {

	private ArrayList<Connection> inputFrom;
	private double inputValue;
	private double prevOutput;
	private double biasWeight;
	private double output;
	private boolean input;
	private double grad;
	private double prevGrad;

	public Neuron(boolean input) {
		grad=0;
		prevGrad=0;
		prevOutput=0.0;
		output = 0;
		if (input) {
			this.input = true;
		} else {
			inputFrom = new ArrayList<Connection>();
			Random rd = new Random();
			biasWeight = rd.nextDouble() - 0.5;

			this.input = false;
		}
	}

	public void calculateOutput() {
		prevOutput= output;
		if (input) {
			
			output=inputValue;
			//output += biasWeight * Network.BIAS;
			//output = Network.sigmoid(output);

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
	public double getPrevOutput(){
		
		return prevOutput;
	}
	
	public ArrayList<Connection> getCon(){
		return inputFrom;
	}
	public void setGrad(double gra){
		setPrevGrad(this.grad);
		this.grad=gra;
	}
	public double getGrad(){
		return grad;
	}
	public void setPrevGrad(double gra){
		this.prevGrad=gra;
	}
	public double getPrevGrad(){
		return prevGrad;
	}
	
}
