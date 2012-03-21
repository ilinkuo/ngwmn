package gov.usgs.ngwmn.dm.pipe;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class Pipeline {

	protected InputStream input;
	protected OutputStream output;
	
	public Pipeline(InputStream is, OutputStream os) {
		input = is;
		output = os;
	}
	
	public abstract void perform() throws IOException;
}
