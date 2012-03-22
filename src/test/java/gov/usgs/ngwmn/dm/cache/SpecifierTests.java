package gov.usgs.ngwmn.dm.cache;

import static org.junit.Assert.*;
import gov.usgs.ngwmn.WellDataType;

import java.security.InvalidParameterException;

import org.junit.Before;
import org.junit.Test;

public class SpecifierTests {

	Specifier spec;
	
	@Before
	public void setUp() {
		spec = new Specifier();
		spec.setAgencyID("agency");
		spec.setFeatureID("well");
		spec.setTypeID(WellDataType.LOG);
	}
	
	@Test
	public void test_check_noEmptyFeatureId() {
		spec.setFeatureID("");
		try {
			Specifier.check(spec);
			assertTrue(false);
		} catch (NullPointerException e) {
			assertTrue(false);
		} catch (InvalidParameterException e) {
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	@Test
	public void test_check_noEmptyAgencyId() {
		spec.setAgencyID("");
		try {
			Specifier.check(spec);
			assertTrue(false);
		} catch (NullPointerException e) {
			assertTrue(false);
		} catch (InvalidParameterException e) {
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void test_check_notNullAgencyId() {
		spec.setAgencyID(null);
		try {
			Specifier.check(spec);
			assertTrue(false);
		} catch (NullPointerException e) {
			assertTrue(false);
		} catch (InvalidParameterException e) {
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void test_check_noNullTypeId() {
		spec.setTypeID(null);
		try {
			Specifier.check(spec);
			assertTrue(false);
		} catch (NullPointerException e) {
			assertTrue(false);
		} catch (InvalidParameterException e) {
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	@Test
	public void test_check_noNullFeatureId() {
		spec.setFeatureID(null);
		try {
			Specifier.check(spec);
			assertTrue(false);
		} catch (NullPointerException e) {
			assertTrue(false);
		} catch (InvalidParameterException e) {
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}
	}


}
