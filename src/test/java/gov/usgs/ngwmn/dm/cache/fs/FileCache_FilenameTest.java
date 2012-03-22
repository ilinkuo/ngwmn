package gov.usgs.ngwmn.dm.cache.fs;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import gov.usgs.ngwmn.WellDataType;
import gov.usgs.ngwmn.dm.cache.Specifier;

import org.junit.Before;
import org.junit.Test;

public class FileCache_FilenameTest extends FileCache {

	@Before
	public void setup() throws IOException {
		File tmp = new File("/tmp");
		File basedir = new File(tmp,"fscache_test_dir");
		basedir.mkdirs();
		setBasedir(basedir);
	}
	
	@Test
	public void testBaseContentFile() throws IOException {
		Specifier spec = new Specifier();
		spec.setFeatureID("safe");
		spec.setAgencyID("SAFE");
		spec.setTypeID(WellDataType.ALL);
		
		File f = super.contentFile(spec);
		assertTrue("pre-existing condition", f.createNewFile()||f.exists());
	}

	@Test
	public void testUnsafeFeatureID() throws IOException {
		Specifier spec = new Specifier();
		spec.setFeatureID(":very/unsafe/Feature!ID");
		spec.setAgencyID("SAFE");
		spec.setTypeID(WellDataType.ALL);
		
		File f = super.contentFile(spec);
		f.createNewFile();
		assertTrue("funny feature id", f.exists());
	}
	
	@Test
	public void testUnsafeAgencyID() throws IOException {
		Specifier spec = new Specifier();
		spec.setFeatureID("safe");
		spec.setAgencyID("un/safe/\u0003Agency/ID");
		spec.setTypeID(WellDataType.ALL);
		
		File f = super.contentFile(spec);
		f.createNewFile();
		assertTrue("funny agency id", f.exists());
	}
	
	@Test
	public void testRepeatable() {
		Specifier spec = new Specifier();
		spec.setFeatureID("safe");
		spec.setAgencyID("AGID");
		spec.setTypeID(WellDataType.ALL);
		
		File f1 = super.contentFile(spec);
		File f2 = super.contentFile(spec);
		assertEquals("same cache file", f1,f2);
	}

	@Test
	public void testUsesAgency() {
		Specifier spec = new Specifier();
		spec.setFeatureID("safe");
		spec.setAgencyID("ONE");
		spec.setTypeID(WellDataType.ALL);
		
		File f1 = super.contentFile(spec);
		spec.setAgencyID("TWO");
		File f2 = super.contentFile(spec);
		assertFalse("same cache file", f1.equals(f2));
		
	}
	
	@Test
	public void testUsesFeature() {
		Specifier spec = new Specifier();
		spec.setFeatureID("feature");
		spec.setAgencyID("AGID");
		spec.setTypeID(WellDataType.ALL);
		
		File f1 = super.contentFile(spec);
		spec.setFeatureID("creature");
		File f2 = super.contentFile(spec);
		assertFalse("same cache file", f1.equals(f2));
		
	}
	
	@Test
	public void testUsesType() {
		Specifier spec = new Specifier();
		spec.setFeatureID("safe");
		spec.setAgencyID("AGID");
		spec.setTypeID(WellDataType.WATERLEVEL);
		
		File f1 = super.contentFile(spec);
		spec.setTypeID(WellDataType.QUALITY);
		
		File f2 = super.contentFile(spec);
		assertFalse("same cache file", f1.equals(f2));
		
	}
	
	
}
