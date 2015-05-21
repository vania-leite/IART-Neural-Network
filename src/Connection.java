import java.util.Random;


public class Connection {

	private Neuron ori;
	private Neuron dest;
	private double weight;
	
	public Connection(Neuron ori,Neuron dest){
		
		this.ori=ori;
		this.dest=dest;
		Random rd = new Random();
		this.weight= rd.nextFloat() * 2 -1; //setting wieght to a random number beeween [-1,1]
		
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
}
