import java.util.ArrayList;

public class Network {

	public static int BIAS = 1;
	public static float ETA = 0.1f;
	private Layer inputLayer;
	private ArrayList<Layer> hidenLayers;
	private Layer outputLayer;
	private ArrayList<Float> targetValues;

	public Network() {
		hidenLayers = new ArrayList<Layer>();
		targetValues = new ArrayList<Float>();
	}

	public void setTargetValues(ArrayList<Float> target){
		targetValues=target;
	}
	public void replaceInputLayer(Layer layer) {
		inputLayer = layer;
	}

	public void replaceOutputLayer(Layer layer) {
		outputLayer = layer;
	}

	public void addHiddenLayer(Layer layer) {
		hidenLayers.add(layer);
	}

	public void createConnections() {
		if (inputLayer == null | outputLayer == null | hidenLayers.size() == 0) {
			System.out.println("not enough layers");
			return;
		}
		connectLayers(inputLayer, hidenLayers.get(0));
		int chlyr = 0;
		while (chlyr < hidenLayers.size() - 1) {
			connectLayers(hidenLayers.get(chlyr), hidenLayers.get(chlyr + 1));
			chlyr++;
		}
		connectLayers(hidenLayers.get(chlyr), outputLayer);

	}

	private void connectLayers(Layer layer1, Layer layer2) {
		ArrayList<Neuron> temp2 = layer2.getNeurons();
		ArrayList<Neuron> temp1 = layer1.getNeurons();
		for (Neuron neuron2 : temp2) {
			for (Neuron neuron1 : temp1) {
				neuron2.addCon(new Connection(neuron1, neuron2));
			}

		}

	}

	public static double sigmoid(double e) {
		return 1 / (1 + Math.exp(-e));
	}

	public void setInputValues(ArrayList<Float> inputs) {
		ArrayList<Neuron> temp = inputLayer.getNeurons();
		int i = 0;
		for (Neuron neuron : temp) {
			neuron.addInptVal(inputs.get(i));
			i++;

		}
	}

	public void printNet() {
		inputLayer.printLayer();
		for (Layer layer : hidenLayers) {
			layer.printLayer();
		}
		outputLayer.printLayer();
	}

	public void calculate() {
		inputLayer.calculateLayer();
		for (Layer layer : hidenLayers) {
			layer.calculateLayer();
		}
		outputLayer.calculateLayer();
	}
	
	public void updateWeights(){
		
		for (int i=0;i< hidenLayers.size() -1;i++) {
			Layer layer = hidenLayers.get(i);
			Layer nextLayer = hidenLayers.get(i+1);
			ArrayList<Neuron> neurons= layer.getNeurons();
			for (int j=0;j< neurons.size();j++) {
				
				System.out.println("deltaj calcualded for node"+j+" in hiden layer "+i+"is: " + deltaJ(layer, j, nextLayer));
			}
		}
		Layer layer = hidenLayers.get(hidenLayers.size()-1);
		ArrayList<Neuron> neurons=layer.getNeurons();
		for (int j=0;j< neurons.size();j++) {
			
			System.out.println("deltaj calcualded for node"+j+" in the last hiden layer is: " + deltaJ(layer, j, outputLayer));
		}		
	}
	
	private double deltaK(Layer layer,int k){
		
		double outputK = layer.getNeuron(k).getOutput();
		double targetK = targetValues.get(k);
		return outputK * (1 -outputK)*(outputK - targetK);
	}
	private double deltaJ(Layer layer,int j,Layer nextLayer){
		
		Neuron neuronJ= layer.getNeuron(j);
		double outputJ = neuronJ.getOutput();
		double sum=0.0;
		for(int i=0;i < nextLayer.getNeurons().size();i++){
			
			ArrayList<Connection> con = nextLayer.getNeuron(i).getCon();
			for (Connection connection : con) {
				if(connection.getOri().equals(neuronJ)){
					sum+= deltaK(nextLayer,i)* connection.getWei();
					break;
				}
			}
			
		}
		
		return sum*outputJ*(1- outputJ);
	}

	public double calculateError(ArrayList<Float> targetValues) {
		
		
		if(outputLayer.getNeurons().size() != targetValues.size()){
			System.out.println("Error, output size and target values sizer are diferent");
			return -1;
		}
		double error=0;
		for(int i=0;i<targetValues.size();i++){
			error+=Math.pow(outputLayer.getNeurons().get(i).getOutput() - targetValues.get(i), 2);
		}
		error= error/2;
		
		return error;
	}
}
