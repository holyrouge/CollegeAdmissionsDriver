/**
 * The <code>AdmissionsDriver</code> class serves as
 * the primary driver for the <code>CandidateProcessingSystem</code>
 * using a menu-driven main method.
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
import java.util.Scanner;

public class AdmissionsDriver {
	private static final String WAITLIST_FILE = "waitlisted.csv";
	private static final String ACCEPTED_FILE = "accepted.csv";

	/**
	 * This method allows the user to interact with the CandidateProcessingSystem using a menu-driven program.
	 *
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		char input;
		CandidateProcessingSystem processor = new CandidateProcessingSystem();

		System.out.println("Welcome to the Candidate Processing System of Legacy University! Let's begin!");
		while(true)	{
			System.out.println(
					"	(A) Print Candidate Pool\n" +
					"    (B) Print Accepted Candidates\n" +
					"    (C) Print Wait Listed Candidates\n" +
					"    (D) Erase All Data\n" +
					"    (E) Load Candidates\n" +
					"    (F) Process Candidates\n" +
					"    (G) Set Weights/Bonuses for Criteria\n" +
					"    (H) Set Acceptance Rate\n" +
					"    (I) Transfer from Wait List to Accepted\n" +
					"    (Q) Quit");

			System.out.print("\nSelect an input: ");
			input = scanner.next().toUpperCase().charAt(0);

			switch(input) {
				case 'A':
					processor.printCandidates("candidates");
					break;
				case 'B':
					processor.printCandidates("accepted");
					break;
				case 'C':
					processor.printCandidates("waitlist");
					break;
				case 'D':
					processor = new CandidateProcessingSystem();
					System.out.println("All data in the system deleted.");
					break;
				case 'E':
					try {
						System.out.print("Enter file name to load from: ");
						String filename = scanner.next();
						processor.loadCandidates(filename);
						System.out.println("\nCandidates loaded successfully from " + filename + "!");
					}
					catch (Exception ex) {
						System.out.println("Error in loading candidates. Please try again!");
					}
					break;
				case 'F':
					try {
						System.out.println("Processing acceptances...");
						processor.writeAcceptedCandidates();
						System.out.println("\nAdded all accepted candidates to " + ACCEPTED_FILE);

						System.out.println("\nProcessing wait list acceptances...");
						processor.writeWaitListCandidates();
						System.out.println("\nAdded all wait list candidates to " + WAITLIST_FILE);
					}
					catch (Exception e) {
						//System.out.println("Error in processing the candidates. Please try again!");
					}
					break;
				case 'G':
					System.out.println("\nNote about setting weights: default weight for each variable is 1/5.0. " +
							"All weights should be between 0.0 and 1.0. All weights should add up to 1.0.\n");
					System.out.print("Enter the GPA weight:");
					double GPAWeight = scanner.nextDouble();
					processor.setGPAWeight(GPAWeight);

					System.out.print("Enter SAT weight: ");
					double SATWeight = scanner.nextDouble();
					processor.setSATWeight(SATWeight);

					System.out.print("Enter the weight of the number of AP courses taken: ");
					double numAPWeight = scanner.nextDouble();
					processor.setNumAPWeight(numAPWeight);

					System.out.print("Enter the weight of the amount of money donated: ");
					double moneyDonatedWeight = scanner.nextDouble();
					processor.setMoneyWeight(moneyDonatedWeight);

					System.out.print("Enter personal statement Weight: ");
					double statementWeight = scanner.nextDouble();
					processor.setStatementWeight(statementWeight);

					System.out.print("Enter the weight of being a legacy: ");
					double legacyBonus = scanner.nextDouble();
					processor.setLegacyBonus(legacyBonus);

					System.out.println("All weights and bonuses successfully set!");
					break;
				case 'H':
					System.out.print("Enter the Acceptance Rate (0 to 1):");
					double acceptanceRate = scanner.nextDouble();
					processor.setAcceptanceRate(acceptanceRate);
					System.out.println("Acceptance Rate successfully set!");
					break;
				case 'I':
					System.out.print("Enter the number of applicants to transfer from the waitlist to the" +
							" accepted: ");
					int numToTransfer = scanner.nextInt();
					processor.moveFromWaitListToAccepted(numToTransfer);
					processor.writeUpdatedAcceptedList();
					processor.writeUpdatedWaitList();
					System.out.println("First on wait list: " +
							((Candidate) processor.waitlistCandidatesQueue.peek()).getName());
					break;
				case 'Q':
					System.out.println("Terminating program...");
					System.exit(0);
				default:
					System.out.println("\nInvalid input. Please try again!");
					break;
			}				
		}
	}
}
