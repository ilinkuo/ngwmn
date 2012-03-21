package gov.usgs.ngwmn;

import gov.usgs.ngwmn.dm.cache.Specifier;

import java.io.OutputStream;

public interface DataLoader {
	OutputStream getOutputStream(Specifier spec);
}
