import java.io.IOException;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Random;

public class teste {

	public static void main(String[] args) throws IOException {

		Network testNet = new Network();
		Layer inLayer = new Layer(68, 1);
		Layer hLayer1 = new Layer(100, 2);
		Layer outLayer = new Layer(33, 3);

		testNet.replaceInputLayer(inLayer);
		testNet.replaceOutputLayer(outLayer);
		testNet.addHiddenLayer(hLayer1);
		testNet.createConnections();

		testNet.printNet();

		DataHandler.loadTrainingInputs();
		
		System.out.println(DataHandler.coord.size());
		Random rd= new Random();
		int k = (int) (rd.nextDouble()*1000);
		ArrayList<Double> targetValues;
		targetValues = DataHandler.coord.get(k).getTargetValues();
		ArrayList<Double> inps;
		inps = DataHandler.coord.get(k).getInput();

		testNet.setInputValues(inps);
		testNet.setTargetValues(targetValues);

		int tenper = 100000;
		int i = 0;
		double error;
		for(int q=0;q<targetValues.size();q++){
			if(targetValues.get(q) == 1.0){
				System.out.println("Neuron "+q+"is expected as on");
			}
		}
		do {
			testNet.calculate();
			testNet.updateWeights();
			error = testNet.calculateError(targetValues);
			if (i % tenper == 0) {
				System.out.println("Error: " + error);
				for (int j = 0; j < 33; j++) {
					System.out.print("Final value" + j + ": "
							+ outLayer.getNeurons().get(j).getOutput());
					if (outLayer.getNeurons().get(j).getOutput() > 0.7)
						System.out.println(" ---ON");
					else {
						System.out.println("");
					}
				}

			}
			i++;

		} while (i < 1000000);
		System.out.println("Leaving cicle");
		System.out.println("Error: " + error);
		for (int j = 0; j < 33; j++) {
			System.out.print("Final value" + j + ": "
					+ outLayer.getNeurons().get(j).getOutput());
			if (outLayer.getNeurons().get(j).getOutput() > 0.7)
				System.out.println(" ---ON");
			else {
				System.out.println("");
			}
		}

		return;

	}

}
