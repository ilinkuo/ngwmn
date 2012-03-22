package gov.usgs.ngwmn.dm.harvest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import gov.usgs.ngwmn.dm.cache.PipeStatistics;
import gov.usgs.ngwmn.dm.cache.Specifier;
import gov.usgs.ngwmn.dm.cache.fs.FileCache;
import gov.usgs.ngwmn.dm.io.Invoker;

public class FakeInputInvoker implements Invoker {

	private Specifier specifier;
	public FakeInputInvoker(Specifier spec) {
		specifier = spec;
	}

	@Override
	public void invoke(InputStream is, OutputStream os, PipeStatistics stats)
			throws IOException {
		String s = String.valueOf(specifier);
		
		InputStream fakeInput = new ByteArrayInputStream(s.getBytes());
		
		FileCache.copyStream(fakeInput, os, stats);

	}

}
