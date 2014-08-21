package de.iban_generator.IbanGenerator;

import java.math.BigInteger;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Composite;

public class IbanGenerator {

	private static String calculateChecksum(String bban) {
		String checkSumBase = bban + "131400";
		int moduloResult = new BigInteger(checkSumBase).mod(new BigInteger("97")).intValue();
		int result = 98 - moduloResult;
		if (result < 10) {
			return "0" + new Integer(result).toString();
		} else {
			return new Integer(result).toString();
		}
	}

	public static String calculateIban(Composite composite, String blz, String ktnr) {
		String iban = "";
		if (canExecute(composite, blz, ktnr)) {
			// BLZ have always 8 digits
			// Ktnrs must be filled with leading zeros until they have 10 digits
			// the BBAN consists of the BLz, additional zero digits, and the ktnr
			// the BBAN is connected with (131400) for Germany - that is the checksum base,
			// this base is taken modulo 97 and the result is subtracted from 98. As a result, we have the checksum.
			int numberOfLeadingZerosForBLZ = 8 - blz.length();
			int numberOfLeadingZerosForKtnr = 10 - ktnr.length();
			final String bban = getZeros(numberOfLeadingZerosForBLZ) + blz + getZeros(numberOfLeadingZerosForKtnr) + ktnr;
			final String checkSum = calculateChecksum(bban);
			iban = "DE" + checkSum + bban;
		}
		return iban;
	}

	public static boolean canExecute(Composite composite, String blz, String ktnr) {
		if (blz.length() > 8) {
			MessageDialog.openError(composite.getShell(), "BLZ nicht korrekt", "Die BLZ darf maximal 8 Stellen lang sein. Sie hat aber "
					+ blz.toString().length() + " Stellen.");
			return false;
		}
		if (ktnr.length() > 10 || ktnr.length() < 3) {
			MessageDialog.openError(composite.getShell(), "Kontonummer nicht korrekt",
					"Die Kontonummer muss mindestens 3 Stellen haben und darf maximal 10 Stellen lang sein. Sie hat aber "
							+ ktnr.toString().length() + " Stellen.");
			return false;
		}
		try {
			new Integer(blz);
		} catch (Exception e2) {
			MessageDialog.openError(composite.getShell(), "BLZ nicht korrekt", "Die BLZ darf nur aus Ziffern bestehen!");
			return false;
		}
		try {
			new BigInteger(ktnr);
		} catch (Exception e2) {
			MessageDialog.openError(composite.getShell(), "Kontonummer nicht korrekt", "Die Kontonummer darf nur aus Ziffern bestehen!");
			return false;
		}
		return true;
	}

	private static String getZeros(int numberOfLeadingZerosForKtnr) {
		String result = new String();
		for (int i = 0; i < numberOfLeadingZerosForKtnr; i++) {
			result += "0";
		}
		return result;
	}
}
