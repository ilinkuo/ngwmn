package gov.usgs.ngwmn.dm.cache;

import gov.usgs.ngwmn.dm.io.Pipeline;

import java.io.IOException;
import java.io.OutputStream;

public interface Cache {

	/** Create an outputstream to hold the data for specifier. 
	 * 
	 * @param well
	 * @param data
	 * @throws IOException
	 */
	public OutputStream destination(Specifier well) throws IOException;

	public boolean fetchWellData(Specifier spec, Pipeline pipe)
			throws IOException;

	public boolean contains(Specifier spec);;
}

