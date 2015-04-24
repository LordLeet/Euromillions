import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Drawer {

	public LinkedList<String> draws = new LinkedList<>();
	private LinkedList<Integer> stars_Draw = new LinkedList<>();
	private LinkedList<Integer> numbers_Draw = new LinkedList<>();

	private Random r = new Random();
	private int drawn;

	private Document EuromillionsDocument; 

	public String language;
	private String numbers;
	private String outputNumbers;
	private String stars;
	private String outputStars;	
	private String finalOutput;
	private LinkedList<Integer> correctNumbers = new LinkedList<Integer>();
	private LinkedList<Integer> correctStars = new LinkedList<Integer>();


	private void connect() throws IOException {

		EuromillionsDocument = Jsoup.connect("http://www.euromilhoes.com/").get();		
	}
	
	public void compareWithdraw(LinkedList<Integer> userNumbersDraw , LinkedList<Integer> userStarsDraw ) throws IOException {

		connect();

		String[] numbersString = outputNumbers.split(",");

		int[] numbersIntegers = new int[numbersString.length];

		for (int i = 0; i < numbersString.length; i++) {
			try {
				numbersIntegers[i] = Integer.parseInt(numbersString[i]);
			} catch (NumberFormatException nfe) {};
		}

		for (int i = 0; i < userNumbersDraw.size(); i++) {
			for (int j = 0; j < numbersIntegers.length; j++) {
				if (userNumbersDraw.get(i) == numbersIntegers[j]) {
					correctNumbers.add(i);
				}
			}
		}

		String[] starsString = outputStars.split(",");

		int[] starsIntegers = new int[starsString.length];

		for (int i = 0; i < starsString.length; i++) {
			try {
				starsIntegers[i] = Integer.parseInt(starsString[i]);
			} catch (NumberFormatException nfe) {};
		}

		for (int i = 0; i < userStarsDraw.size(); i++) {
			for (int j = 0; j < starsIntegers.length; j++) {
				if (userStarsDraw.get(i) == starsIntegers[j])
					correctStars.add(i);
			}
		}	
	}

	public LinkedList<Integer> getCorrectNumbers() {
		return correctNumbers;
	}
	
	public void setCorrectNumbers(LinkedList<Integer> correctNumbers) {
		this.correctNumbers = correctNumbers;
	}
	
	public LinkedList<Integer> getCorrectStars() {
		return correctStars;
	}
	
	public void setCorrectStars(LinkedList<Integer> correctStars) {
		this.correctStars = correctStars;
	}
	
	public String retreiveResults() throws IOException {

		connect();

		numbers = EuromillionsDocument.select("ul.numbers :not(li.star)").text();
		outputNumbers = numbers.replace(" ", ",");

		stars = EuromillionsDocument.select("li.star").text();
		outputStars = stars.replace(" ", ",");

		String date = EuromillionsDocument.select(".title").text();

		if (language.equals("UK")){

			String dateOutput = null;

			if (date.contains("Sexta")) {
				dateOutput = date.replace("Resultados EuroMilhões - Sexta", "Friday");
				dateOutput = dateOutput.replace("Ver a chave do EuroMilhoes", "");
			}
			
			if (date.contains("Terça")){
				dateOutput = date.replace("Resultados EuroMilhões - Terça", "Tuesday");
				dateOutput = dateOutput.replace("Ver a chave do EuroMilhoes", "");
			}
			
			finalOutput = "<html><center> " + dateOutput + "<br> Numbers: [" + outputNumbers + "] | Stars: [" + outputStars + "]; </center></html>";
		}

		else {
			date = date.replace("Resultados EuroMilhões - ", "");
			date = date.replace("Ver a chave do EuroMilhoes", "");
			finalOutput = "<html><center> " + date + "<br> Números: [" + outputNumbers + "] | Estrelas: [" + outputStars + "]; </center></html>";
		}
		
		return finalOutput;
	}

	public String getOutputNumbers() {
		return outputNumbers;
	}

	private void checkNumbers() {

		for (int i = 1; i < numbers_Draw.size(); i++) {

			int k = i;

			if (numbers_Draw.getFirst().equals(0)) {
				numbers_Draw.removeFirst();
				drawn = r.nextInt(51);
				numbers_Draw.addLast(drawn);	
			}

			for (int p = 0; p < numbers_Draw.size()-1; p++) {

				if (numbers_Draw.get(k).equals(numbers_Draw.get(p)) && ( i != p)) {

					numbers_Draw.remove(p);
					drawn = r.nextInt(51);
					numbers_Draw.addLast(drawn);
					i = 0;
				}	

				if (numbers_Draw.get(k).equals(0)) {

					numbers_Draw.remove(k);
					drawn = r.nextInt(51);
					numbers_Draw.add(drawn);
					i = 0;
				}
			}
		}
	}

	private void checkStars() {

		for (int q = 0; q < stars_Draw.size(); q++) {

			if (stars_Draw.getFirst().equals(0)) {

				stars_Draw.removeFirst();
				drawn = r.nextInt(12);
				stars_Draw.add(drawn);
				q--;
			}

			if (stars_Draw.getLast().equals(0)) {

				stars_Draw.removeLast();
				drawn = r.nextInt(12);
				stars_Draw.add(drawn);
				q--;
			}

			if (stars_Draw.getLast().equals(stars_Draw.getFirst())) {

				stars_Draw.removeLast();
				drawn = r.nextInt(12);
				stars_Draw.add(drawn);
				q--;
			}
		}
	}

	private void clear() {

		numbers_Draw.clear();
		stars_Draw.clear();
	}

	public void draw(int x) {

		int i = 0;

		while (i < x) {

			i++;

			for (int j = 0; j < 5; j++) {
				Random rN = new Random();
				int rn = rN.nextInt(51);
				numbers_Draw.add(rn);

			}

			for (int j = 0; j < 2; j++) {

				Random rS = new Random();
				int rs = rS.nextInt(12);
				stars_Draw.add(rs);
			}

			checkNumbers();
			Collections.sort(numbers_Draw);
			checkStars();
			Collections.sort(stars_Draw);
			addResult();
			clear();
		}
	}

	private void addResult() {

		draws.add(toString());
	}

	@Override
	public String toString() {

		if (language.equals("P"))
			return " Números: " + numbers_Draw + "  | Estrelas: " + stars_Draw
					+ "; \n";

		else
			return " Numbers: " + numbers_Draw + "  | Stars: " + stars_Draw
					+ "; \n";
	}
}
