package com.dse.hospital;

import java.util.Scanner;

import com.dse.hospital.consultation.ConsultQueue;
import com.dse.hospital.patient.PatientRecord;

public class Menu {
	boolean exit;
	private Scanner keyboard;
	PatientRecord patient_record;
	int id;

	// Display Menu
	public void display_menu() {

		System.out.println("\n\n*************** Menu ************");
		System.out.println("1. Register Patient");
		System.out.println("2. Display and dequeue Next Patient");
		System.out.println("3. Display Queue");
		System.out.println("0. Exit the application");
		System.out.println("*********************************\n");

	}

	public int getMenuChoice() {
		keyboard = new Scanner(System.in);
		int choice = -1;
		do {
			System.out.print("Enter your choice: ");
			try {
				choice = Integer.parseInt(keyboard.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Invalid selection. Numbers only please.");
			}
			if (choice < 0 || choice > 3) {
				System.out.println("Choice outside of range. Please chose again.");
			}
		} while (choice < 0 || choice > 3);
		return choice;
	}

	public void choose_menu_option(int option) {
		Scanner input = new Scanner(System.in);
		PatientRecord dll = PatientRecord.getInstance();
		ConsultQueue patientqueue = ConsultQueue.getQueueInstance();
		switch (option) {
		case 0:
			System.out.println("Thank you for using our application.");
			input.close();
			System.exit(0);
			break;
		case 1: {
			try {
				System.out.println("Enter the Patient's Name :");
				String name = input.next();
				System.out.println("Enter the Patient's Age :");
				int age = input.nextInt();
				id = dll.registerPatient(name, age);
				System.out.println(" Patient :" + name + " is registered successfully with id : " + id);
				patientqueue.nextPatient();
			} catch (Exception ex) {
				System.out.println("Patient entry was not created.");
			}
			break;
		}
		case 2:
			patientqueue.nextPatient();
			patientqueue.dequeuePatient();
			break;
		case 3:
			patientqueue.displayQueue(dll);
			break;
		default:
			System.out.println("Unknown error has occured.");
		}
	}

	public void runMenu() {
		while (!exit) {
			display_menu();
			int choice = getMenuChoice();
			choose_menu_option(choice);
		}
	}
}