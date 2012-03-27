package gov.usgs.ngwmn.dm.cache.fs;

import gov.usgs.ngwmn.dm.cache.Cache;
import gov.usgs.ngwmn.dm.cache.CacheInfo;
import gov.usgs.ngwmn.dm.cache.Specifier;
import gov.usgs.ngwmn.dm.cache.PipeStatistics;
import gov.usgs.ngwmn.dm.cache.PipeStatistics.Status;
import gov.usgs.ngwmn.dm.io.FileInputInvoker;
import gov.usgs.ngwmn.dm.io.Invoker;
import gov.usgs.ngwmn.dm.io.Pipeline;
import gov.usgs.ngwmn.dm.io.TempfileOutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileCache implements Cache {
	private Logger logger = LoggerFactory.getLogger(FileCache.class);
	
	private File basedir;
	private String filename(Specifier spec) {
		// Note that there is no requirement to decode a cache file name; 
		// having the file name be human-readable is helpful for debugging,
		// not required for system operation
		return spec.getAgencyID()+"_"+spec.getFeatureID()+"_"+spec.getTypeID();
	}
	private String safeFileName(Specifier spec) {
		String starter = filename(spec);
		String scrambled = DigestUtils.md5Hex(starter);
		return scrambled;
	}
	
	private boolean isSafeFilename(String fn) {
		// could try to create the file, then delete it on success.
		// but that's pretty heavy-handed.
		// also susceptible to path injection, if ../ is allowed in input
		
		/* This is too liberal -- almost any character *can* be in a Unix filename.
		 */
/*		try {
 * 			File f = new File(fn);
			f.getCanonicalPath();
			// make sure they didn't spoof in a directory change
			return fn.equals(f.getName();
		} catch (IOException ioe) {
			return false;
		}
*/		
		return fn.matches("[A-Z a-z\\.0-9_-]+");
	}
	
	protected final File contentFile(Specifier spec) {
		
		String fname = filename(spec);
		if (fname.isEmpty()) {
			// can't happen, but let's be safe
			logger.error("Generated empty file name for spec {}", spec);
			throw new RuntimeException("Generated empty file name");
		}
		if ( ! isSafeFilename(fname)) {
			String sfname = safeFileName(spec);
			logger.warn("had to encode {} to {}", fname, sfname);
			fname = sfname;			
		}
		File v = new File(basedir,fname);
		return v;
	}
	
	/**
	 * @see gov.usgs.ngwmn.dm.cache.Cache#putter(gov.usgs.ngwmn.dm.cache.Specifier)
	 */
	@Override
	public OutputStream destination(Specifier well)
			throws IOException
	{
		File f = contentFile(well);
		File tf = File.createTempFile("LDR", ".xml");
		
		// TODO Need to make these stats available to DataBroker
		PipeStatistics s = new PipeStatistics();
		s.setStatus(Status.OPEN);
		
		OutputStream v = new TempfileOutputStream(f, tf, s);
		logger.info("Created tempfile output for {}", well);
		return v;
	}
	
	/**
	 * @see gov.usgs.ngwmn.dm.cache.Cache#get(gov.usgs.ngwmn.dm.cache.Specifier, java.io.OutputStream)
	 */
	@Override
	public boolean fetchWellData(Specifier spec, Pipeline pipe) 
			throws IOException
	{
		File f = contentFile(spec);
		
		FileInputStream fis = new FileInputStream(f);
		pipe.setInputStream(fis);
		pipe.getStatistics().setSpecifier(spec);
		Invoker i = new FileInputInvoker();
		pipe.setInvoker(i);
		
		return true;
	}
	
	private static void copyTo(InputStream is, OutputStream os, PipeStatistics stat) 
			throws IOException 
	{
		// TODO: measure performance, see if nio might be worthwhile.
		
		byte[] buf = new byte[1024];
		
		
		//TODO this var is not used at this time - is it for statistics?
		int ops = 0;
		while (true) {
			int ict = is.read(buf);
			if (ict <= 0) {
				break;
			}
			os.write(buf,0,ict);
			ops += ict;
			stat.incrementCount(ict);
		}
		
		os.close();
	}

	public static void copyStream(InputStream is, OutputStream os, PipeStatistics stats) 
			throws IOException
	{
		copyTo(is, os, stats);
	}
	
	
	public File getBasedir() {
		return basedir;
	}

	public void setBasedir(File basedir) throws IOException {
		if ( ! basedir.exists()) {
			boolean ok = basedir.mkdirs();
			if ( ! ok) {
				logger.warn("Failed to create base dir {}", basedir);
			}
		}
		if ( ! basedir.exists() ) {
			throw new IOException("Base directory does not exist");			
		}
		if ( ! basedir.isDirectory() ) {
			throw new IOException("Bse dir not a directory");
		}
		if ( ! basedir.canRead()) {
			throw new IOException("Cannot read base directory");
		}
		this.basedir = basedir;
	}

	public boolean contains(Specifier spec) {
		File f = contentFile(spec);
		
		if (f == null) {
			logger.warn("no such file spec as {}", f);
			return false;
		}
		if ( ! f.exists()) {
			logger.info("no cached file {}", f);
			return false;
		}
		if ( ! f.canRead()) {
			logger.warn("file exists but not readable {}", f);
		}
		return true;
	}

	@Override
	public CacheInfo getInfo(Specifier spec) {
		File f = contentFile(spec);

		boolean exists = f.exists() && f.canRead();
		Date created = null;
		long sz = -1;
		Date modified = null;
		
		if (exists) {
			modified = new Date(f.lastModified());
			// Java 6 does not provide access to file create time
			// Java 7 does
			sz = f.length();
		}
		
		return new CacheInfo(created, exists, modified, sz);
	}
	
	
}
