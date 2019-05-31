/**
 * The <code>CandidateQueue</code> class represents a list
 * of Candidates who have applied to the University based on
 * their time of application.
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
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class CandidateQueue extends PriorityQueue {

    /**
     * Returns an instance of Candidate
     *
     * <dt><b>Postcondition:</b><db>
     *     This Candidate has been initialized.
     */
	public CandidateQueue()
	{

	}

    /**
     * Adds the given Candidate to the queue.
     *
     * @param candidate
     * 		A Candidate that is to be added to the queue.
     *
     * <dt><b>Precondition:</b><db>
     *     This <code>CandidateQueue</code> has been initialized.
     */
	public void enqueue(Candidate candidate)
	{
		this.add(candidate);
	}

    /**
     * Returns and the Candidate at the front of the queue.
     *
     * <dt><b>Precondition:</b><db>
     *     This <code>CandidateQueue</code> has been initialized.
     *
     * @return
     * 		Returns the Candidate at the front of the queue.
     */
	public Candidate dequeue()
	{
		return (Candidate) this.remove();
	}

    /**
     * Returns the information of each Candidate in the queue.
     *
     * <dt><b>Precondition:</b><db>
     *     This <code>CandidateQueue</code> has been initialized.
     *
     * @return
     * 		Returns a String with the information of each Candidate in the queue.
     */
	public String toString() {
		Queue<Candidate> tempQueue = new LinkedList<>();
		Candidate candidate;
		String returnString = "";
		int size = size();
		for (int i = 0; i < size; i++) {
			candidate = (Candidate) this.poll();
			tempQueue.add(candidate);
			returnString += candidate.toString();
		}
		for(int i = 0; i < size; i++)
		{
			this.enqueue(tempQueue.poll());
		}
		
		return returnString;
	}
}
