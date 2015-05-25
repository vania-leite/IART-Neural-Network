import java.util.ArrayList;

public class teste {

	public static void main(String[] args) {

		Network testNet = new Network();
		Layer inLayer = new Layer(2, 1);
		Layer hLayer1 = new Layer(2, 2);
		Layer hLayer2 = new Layer(2, 2);
		Layer outLayer = new Layer(1, 3);

		testNet.replaceInputLayer(inLayer);
		testNet.replaceOutputLayer(outLayer);
		testNet.addHiddenLayer(hLayer1);
		testNet.addHiddenLayer(hLayer2);
		testNet.createConnections();

		testNet.printNet();

		ArrayList<Float> targetValues = new ArrayList<Float>();
		targetValues.add(1.0f);

		ArrayList<Float> inps = new ArrayList<Float>();
		inps.add(0.9f);
		inps.add(0.8f);

		testNet.setInputValues(inps);
		testNet.setTargetValues(targetValues);
		for (int i = 0; i < 100000; i++) {
			testNet.calculate();
			testNet.updateWeights();
			System.out
					.println("Error: " + testNet.calculateError(targetValues));

		}
		System.out.println("Final value: " + outLayer.getNeurons().get(0).getOutput());

	}

}
