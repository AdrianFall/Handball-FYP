package af.handball.generator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class RandomTeamNameGenerator {

	private int leagueLevel;
	private BufferedReader br;
	private ArrayList<Integer> generatedRandomNumbersList;

	public RandomTeamNameGenerator(int leagueLevel) {
		this.leagueLevel = leagueLevel;
		// Obtain the text file with the team names

		try {
		
			br = new BufferedReader(new FileReader("C://Users//Adrian//Desktop//handball-random-teams.txt"));
			System.out.println("Obtained the team names file txt");
			
		} catch (FileNotFoundException e) {
			System.out.println("Couldn't obtain the file with team names.");
			e.printStackTrace();
		}
		
	}
	
	public String[] generateTeams() {
		String[] generatedTeamNames = new String[12]; 
		if (br != null) {
			String allTeamNames;
			try {
				allTeamNames = br.readLine();
				String[] splitTeamNames = allTeamNames.split(",");
				int amountOfTeams = splitTeamNames.length;
				System.out.println("Amount of teams = " + amountOfTeams);
				
				// Array to keep the generated team names
				
				
				// Array list to keep track of generated random numbers
				generatedRandomNumbersList = new ArrayList<Integer>();
				
				Random randomGenerator = new Random();
				for (int i = 0; i < 12; i++) {
					int randomInt = randomGenerator.nextInt(amountOfTeams);
					
					// Call a method to check whether the randomInt isn't already allocated in the generatedRandomNumbersList (i.e. to avoid repetition of team name)
					boolean teamNameAlreadyExists = checkTeamNameRepetition(randomInt);
					
					while (teamNameAlreadyExists) {
						// Re-roll random number
						System.out.println("Re-rolling random number");
						randomInt = randomGenerator.nextInt(amountOfTeams);
						teamNameAlreadyExists = checkTeamNameRepetition(randomInt);
					}
					generatedTeamNames[i] = splitTeamNames[randomInt];
					generatedRandomNumbersList.add(randomInt);
					//generatedRandomNumbersList.add(randomInt);
					
				}
				
				System.out.println("Generated team names = " + Arrays.toString(generatedTeamNames));
				
			} catch (IOException e) {
				System.out.println("Couldn't read line from the BufferedReader");
				e.printStackTrace();
			}
			
		}
		
		return generatedTeamNames;
	}

	private boolean checkTeamNameRepetition(int randomInt) {
		boolean teamNameAlreadyExists = false;

		if (generatedRandomNumbersList.contains(randomInt)) {
			teamNameAlreadyExists = true;
			System.out.println("generatedRandomNumbersList already contains the random number of " + randomInt);
		}
		
		return teamNameAlreadyExists;
	}

}
