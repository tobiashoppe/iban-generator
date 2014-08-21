package de.iban_generator.IbanGenerator.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.iban_generator.IbanGenerator.IbanGenerator;

public class IbanGeneratorTests {

	@Test
	public void testCalculateIban() {
		assertEquals("DE08700901001234567890", IbanGenerator.calculateIban(null, "70090100", "1234567890"));
		assertEquals("DE50160502020123456789", IbanGenerator.calculateIban(null, "16050202", "123456789"));
		assertEquals("DE48001234560123456789", IbanGenerator.calculateIban(null, "123456", "123456789"));
		assertEquals("DE85999999999999999999", IbanGenerator.calculateIban(null, "99999999", "9999999999"));
		assertEquals("DE36000000000000000000", IbanGenerator.calculateIban(null, "0", "000"));
	}

	@Test
	public void testCanExecute() {
		assertTrue(IbanGenerator.canExecute(null, "123", "1234"));
		assertTrue(IbanGenerator.canExecute(null, "0", "000"));
		try {
			assertFalse(IbanGenerator.canExecute(null, "0", "00"));
		} catch (NullPointerException e) {
			// expected exception everything is fine
		}
		try {
			assertFalse(IbanGenerator.canExecute(null, "", ""));
		} catch (NullPointerException e) {
			// expected exception everything is fine
		}
		try {
			assertFalse(IbanGenerator.canExecute(null, "000000000", "0000"));
		} catch (NullPointerException e) {
			// expected exception everything is fine
		}
		try {
			assertFalse(IbanGenerator.canExecute(null, "00000000", "00000000000"));
		} catch (NullPointerException e) {
			// expected exception everything is fine
		}
		try {
			assertFalse(IbanGenerator.canExecute(null, "12e333", "3333333"));
		} catch (NullPointerException e) {
			// expected exception everything is fine
		}
		try {
			assertFalse(IbanGenerator.canExecute(null, "1234", "1234er33"));
		} catch (NullPointerException e) {
			// expected exception everything is fine
		}
	}
}
