package gov.usgs.ngwmn.dm.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Invoker {
	void invoke(InputStream is, OutputStream os) throws IOException;
}
