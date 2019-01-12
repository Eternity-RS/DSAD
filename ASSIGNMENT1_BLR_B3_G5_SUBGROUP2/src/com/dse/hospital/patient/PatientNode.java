package com.dse.hospital.patient;

public class PatientNode {
	String name;
	int age;
	int id;
	PatientNode previous;
	PatientNode next;
	private static int numberOfPatients = 0;

	/**
	 * @param name
	 * @param age
	 */
	public PatientNode(String name, int age) {
		this.name = name;
		this.age = age;
		// Incrementing numberOfPatients and setting to id
		this.id = numberOfPatients++;

	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

}
