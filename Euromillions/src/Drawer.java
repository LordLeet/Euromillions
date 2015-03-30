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


	public String retreiveResults() throws IOException {

		EuromillionsDocument = Jsoup.connect("http://www.euromilhoes.com/").get();

		numbers = EuromillionsDocument.select("#results-content li:not(.extra-numbers li)").text();
		outputNumbers = numbers.replace(" ", ",");

		stars = EuromillionsDocument.select(".extra-numbers li").text();
		outputStars = stars.replace(" ", ",");

		String date = EuromillionsDocument.select("#results-content h2").text();

		if (language.equals("UK")){
			
			String dateOutput = null;
			
			if (date.contains("Sexta-feira"))
				dateOutput = date.replace("Sexta-feira", "Friday");

			if (date.contains("Terça-feira"))
				dateOutput = date.replace("Terça-feira", "Tuesday");
			
			finalOutput = "<html><center> " + dateOutput + "<br> Numbers: [" + outputNumbers + "] | Stars: [" + outputStars + "]; </center></html>";
		}
		
		else
			finalOutput = "<html><center> " + date + "<br> Números: [" + outputNumbers + "] | Estrelas: [" + outputStars + "]; </center></html>";
	
		return finalOutput;

	}

	public void checkNumbers() {

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

	public void checkStars() {

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

	public void clear() {

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

	public void addResult() {

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
