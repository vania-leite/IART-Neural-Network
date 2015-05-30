package ai;
import java.util.ArrayList;


public class Layer {
	
	//1 ->input Layer, 2 -> hiddenLayer, 3 -> output layer
	private int mode;
	private ArrayList<Neuron> neurons;
	
	public Layer(int nNeurons, int mode){
		this.mode = mode;
		boolean in = mode==1;
		neurons =  new ArrayList<Neuron>();
		for(int i=0;i< nNeurons;i++){
			neurons.add(new Neuron(in));
		}
		
	}
	public Neuron getNeuron(int n){
		return neurons.get(n);
	}
	public ArrayList<Neuron> getNeurons(){
		return neurons;
	}
	public void printLayer() {
		System.out.println("this layer has " + neurons.size() + " nodes");
		
	}
	public void calculateLayer(){
		for (Neuron neuron : neurons) {
			neuron.calculateOutput();
		}
	}
}
