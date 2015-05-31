package ai;
import java.util.ArrayList;
import java.util.Random;

import application.Main;

public class Data {

	private int local;
	private ArrayList<Double> input;
	private boolean used;
	private double error;

	public Data(int loc, ArrayList<Double> inp) {
		local = loc;
		input = inp;
		used = false;
		error = 0.0;
	}

	public double getError() {
		return error;
	}

	public void setError(double er) {
		error = er;
	}

	public boolean isUsed() {
		return used;
	}

	public void setAsUsed() {
		used = true;
	}

	public int getLocal() {
		return local;
	}

	public void resetTemps() {
		used = false;
		error = 0.0;
	}

	public ArrayList<Double> getTargetValues() {


		ArrayList<Double> temp = new ArrayList<Double>();
		for (int i = 0; i < Main.outNodes; i++) {
			if (i == local - 1) {

				temp.add(1.0);
			} else {
				temp.add(0.0);
			}
		}
		return temp;

	}

	public ArrayList<Double> getInput() {
		return input;
	}

	public String toString() {
		String s = "LOCATION: " + local + " DATA: ";
		for (int i = 0; i < 68; i++) {
			s += input.get(i);
		}
		return s;
	}
}
