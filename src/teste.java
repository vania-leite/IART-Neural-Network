import java.util.ArrayList;


public class teste {

	public static void main(String[] args) {

		
		Network testNet = new Network();
		Layer inLayer= new Layer(2,1);
		Layer hLayer1 = new Layer(2,2);
		Layer outLayer = new Layer(1,3);
		
		testNet.replaceInputLayer(inLayer);
		testNet.replaceOutputLayer(outLayer);
		testNet.addHiddenLayer(hLayer1);
		testNet.createConnections();
		
		testNet.printNet();
		
		ArrayList<Float> targetValues = new ArrayList<Float>();
		targetValues.add(1.0f);
		
		ArrayList<Float> inps = new ArrayList<Float>();
		inps.add(0.9f);
		inps.add(0.8f);
		
		testNet.setInputValues(inps);
		testNet.setTargetValues(targetValues);
		testNet.calculate();
		testNet.updateWeights();
		
		System.out.println(testNet.calculateError(targetValues));
		
		System.out.println(outLayer.getNeurons().get(0).getOutput());
	}

}
