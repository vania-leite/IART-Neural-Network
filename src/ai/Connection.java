package ai;

import java.util.Random;

public class Connection {

	private Neuron ori;
	private Neuron dest;
	private double weight;
	private double lastDelta;
	private double learning_rate;

	public Connection(Neuron ori, Neuron dest) {

		this.ori = ori;
		this.dest = dest;
		this.lastDelta = 0;
		this.learning_rate = Network.ETA;
		Random rd = new Random();
		this.weight = rd.nextFloat() - 0.5; // setting wieght to a random number
											// beeween [-0.5,0.5]

	}

	public Neuron getOri() {
		return ori;
	}

	public Neuron getDest() {
		return dest;
	}

	public double getWei() {
		return weight;
	}

	public void addWei(double del) {
		weight += del + Network.M * lastDelta;
		lastDelta = del;
	}

	public double getDelta() {
		return lastDelta;
	}

	public double getLR() {
		return learning_rate;
	}

	public void multiplyLearningRate(double v) {
		learning_rate *= v;
		if (learning_rate > 0.9) {
			learning_rate = 0.9;
		}
		else if(learning_rate < 0.05){
			learning_rate = 0.05;
		}
	}
}
