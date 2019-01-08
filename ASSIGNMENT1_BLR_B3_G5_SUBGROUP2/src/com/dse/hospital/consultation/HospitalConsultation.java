package com.dse.hospital.consultation;

import com.dse.hospital.Menu;
import com.dse.hospital.patient.PatientRecord;

public class HospitalConsultation {
	boolean exit;

	public static void main(String[] args) {
		PatientRecord p_record = PatientRecord.getInstance();
		boolean firsttime = true;
		// Load from file only once
		if (firsttime) {
			p_record.loadFile(firsttime);
			firsttime = false;
		}
		Menu menu = new Menu();
		menu.runMenu();
	}
}
