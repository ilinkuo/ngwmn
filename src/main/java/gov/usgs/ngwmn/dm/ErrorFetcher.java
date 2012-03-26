package gov.usgs.ngwmn.dm;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import gov.usgs.ngwmn.dm.cache.PipeStatistics;
import gov.usgs.ngwmn.dm.cache.Specifier;
import gov.usgs.ngwmn.dm.io.Invoker;
import gov.usgs.ngwmn.dm.io.Pipeline;

public class ErrorFetcher implements DataFetcher {

	public class ErrorInvoker implements Invoker {

		@Override
		public void invoke(InputStream is, OutputStream os, PipeStatistics stats)
				throws IOException {
			throw new IOException("some problem");
		}
	}

	@Override
	public boolean configureInput(Specifier spec, Pipeline pipe)
			throws Exception {
		pipe.setInvoker(new ErrorInvoker());
		return true;
	}

}
