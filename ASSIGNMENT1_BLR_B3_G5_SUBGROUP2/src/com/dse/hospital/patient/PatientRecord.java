package com.dse.hospital.patient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import com.dse.hospital.consultation.ConsultQueue;

public class PatientRecord {
	public static PatientNode NOT_FOUND = new PatientNode("Patient does not exists ....", -1);
	public PatientNode head;
	PatientNode tail;
	int length;
	private static PatientRecord single_dll = null;
	ConsultQueue patientqueue = ConsultQueue.getQueueInstance();

	/**
	 * @param headNode
	 * @param tailNode
	 * @param length
	 */
	private PatientRecord() {
		this.head = null;
		this.tail = null;
		this.length = 0;
	}

	public static PatientRecord getInstance() {
		if (single_dll == null)
			single_dll = new PatientRecord();
		return single_dll;
	}

	public boolean isEmpty() {
		return length == 0;
	}

	public int length() {
		return length;
	}

	public void loadFile(boolean firsttime) {

		String initial_content = new String();
		String name;
		int age;
		int id;
		File file = new File("src/main/resource/Input.txt");
		try {
			Scanner sc = new Scanner(new FileInputStream(file));
			System.out.println("Initial Content from the Input file ");
			while (sc.hasNextLine()) {
				initial_content = sc.nextLine();
				name = initial_content.split(",")[0];
				age = Integer.valueOf(initial_content.split(",")[1].trim());
				id = registerPatient(name, age);
				System.out.println(" ID : " + id + " Name: " + name + " Age: " + age);
			}
			sc.close();
		} catch (FileNotFoundException fnf) {
			fnf.printStackTrace();
		}

	}

	public int registerPatient(String name, int age) {
		PatientNode p_node = new PatientNode(name, age);
		if (isEmpty()) {
			head = p_node;
		} else {
			tail.next = p_node;
		}
		p_node.previous = tail;
		tail = p_node;
		length++;
		patientqueue.enqueuePatient(p_node.getId());
		return p_node.getId();
	}
	
	// Display the DoublyLL	
	/*public void patientRecordList(){ if (head==null){ return; } 
	PatientNode temp =head; while(temp!=null){
	 System.out.println("List::  "+temp.id+"  "+temp.name+"  "+temp.age);
	 temp=temp.next; } System.out.println("-------------------------"); }*/
	 
	
	
	// Get the node in the DLL given the patient_id	
		public static PatientNode getPatientById(PatientNode head, int patient_id) {
			if (head == null) {
				return NOT_FOUND;
			}
			if (head.id == patient_id)
				return head;
			return getPatientById(head.next, patient_id);
	}

}
