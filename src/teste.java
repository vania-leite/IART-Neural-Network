import java.util.ArrayList;

public class teste {

	public static void main(String[] args) {

		Network testNet = new Network();
		Layer inLayer = new Layer(2, 1);
		Layer hLayer1 = new Layer(20, 2);
		Layer hLayer2 = new Layer(20, 2);
		Layer outLayer = new Layer(4, 3);

		testNet.replaceInputLayer(inLayer);
		testNet.replaceOutputLayer(outLayer);
		testNet.addHiddenLayer(hLayer1);
		testNet.addHiddenLayer(hLayer2);
		testNet.createConnections();

		testNet.printNet();

		ArrayList<Float> targetValues = new ArrayList<Float>();
		targetValues.add(1.0f);
		targetValues.add(0.0f);
		targetValues.add(0.0f);
		targetValues.add(1.0f);

		ArrayList<Float> inps = new ArrayList<Float>();
		inps.add(0.9f);
		inps.add(0.3f);

		testNet.setInputValues(inps);
		testNet.setTargetValues(targetValues);

		int tenper = 100000;
		int i = 0;
		double error;
		double preverror;
		testNet.calculate();
		testNet.updateWeights();
		error = testNet.calculateError(targetValues);
		do {
			preverror = error;
			testNet.calculate();
			testNet.updateWeights();
			error = testNet.calculateError(targetValues);
			if (i % tenper == 0) {
				System.out.println("Error: " + error);
				System.out.println("Final value1: "
						+ outLayer.getNeurons().get(0).getOutput());
				System.out.println("Final value2: "
						+ outLayer.getNeurons().get(1).getOutput());
				System.out.println("Final value3: "
						+ outLayer.getNeurons().get(2).getOutput());
				System.out.println("Final value4: "
						+ outLayer.getNeurons().get(3).getOutput());
			}
			i++;
		} while (((preverror - error) > 0.000000001) || i < 1000);
		System.out.println("Leaving cicle");
		System.out.println("Error: " + error);
		System.out.println("Final value1: "
				+ outLayer.getNeurons().get(0).getOutput());
		System.out.println("Final value2: "
				+ outLayer.getNeurons().get(1).getOutput());
		System.out.println("Final value3: "
				+ outLayer.getNeurons().get(2).getOutput());
		System.out.println("Final value4: "
				+ outLayer.getNeurons().get(3).getOutput());

		return;
	}
}
