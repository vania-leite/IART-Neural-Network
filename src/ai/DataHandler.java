package ai;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class DataHandler {

	static String COORD = "coordsmudadas.txt";
	public static ArrayList<Data> coord;
	static int lastClass = -1;

	public static void loadTrainingInputs() throws IOException {
		coord = new ArrayList<Data>();
		FileInputStream is = new FileInputStream(COORD);
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String line = null;
		String[] thisLine = null;
		ArrayList<Double> thisInp = new ArrayList<Double>();
		Data thisData;
		int k=0;
		while ((line = reader.readLine()) != null && k <teste.NLINES) {
			thisLine = line.split(",");
			for (int i = 0; i < 68; i++) {
				thisInp.add(Double.valueOf(thisLine[i]));
			}
			int capital = Integer.parseInt(thisLine[68]);
			thisData = new Data(capital, thisInp);
			coord.add(thisData);
			k++;
		}

	}

	public static int getRandomData() {
		Random rand = new Random();
		int i = rand.nextInt(teste.NLINES);
		int k = 0;
		ArrayList<Integer> values = new ArrayList<Integer>();
		while (k < teste.NLINES) {
			if (!coord.get(i).isUsed()) {
				values.add(i);
				if (coord.get(i).getLocal() != lastClass) {
					lastClass = coord.get(i).getLocal();
					return i;
				}
			}

			i++;
			if (i == teste.NLINES) {
				i = 0;
			}
			k++;
		}
		
		int randN = rand.nextInt(values.size());
		return randN;
	}

}
