package com.dse.hospital.consultation;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import com.dse.hospital.patient.*;


public class ConsultQueue {
	private PatientNode[] patient_queue;
	private int count;
	private int total_patient;
	private static ConsultQueue single_queue = null;
	private static final int FRONT = 1;

	/**
	 * Constructor
	 * @param total_patient
	 */
	private ConsultQueue(int total_patient) {
		this.count = 0;
		this.total_patient = total_patient;
		patient_queue = new PatientNode[this.total_patient + 1];
		for (int i = 0; i <= total_patient; i++) {
			patient_queue[i] = PatientRecord.NOT_FOUND;
		}
		//patient_queue[0] = new PatientNode("", Integer.MAX_VALUE);
	}

	public static ConsultQueue getQueueInstance() {
		if (single_queue == null)
			single_queue = new ConsultQueue(100);
		return single_queue;
	}
	
	/**
	 * HELPER FUNCTIONS FOR HEAP.
	 */
	private int parent(int pos) {
		return (pos) / 2;
	}

	private int leftChild(int pos) {
		return (2 * pos);
	}

	private int rightChild(int pos) {
		return (2 * pos) + 1;
	}

	private boolean isLeaf(int pos) {
		if (pos > (count / 2) && pos <= count) {
			return true;
		}
		return false;
	}

	private void swap(int fpos, int spos) {
		PatientNode tmp;
		tmp = patient_queue[fpos];
		patient_queue[fpos] = patient_queue[spos];
		patient_queue[spos] = tmp;
	}

	private void maxHeapify(int pos) {
		if (!isLeaf(pos)) {
			if (patient_queue[pos].getAge() < patient_queue[leftChild(pos)].getAge()
					|| patient_queue[pos].getAge() < patient_queue[rightChild(pos)].getAge()) {
				if (patient_queue[leftChild(pos)].getAge() > patient_queue[rightChild(pos)].getAge()) {
					swap(pos, leftChild(pos));
					maxHeapify(leftChild(pos));
				} else {
					swap(pos, rightChild(pos));
					maxHeapify(rightChild(pos));
				}
			}
		}
	}

	/**
	 * Enqueue operation : insert in to proper location of heap.
	 * 1. insert as last element.
	 * 2. if current older than parent, swap with parent.
	 * 3. repeat step 2 , as long as older than parent, or reached root.
	 * 
	 * @param patient
	 */
	public void enqueuePatient(PatientNode patient) {
		if (count == total_patient) {
			System.out.println("Queue full");
		} else {
			patient_queue[++count] = patient;
			int current = count;
			while (patient_queue[current].getAge() > patient_queue[parent(current)].getAge() 
					&& patient_queue[parent(current)].getAge() != -1) {
				swap(current, parent(current));
				current = parent(current);
			}
		}
	}

	/**
	 * Dequeue operation : remove top element from heap.
	 * 1. replace root with last element.
	 * 2. re-heapify the array.
	 */
	public void dequeuePatient() {
		if (count == 0) {
			System.out.println("No patient to Dequeue");
		} else {
			patient_queue[FRONT] = patient_queue[count--];
			maxHeapify(FRONT);
		}
	}

	/**
	 * Display the root element.
	 */
	public void nextPatient() {
		if (count != 0)
			System.out.println(
					"Next patient is ID:" + patient_queue[FRONT].getId()
							+ " Name:" + patient_queue[FRONT].getName()
							+ " Age:" + patient_queue[FRONT].getAge() + "\n");
		else {
			System.out.println("\nNo next Patient \n");
		}
	}

	/**
	 * Display queue:
	 * Since we want the display in order of visiting doctor, 
	 * we are dequeue-ing everyone, and display in the order of dequeu-ing,
	 * and enqueue again. We are using a temporary array for this.
	 * @param dll
	 */
	public void displayQueue(PatientRecord dll) {
		String output_file = "src/main/resource/Output.txt";
		File file = new File(output_file);
		PatientNode[] display_list = new PatientNode[total_patient];
		int size = count;
		try {
			System.out.println("List written to " + output_file);
			PrintStream o = new PrintStream(file);
			PrintStream console = System.out;
			System.setOut(o);

			if (count == 0) {
				System.out.println("\nNo Patient in the queue \n");
				System.setOut(console);
			} else {
				System.out.println("\n Displaying the queue");
				System.out.println("------------------------");

				while (count > 0) {
					display_list[count] = patient_queue[FRONT];
					ConsultQueue.getQueueInstance().dequeuePatient();
				}
				for (int i = size; i > 0; i--) {
					System.out.println(size + 1 - i + " , " + display_list[i].getId() + " , "
							+ display_list[i].getName() + " , "
							+ display_list[i].getAge());
					enqueuePatient(display_list[i]);
				}
				System.setOut(console);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
