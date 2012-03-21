package gov.usgs.ngwmn.dm.pipe;

import gov.usgs.ngwmn.dm.cache.fs.FileCache;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class GenericPipeline extends Pipeline {

	public GenericPipeline(InputStream is, OutputStream os) {
		super(is, os);
	}

	@Override
	public void perform() throws IOException {
		FileCache.copyStream(input, output);
	}

}
