/**
 * The <code>CandidateProcessingSystem</code> class interacts
 * with the CandidateQueues and facilitates the processing of the
 * Candidates.
 *
 * @author Sean Reagan
 *      Email: sean.reagan@stonybrook.edu
 *      Stony Brook ID: 170024471
 *      Section: 02
 *      Instructor: Professor Esmaili
 *      TA: Felix Rieg-Baumhauer
 *      Recitation: 02 (Tuesdays 04:00pm - 04:53pm)
 *
 * @author Prangon Ghose
 *      Email: prangon.ghose@stonybrook.edu
 *      Stony Brook ID: 111623211
 *      Section: 02
 *      Instructor: Professor Esmaili
 *      TA: Jamie Kunzmann
 *      Recitation: 01 (Tuesdays 11:30am - 12:23pm)
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import com.opencsv.CSVWriter;

public class CandidateProcessingSystem {
	CandidateQueue candQueue; // queue of Candidates
	CandidateQueue acceptedCandidatesQueue; // queue of accepted Candidates
	CandidateQueue waitlistCandidatesQueue; // queue of wait listed Candidates
	private double GPAWeight; // weight of the gpa in the overall score
	private double SATWeight; // weight of the sat score in the overall score
	private double numAPWeight; // weight of the number of ap courses in the overall score
	private double moneyWeight; // weight of the money donated in the overall score
	private double statementWeight; // weight of the personal statement rating in the overall score
	private double legacyBonus; // weight of the legacy boolean in the overall score
	
	// using fractions of max to determine overall score
	// will multiply the fractions by the "weights" of each to determine overall score
	// note: moneyWeight will need to factor differently, since there is no cap to the amount of money one can donate.
	// note(2): legacy will also need to factor differently, since it is binary.
	// Will use an arbitrary multiplier for the overall weight to change admission likelihood.
	private final double GPA_MAX = 4.0; // max value of GPA
	private final double SAT_MAX = 1600; // max value of SAT
	private final double NUM_OF_APS_MAX = 10; // max number of aps, arbitrary value
	private final double STATEMENT_MAX = 5.0; // max value of personal statement
	private double acceptanceRate = 0.1; // acceptance rate of the university
	private int numCandidates = 0; // number of candidates

	private static final String WAITLIST_FILE = "waitlisted.csv";
	private static final String ACCEPTED_FILE = "accepted.csv";

    /**
     * Returns an instance of Candidate with template values.
     *
     * <dt><b>Postcondition:</b><db>
     *     This Candidate has been initialized with template values.
     */
	public CandidateProcessingSystem()	{
		candQueue = new CandidateQueue();
		acceptedCandidatesQueue = new CandidateQueue();
		waitlistCandidatesQueue = new CandidateQueue();

		GPAWeight = 0.2;
		SATWeight = 0.2;
		numAPWeight = 0.2;
		moneyWeight = 0.2;
		statementWeight = 0.2;
		legacyBonus = 1.15;
	}

    /**
     * Loads the Candidates from the given file into the system.
     *
     * @param filename
     * 		The name of the file with the candidates.
     *
     * <dt><b>Precondition:</b><db>
     *     This <code>CandidateProcessingSystem</code> has been initialized.
     *
     * @throws FileNotFoundException
     * 		Indicates that the given file could not be found.
     */
	public void loadCandidates(String filename) throws FileNotFoundException {
		try (Scanner scanIn = new Scanner(new File(filename));)
		{
			int index = 1;
		    while (scanIn.hasNextLine()) {
		    	String candidateInfo = scanIn.nextLine();
		    	Scanner rowScanner = new Scanner(candidateInfo);
		    
		    	rowScanner.useDelimiter(",");

		    	String name = rowScanner.next();
		    	int subOrder = index;
		    	int SAT = Integer.parseInt(rowScanner.next());
		    	double GPA = Double.parseDouble(rowScanner.next());
		    	int numAP = Integer.parseInt(rowScanner.next());
				// cutting off the effectiveness of AP courses at 10 for simplification.
				// Plus, this makes sense on the principle of diminishing returns
		    	if(numAP > 10) numAP=10;
		    	int statementRating = Integer.parseInt(rowScanner.next());
		    	int moneyDonated = Integer.parseInt(rowScanner.next());
		    	boolean legacy = Boolean.parseBoolean(rowScanner.next());
		    	
		    	rowScanner.close();
		    	Candidate newCandidate = new Candidate(name, subOrder, SAT, GPA, numAP, statementRating, moneyDonated,
						legacy);
		    	System.out.println(name + " added to the system!");
				// weighting the criteria of this new candidate to determine a score (which determines priority)
		    	int candidateScore = createScore(newCandidate);
		    	newCandidate.setScore(candidateScore);

		    	int maxScore = (int) (legacyBonus*(100*GPAWeight + 100*SATWeight + 100*numAPWeight +
						100*statementWeight + 100*moneyWeight));
		    	
		    	if(candidateScore >= 0.9*maxScore){
		    		newCandidate.setPriority(1);
		    	}
		    	else if (candidateScore >= 0.8*maxScore){
		    		newCandidate.setPriority(2);
		    	}
		    	else if(candidateScore >= 0.6*maxScore){
		    		newCandidate.setPriority(3);
		    	}
		    	else if(candidateScore >= 0.4*maxScore){
		    		newCandidate.setPriority(4);
		    	}
		    	else{
		    		newCandidate.setPriority(5);
		    	}
		    	index++;
		    	candQueue.enqueue(newCandidate);
		    	
		    	numCandidates++; // increment the number of candidates in the pool of applicants
		    }
		    scanIn.close(); // close the file scanner
		}
	}

    /**
     * Returns the overall score of the given Candidate.
     *
     * @param newCandidate
     * 		A Candidate to be scored
     *
     * <dt><b>Precondition:</b><db>
     *     This <code>CandidateProcessingSystem</code> has been initialized.
     *
     * @return
     * 		Returns the overall score of the given Candidate.
     */
	public int createScore(Candidate newCandidate) {
		int score = 0;
		double GPAFraction = GPAWeight*newCandidate.getGPA() / GPA_MAX * 100;
		double SATFraction = SATWeight*newCandidate.getSAT() / SAT_MAX * 100;
		double numAPFraction = numAPWeight*newCandidate.getNumAP() / NUM_OF_APS_MAX * 100;
		double statementFraction = statementWeight*newCandidate.getStatementRating() / STATEMENT_MAX * 100;

		score += (GPAFraction+SATFraction+numAPFraction+statementFraction);

		if(newCandidate.isLegacy()) {
			score *= legacyBonus; // Large boost if a legacy candidate
		}
		
		return score;
	}

    /**
     * Prints all the Candidates in the given queue in a formatted form.
     *
     * @param queue
     *      The CandidateQueue to be printed.
     *
     * <dt><b>Precondition:</b><db>
     *     This <code>CandidateProcessingSystem</code> has been initialized.
     */
	public void printCandidates(String queue) {
		if (queue.equals("candidates")) {
            System.out.println("Candidates: \n" +
                    "Name                 Submission Order     GPA  SAT Score # of APs    PSR  " +
                    "     Money Donated          Legacy");
            System.out.println("---------------------------------------------------------------------------------------" +
                    "---------------------------------");
            System.out.println(candQueue.toString());
        }
		else if (queue.equals("accepted")) {
            System.out.println("Accepted: \n" +
                    "Name                 Submission Order     GPA  SAT Score # of APs    PSR  " +
                    "     Money Donated          Legacy");
            System.out.println("---------------------------------------------------------------------------------------" +
                    "---------------------------------");
            System.out.println(acceptedCandidatesQueue.toString());
        }
		else if(queue.equals("waitlist")) {
            System.out.println("Waitlisted: \n" +
                    "Name                 Submission Order     GPA  SAT Score # of APs    PSR  " +
                    "     Money Donated          Legacy");
            System.out.println("---------------------------------------------------------------------------------------" +
                    "---------------------------------");
            System.out.println(waitlistCandidatesQueue.toString());
        }
	}

    /**
     * Determines and writes the accepted Candidates to a CSV file.
     *
     * <dt><b>Precondition:</b><db>
     *     This <code>CandidateProcessingSystem</code> has been initialized.
     */
	public void writeAcceptedCandidates() // this is for when we're INITIALLY accepting
    // candidates
	{							// We will use a different method when writing the updated accepted candidates list
	    // that includes waitlisted candidates who were later accepted
		int candidatesToAccept = (int) (numCandidates*acceptanceRate);
		int acceptedCandidates = 0;
		int index;
		CSVWriter writer = null;
		try {
			File file = new File(ACCEPTED_FILE);
			FileWriter outputfile = new FileWriter(file);
			writer = new CSVWriter(outputfile);
			
			if(this.acceptedCandidatesQueue.isEmpty())
			{
				while (acceptedCandidates < candidatesToAccept) 
				{
					Candidate pointer;
					acceptedCandidates++;
					pointer = (Candidate) candQueue.remove();
					String[] candidateData = {pointer.getName(), Integer.toString(pointer.getPriority()),
							Integer.toString(pointer.getSAT()), Double.toString(pointer.getGPA()),
							Integer.toString(pointer.getNumAP()), Integer.toString(pointer.getStatementRating()),
							Integer.toString(pointer.getMoneyDonated()),
							Boolean.toString(pointer.isLegacy())};
					System.out.println(pointer.getName() + " added to accepted list!");
					writer.writeNext(candidateData);
					acceptedCandidatesQueue.enqueue(pointer);
				}	
			}	
		}
		catch (IOException ex) {
			System.out.println("Error in file I/O " + ACCEPTED_FILE);
		}

        try {
            writer.close();
        }
        catch (IOException e) {
            System.out.println("Error in closing the CSVWriter.");
        }
	}

    /**
     * Transfer the given number of students from the wait list to the accepted list
     *
     * @param numOfCandsToMove
     *      Number of candidates to move from wait list to accepted list
     */
	public void moveFromWaitListToAccepted(int numOfCandsToMove) // pass in a certain number of candidates
    // to accept from the waitlist
	{
		for(int i = 0; i < numOfCandsToMove; i++)
		{
			Candidate removedFromWait = (Candidate) waitlistCandidatesQueue.poll(); // remove the top
            // wait list candidate from the top of the heap
			if(removedFromWait !=null)
			acceptedCandidatesQueue.enqueue(removedFromWait);
			else return;
		}
	}

    /**
     * Writes the wait list candidates to a CSV file.
     *
     * <dt><b>Precondition:</b><db>
     *     This <code>CandidateProcessingSystem</code> has been initialized.
     */
	public void writeUpdatedWaitList()
	{
		CSVWriter writer = null;
		
		try {
			File file = new File(WAITLIST_FILE);
			FileWriter outputfile = new FileWriter(file);
			writer = new CSVWriter(outputfile);
			int staticSize = waitlistCandidatesQueue.size();
			CandidateQueue tempQueue = new CandidateQueue();
			for(int i = 0; i < staticSize; i++)
			{
				Candidate pointer = (Candidate) waitlistCandidatesQueue.remove();
				String[] candidateData = {pointer.getName(), Integer.toString(pointer.getPriority()),
						Integer.toString(pointer.getSAT()), Double.toString(pointer.getGPA()),
						Integer.toString(pointer.getNumAP()), Integer.toString(pointer.getStatementRating()),
						Integer.toString(pointer.getMoneyDonated()),
						Boolean.toString(pointer.isLegacy())};
				tempQueue.enqueue(pointer);
				writer.writeNext(candidateData);
			}
			writer.close();
			waitlistCandidatesQueue = tempQueue;
		}
		catch(Exception e)
		{
			System.out.println("Error in file IO (when updating the waitlist file).");
		}
		
	}

    /**
     * Writes the accepted list candidates to a CSV file.
     *
     * <dt><b>Precondition:</b><db>
     *     This <code>CandidateProcessingSystem</code> has been initialized.
     */
	public void writeUpdatedAcceptedList()
	{
		CSVWriter writer = null;
		
		try {
			File file = new File(ACCEPTED_FILE);
			FileWriter outputfile = new FileWriter(file);
			writer = new CSVWriter(outputfile);
			int staticSize = acceptedCandidatesQueue.size();
			CandidateQueue tempQueue = new CandidateQueue();
			for(int i = 0; i < staticSize; i++)
			{
				Candidate pointer = (Candidate) acceptedCandidatesQueue.remove();
				String[] candidateData = {pointer.getName(), Integer.toString(pointer.getPriority()),
						Integer.toString(pointer.getSAT()), Double.toString(pointer.getGPA()),
						Integer.toString(pointer.getNumAP()), Integer.toString(pointer.getStatementRating()),
						Integer.toString(pointer.getMoneyDonated()),
						Boolean.toString(pointer.isLegacy())};
				tempQueue.enqueue(pointer);
				writer.writeNext(candidateData);
			}
			writer.close();
			acceptedCandidatesQueue = tempQueue;
		}
		catch(Exception e)
		{
			System.out.println("Error in file IO (when updating the accepted candidates file).");
		}
	}

    /**
     * Determines and writes the wait list candidates to a CSV file.
     *
     * <dt><b>Precondition:</b><db>
     *     This <code>CandidateProcessingSystem</code> has been initialized.
     */
	public void writeWaitListCandidates() {
		// set number of waitlist students equal to number accepted.
		int candidatesToWait = (int) (numCandidates*acceptanceRate);
		// coefficient of 2 to signify the sum of accepted & waitlisted applicants.
		if((2*candidatesToWait) >= numCandidates) {
			// This term will be larger than # of applicants if the acceptance rate > 50%
			CSVWriter writer = null;
			try {
				File file = new File(WAITLIST_FILE);
				FileWriter outputfile = new FileWriter(file);
				writer = new CSVWriter(outputfile);
			}
			catch (Exception e) {
				System.out.println("Error in accessing waitlist file.");
			}

			while(!candQueue.isEmpty())
			{
					Candidate cand = (Candidate) candQueue.remove();
					waitlistCandidatesQueue.add(cand);
					System.out.println(cand.getName() + " added to wait list!"); // find the index of priority 1 item,
					// then print it
					
					String[] candidateData = {cand.getName(), Integer.toString(cand.getPriority()),
							 Integer.toString(cand.getSAT()), Double.toString(cand.getGPA()),
							Integer.toString(cand.getNumAP()), Integer.toString(cand.getMoneyDonated()),
							Boolean.toString(cand.isLegacy())};
					writer.writeNext(candidateData);
			}
			try {
                writer.close();
            }
            catch (IOException e) {
                System.out.println("Error in closing the CSVWriter.");
            }
	}
		
	else {
		CSVWriter writer = null;
		try {
			File file = new File(WAITLIST_FILE);
			FileWriter outputfile = new FileWriter(file);
			writer = new CSVWriter(outputfile);
		}
		catch (Exception e) {
			System.out.println("Error in accessing waitlist file.");
		}
		for(int i = 0; i < candidatesToWait; i++) {
			Candidate cand = (Candidate) candQueue.remove();
			waitlistCandidatesQueue.enqueue(cand);
			System.out.println(cand.getName() + " added to wait list!111"); // find the index of priority 1 item,
			// then print it
		

			String[] candidateData = {cand.getName(), Integer.toString(cand.getPriority()),
					 Integer.toString(cand.getSAT()), Double.toString(cand.getGPA()),
					Integer.toString(cand.getNumAP()), Integer.toString(cand.getMoneyDonated()),
					Boolean.toString(cand.isLegacy())};
			writer.writeNext(candidateData);
		}

            try {
                writer.close();
            }
            catch (IOException e) {
                System.out.println("Error in closing the CSVWriter.");
            }

		}
	}

    /**
     * Sets the weight of the GPA in the overall score for each Candidate.
     *
     * @param GPAWeight
     *      The weight of the GPA in the overall score
     *
     * <dt><b>Precondition:</b><db>
     *     This <code>CandidateProcessingSystem</code> has been initialized.
     */
	public void setGPAWeight(double GPAWeight)
	{
		this.GPAWeight = GPAWeight;
	}

    /**
     * Sets the weight of the SAT score in the overall score for each Candidate.
     *
     * @param SATWeight
     *      The weight of the SAT score in the overall score
     *
     * <dt><b>Precondition:</b><db>
     *     This <code>CandidateProcessingSystem</code> has been initialized.
     */
	public void setSATWeight(double SATWeight)
	{
		this.SATWeight = SATWeight;
	}

    /**
     * Sets the weight of the number of the APs in the overall score for each Candidate.
     *
     * @param numAPWeight
     *      The weight of the number of APs in the overall score
     *
     * <dt><b>Precondition:</b><db>
     *     This <code>CandidateProcessingSystem</code> has been initialized.
     */
	public void setNumAPWeight(double numAPWeight)
	{
		this.numAPWeight = numAPWeight;
	}

    /**
     * Sets the weight of the personal statement rating in the overall score for each Candidate.
     *
     * @param statementWeight
     *      The weight of the personal statement rating in the overall score
     *
     * <dt><b>Precondition:</b><db>
     *     This <code>CandidateProcessingSystem</code> has been initialized.
     */
	public void setStatementWeight(double statementWeight)
	{
		this.statementWeight = statementWeight;
	}

    /**
     * Sets the weight of the amount of money donated in the overall score for each Candidate.
     *
     * @param moneyWeight
     *      The weight of the amount of money donated in the overall score
     *
     * <dt><b>Precondition:</b><db>
     *     This <code>CandidateProcessingSystem</code> has been initialized.
     */
	public void setMoneyWeight(double moneyWeight) {
		this.moneyWeight = moneyWeight;
	}

    /**
     * Sets the bonus of being legacy in the overall score for each Candidate.
     *
     * @param legacyBonus
     *      The bonus of being legacy in the overall score
     *
     * <dt><b>Precondition:</b><db>
     *     This <code>CandidateProcessingSystem</code> has been initialized.
     */
	public void setLegacyBonus(double legacyBonus) {
		this.legacyBonus = legacyBonus;
	}

    /**
     * Sets the acceptance rate of the University
     *
     * @param acceptanceRate
     *      The percentage of students who are accepted to the University
     *
     * <dt><b>Precondition:</b><db>
     *     This <code>CandidateProcessingSystem</code> has been initialized.
     */
	public void setAcceptanceRate(double acceptanceRate) {
	    this.acceptanceRate = acceptanceRate;
    }
}
