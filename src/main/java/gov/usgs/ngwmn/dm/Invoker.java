package gov.usgs.ngwmn.dm;

import java.io.InputStream;
import java.io.OutputStream;

public interface Invoker {
	void invoke(InputStream is, OutputStream os);
}
