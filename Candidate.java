/**
 * The <code>Candidate</code> class represents a candidate
 * to a university with data about the candidate.
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

public class Candidate implements Comparable<Candidate>{
	private String name; // name of candidate
	private int subOrder; // i.e. this is the #th student to apply. Those who apply earlier get more priority
	private int sat; // SAT score of candidate
	private double gpa; // student high school GPA
	private int numAP; // number of Advanced Placement courses taken
	private int statementRating; // rating the student's personal statement on a scale of 1-5
	private int moneyDonated; // amount of money donated to the university by this student/student's parents
	private boolean legacy; // whether or not the student has a legacy status (parent/relative attended this university)

	private int score; // cumulative score based on the above factors
	private int priority; // priority (1-5, higher is better odds of acceptance) is calculated based on score cutoffs


	/**
	 * Returns an instance of Candidate with the given values.
	 *
	 * @param name
	 * 		The name of the Candidate
	 * @param subOrder
	 * 		The order in which the Candidate submitted their application
	 * @param sat
	 * 		The SAT score of the Candidate
	 * @param gpa
	 * 		The GPA of the Candidate
	 * @param numAP
	 * 		The number of AP exams taken by the Candidate
	 * @param statementRating
	 * 		The score given to the Candidate's personal statement
	 * @param moneyDonated
	 * 		The amount of money donated by the Candidate's family
	 * @param legacy
	 * 		The boolean value of whether the Candidate is a legacy or not
	 *
	 * <dt><b>Postcondition:</b><db>
	 *     This Candidate has been initialized with given values.
	 */
	public Candidate(String name, int subOrder, int sat, double gpa, int numAP, int statementRating,
					 int moneyDonated, boolean legacy)
	{
		this.name = name;
		this.subOrder = subOrder;
		this.sat = sat;
		this.gpa = gpa;
		this.numAP = numAP;
		this.statementRating = statementRating;
		this.moneyDonated = moneyDonated;
		this.legacy = legacy;
	}


	/**
	 * Returns the name of the Candidate
	 *
	 * <dt><b>Precondition:</b><db>
	 *     This <code>Candidate</code> has been initialized.
	 *
	 * @return
	 *      Returns the String value of the name of the Candidate.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the Candidate
	 *
	 * @param name
	 *      The name of the Candidate
	 *
	 * <dt><b>Precondition:</b><db>
	 *     This <code>Candidate</code> has been initialized.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the order in which the Candidate submitted their application
	 *
	 * <dt><b>Precondition:</b><db>
	 *     This <code>Candidate</code> has been initialized.
	 *
	 * @return
	 *      Returns the int value of the order in which the Candidate submitted their application.
	 */
	public int getSubOrder() {
		return subOrder;
	}

	/**
	 * Sets the order in which the Candidate submitted their application
	 *
	 * @param subOrder
	 *      The order in which the Candidate submitted their application
	 *
	 * <dt><b>Precondition:</b><db>
	 *     This <code>Candidate</code> has been initialized.
	 */
	public void setSubOrder(int subOrder) {
		this.subOrder = subOrder;
	}

	/**
	 * Returns the overall score of the Candidate
	 *
	 * <dt><b>Precondition:</b><db>
	 *     This <code>Candidate</code> has been initialized.
	 *
	 * @return
	 *      Returns the overall score of the Candidate.
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Sets the overall score of the Candidate
	 *
	 * @param score
	 *      The overall score of the Candidate
	 *
	 * <dt><b>Precondition:</b><db>
	 *     This <code>Candidate</code> has been initialized.
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * Returns the GPA of the Candidate
	 *
	 * <dt><b>Precondition:</b><db>
	 *     This <code>Candidate</code> has been initialized.
	 *
	 * @return
	 *      Returns the GPA of the Candidate
	 */
	public double getGPA() {
		return gpa;
	}

	/**
	 * Sets the GPA of the Candidate
	 *
	 * @param gpa
	 *      The GPA of the Candidate
	 *
	 * <dt><b>Precondition:</b><db>
	 *     This <code>Candidate</code> has been initialized.
	 */
	public void setGPA(double gpa) {
		this.gpa = gpa;
	}

	/**
	 * Returns the Personal Statement Rating of the Candidate
	 *
	 * <dt><b>Precondition:</b><db>
	 *     This <code>Candidate</code> has been initialized.
	 *
	 * @return
	 *      Returns the Personal Statement Rating of the Candidate.
	 */
	public int getStatementRating() {
		return statementRating;
	}

	/**
	 * Sets the Personal Statement Rating of the Candidate
	 *
	 * @param statementRating
	 *      The Personal Statement Rating of the Candidate
	 *
	 * <dt><b>Precondition:</b><db>
	 *     This <code>Candidate</code> has been initialized.
	 */
	public void setStatementRating(int statementRating) {
		this.statementRating = statementRating;
	}

	/**
	 * Returns the number of the AP exams that the Candidate has taken.
	 *
	 * <dt><b>Precondition:</b><db>
	 *     This <code>Candidate</code> has been initialized.
	 *
	 * @return
	 *      Returns the number of AP exams that the Candidate has taken.
	 */
	public int getNumAP() {
		return numAP;
	}

	/**
	 * Sets the number of AP exams taken by the Candidate
	 *
	 * @param numAP
	 *      The number of AP exams taken by the Candidate
	 *
	 * <dt><b>Precondition:</b><db>
	 *     This <code>Candidate</code> has been initialized.
	 */
	public void setNumAP(int numAP) {
		this.numAP = numAP;
	}

	/**
	 * Returns the amount of money donated by the Candidate
	 *
	 * <dt><b>Precondition:</b><db>
	 *     This <code>Candidate</code> has been initialized.
	 *
	 * @return
	 *      Returns the amount of money donated by the Candidate.
	 */
	public int getMoneyDonated() {
		return moneyDonated;
	}

	/**
	 * Sets the amount of money donated by the Candidate
	 *
	 * @param moneyDonated
	 *      The amount of money donated by the Candidate
	 *
	 * <dt><b>Precondition:</b><db>
	 *     This <code>Candidate</code> has been initialized.
	 */
	public void setMoneyDonated(int moneyDonated) {
		this.moneyDonated = moneyDonated;
	}

	/**
	 * Returns if the Candidate is a legacy
	 *
	 * <dt><b>Precondition:</b><db>
	 *     This <code>Candidate</code> has been initialized.
	 *
	 * @return
	 *      Returns true if the Candidate is legacy, false otherwise.
	 */
	public boolean isLegacy() {
		return legacy;
	}

	/**
	 * Sets if the Candidate is a legacy
	 *
	 * @param legacy
	 *      If they Candidate is a legacy or not
	 *
	 * <dt><b>Precondition:</b><db>
	 *     This <code>Candidate</code> has been initialized.
	 */
	public void setLegacy(boolean legacy) {
		this.legacy = legacy;
	}

	/**
	 * Returns the SAT score of the Candidate
	 *
	 * <dt><b>Precondition:</b><db>
	 *     This <code>Candidate</code> has been initialized.
	 *
	 * @return
	 *      Returns the SAT score of the Candidate.
	 */
	public int getSAT() {
		return sat;
	}

	/**
	 * Sets the SAT score of the Candidate
	 *
	 * @param sat
	 *      The SAT score of the Candidate
	 *
	 * <dt><b>Precondition:</b><db>
	 *     This <code>Candidate</code> has been initialized.
	 */
	public void setSAT(int sat) {
		this.sat = sat;
	}

	/**
	 * Decides if the current Candidate greater/equal to the given Candidate or less than.
	 *
	 * @param o
	 * 		Another Candidate which is being compared to
	 * @return
	 * 		Returns 1 if the current Candidate's overall score is greater than or equal to
	 * 		the other Candidate's overall score; 0 otherwise.
	 */
	public int compareTo(Candidate o) {
		return (this.getScore() >= o.getScore() ? 1:0);
	}

	/**
	 * Returns the priority level of the Candidate
	 *
	 * <dt><b>Precondition:</b><db>
	 *     This <code>Candidate</code> has been initialized.
	 *
	 * @return
	 *      Returns the priority level of the Candidate
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * Sets the priority of the Candidate
	 *
	 * @param priority
	 *      The priority level of the Candidate
	 *
	 * <dt><b>Precondition:</b><db>
	 *     This <code>Candidate</code> has been initialized.
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}

	/**
	 * Returns the information in the Candidate.
	 *
	 * <dt><b>Precondition:</b><db>
	 *     This <code>Candidate</code> has been initialized.
	 *
	 * @return
	 *      Returns the information of the Candidate in a formatted string.
	 */
	public String toString() {
		return String.format("%-30s %-10d %.2f   %-10d %-10d %-10d %-20d %-10b \n",
				name, subOrder, gpa, sat, numAP, statementRating, moneyDonated, legacy);
	}
}
