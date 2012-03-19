package gov.usgs.ngwmn.dm.cache;

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

	public Statistics get(Specifier spec, OutputStream puttee)
			throws IOException;

}