package ai;
import java.util.Random;


public class Connection {

	private Neuron ori;
	private Neuron dest;
	private double weight;
	private double lastDelta;
	
	public Connection(Neuron ori,Neuron dest){
		
		this.ori=ori;
		this.dest=dest;
		this.lastDelta=0;
		Random rd = new Random();
		this.weight= rd.nextFloat() -0.5; //setting wieght to a random number beeween [-0.5,0.5]
		
	}
	public Neuron getOri(){
		return ori;
	}
	public Neuron getDest(){
		return dest;
	}
	public double getWei(){
		return weight;
	}
	public void addWei(double del){
		weight+= del + Network.M*lastDelta;
		lastDelta=del;
	}
	public double getDelta(){
		return lastDelta;
	}
}
