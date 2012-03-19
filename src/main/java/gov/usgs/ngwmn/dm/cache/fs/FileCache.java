package gov.usgs.ngwmn.dm.cache.fs;

import gov.usgs.ngwmn.dm.cache.Cache;
import gov.usgs.ngwmn.dm.cache.Specifier;
import gov.usgs.ngwmn.dm.cache.Statistics;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileCache implements Cache {
	
	private File basedir;
	private String filename(Specifier spec) {
		return spec.getFeatureID();
	}
	
	private File contentFile(Specifier spec) {
		return new File(basedir,filename(spec));
	}
	
	/**
	 * @see gov.usgs.ngwmn.dm.cache.Cache#putter(gov.usgs.ngwmn.dm.cache.Specifier)
	 */
	public OutputStream destination(Specifier well)
			throws IOException
	{
		File f = contentFile(well);
		File tf = File.createTempFile("LDR", ".xml");
		Statistics s = new Statistics();
		
		return new TempfileOutputStream(f, tf, s);
	}
	
	public enum Status {OK,FAIL,DONE};
	
	/**
	 * @see gov.usgs.ngwmn.dm.cache.Cache#get(gov.usgs.ngwmn.dm.cache.Specifier, java.io.OutputStream)
	 */
	public Statistics get(Specifier spec, OutputStream puttee) 
			throws IOException
	{
		File f = contentFile(spec);
		
		FileInputStream fis = new FileInputStream(f);
		Statistics stat = new Statistics();
		copyTo(fis,puttee, stat);
		
		return stat;
	}
	
	private static void copyTo(InputStream is, OutputStream os, Statistics stat) 
			throws IOException 
	{
		// TODO: measure performance, see if nio might be worthwhile.
		
		byte[] buf = new byte[1024];
		
		int ops = 0;
		while (true) {
			int ict = is.read(buf);
			if (ict < 0) {
				break;
			}
			os.write(buf,ops,ict);
			ops += ict;
		}
		stat.setCount(ops);
	}

	public static Statistics copyStream(InputStream is, OutputStream os) 
			throws IOException
	{
		Statistics stat = new Statistics();
		copyTo(is, os, stat);
		return stat;
	}
	
	public File getBasedir() {
		return basedir;
	}

	public void setBasedir(File basedir) {
		this.basedir = basedir;
	}
	
	
}
