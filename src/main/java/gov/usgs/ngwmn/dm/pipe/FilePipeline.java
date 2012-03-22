package gov.usgs.ngwmn.dm.pipe;

import gov.usgs.ngwmn.dm.cache.fs.FileCache;

import java.io.IOException;
import java.io.FileInputStream;
import java.io.OutputStream;

public class FilePipeline extends Pipeline {

	private FileInputStream finput;
	
	public FilePipeline(FileInputStream is, OutputStream os) {
		super(is, os);
		finput = is;
	}

	@Override
	public void perform() throws IOException {
		// TODO Use NIO channel
		FileCache.copyStream(finput, output, null);
	}

}
