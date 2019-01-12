package com.dse.hospital.consultation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import com.dse.hospital.patient.PatientRecord;

public class ConsultQueue {
	private int[] patient_queue;
	private int count;
	private int total_patient;
	private static ConsultQueue single_queue = null;
	private static final int FRONT = 1;

	/**
	 * @param total_patient
	 */
	private ConsultQueue(int total_patient) {
		this.count = 0;
		this.total_patient = total_patient;
		patient_queue = new int[this.total_patient];
		patient_queue[0] = Integer.MAX_VALUE;
	}

	public static ConsultQueue getQueueInstance() {
		if (single_queue == null)
			single_queue = new ConsultQueue(100);
		return single_queue;
	}

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
		int tmp;
		tmp = patient_queue[fpos];
		patient_queue[fpos] = patient_queue[spos];
		patient_queue[spos] = tmp;
	}

	private void maxHeapify(int pos) {
		PatientRecord dll = PatientRecord.getInstance();
		if (!isLeaf(pos)) {
			if (PatientRecord.getPatientById(dll.head, patient_queue[pos]).getAge() < PatientRecord
					.getPatientById(dll.head, patient_queue[leftChild(pos)]).getAge()
					|| PatientRecord.getPatientById(dll.head, patient_queue[pos]).getAge() < PatientRecord
							.getPatientById(dll.head, patient_queue[rightChild(pos)]).getAge()) {
				if (PatientRecord.getPatientById(dll.head, patient_queue[leftChild(pos)]).getAge() > PatientRecord
						.getPatientById(dll.head, patient_queue[rightChild(pos)]).getAge()) {
					swap(pos, leftChild(pos));
					maxHeapify(leftChild(pos));
				} else {
					swap(pos, rightChild(pos));
					maxHeapify(rightChild(pos));
				}
			}
		}
	}

	public void maxHeap() {
		for (int pos = (count / 2); pos >= 1; pos--) {
			maxHeapify(pos);
		}
	}

	/*
	 * Printing Parent, Left and Right node of max heap
	 */
	/*
	 * public void print(){ for (int i = 1; i <= count / 2; i++) {
	 * System.out.print(" PARENT:  " + patient_queue[i] + " LEFT CHILD: " +
	 * patient_queue[2 * i] + " RIGHT CHILD: " + patient_queue[2 * i + 1]); } }
	 */

	public void enqueuePatient(int patient_id) {
		PatientRecord dll = PatientRecord.getInstance();
		if (count == total_patient) {
			System.out.println("Queue full");
		} else {
			patient_queue[++count] = patient_id;
			int current = count;
			while (PatientRecord.getPatientById(dll.head, patient_queue[current]).getAge() > PatientRecord
					.getPatientById(dll.head, patient_queue[parent(current)]).getAge()
					&& PatientRecord.getPatientById(dll.head, patient_queue[parent(current)]).getAge() != -1) {
				swap(current, parent(current));
				current = parent(current);
			}
		}
	}

	public void dequeuePatient() {
		if (count == 0) {
			System.out.println("No patient to Dequeue");
		} else {
			patient_queue[FRONT] = patient_queue[count--];
			maxHeapify(FRONT);
		}
	}

	public void nextPatient(PatientRecord dll) {
		if (count != 0)
			System.out.println(
					"Next patient is ID:" + PatientRecord.getPatientById(dll.head, patient_queue[FRONT]).getId()
							+ " Name:" + PatientRecord.getPatientById(dll.head, patient_queue[FRONT]).getName()
							+ " Age:" + PatientRecord.getPatientById(dll.head, patient_queue[FRONT]).getAge() + "\n");
		else {
			System.out.println("\n No next Patient \n");
		}
	}

	public void displayQueue(PatientRecord dll) {
		File file = new File("src/main/resource/Output.txt");
		int[] display_list = new int[total_patient];
		int size = count;
		try {
			PrintStream o = new PrintStream(file);
			PrintStream console = System.out;
			System.setOut(o);

			if (count == 0) {
				System.out.println("\n No Patient in the queue \n");
				System.setOut(console);
			} else {
				System.out.println("\n Displaying the queue");
				System.out.println("------------------------");

				while (count > 0) {
					display_list[count] = PatientRecord.getPatientById(dll.head, patient_queue[FRONT]).getId();
					ConsultQueue.getQueueInstance().dequeuePatient();
				}
				for (int i = size; i > 0; i--) {
					System.out.println(size + 1 - i + " , " + display_list[i] + " , "
							+ PatientRecord.getPatientById(dll.head, display_list[i]).getName() + " , "
							+ PatientRecord.getPatientById(dll.head, display_list[i]).getAge());
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
