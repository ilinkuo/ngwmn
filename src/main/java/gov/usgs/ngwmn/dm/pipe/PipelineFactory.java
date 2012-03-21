package gov.usgs.ngwmn.dm.pipe;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class PipelineFactory {

	public Pipeline fit(InputStream i, OutputStream o) {
		if (i instanceof FileInputStream) {
			return fit((FileInputStream)i, o);
		}
		return new GenericPipeline(i, o);
	}
	
	public Pipeline fit(FileInputStream i, OutputStream o) {
		return new FilePipeline(i, o);
	}
}
