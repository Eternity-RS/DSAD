package com.dse.hospital.consultation;

import com.dse.hospital.Menu;
import com.dse.hospital.patient.PatientRecord;
/**
 * 
 * Main program which calls loadFile and Menu.
 *
 */
public class HospitalConsultation {

	public static void main(String[] args) {
		PatientRecord p_record = PatientRecord.getInstance();
		boolean firsttime = true;
		// Load from file only once
		if (firsttime) {
			p_record.loadFile();
			firsttime = false;
		}
		Menu menu = new Menu();
		menu.runMenu();
	}
}
