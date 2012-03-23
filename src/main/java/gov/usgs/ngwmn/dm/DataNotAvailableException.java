package gov.usgs.ngwmn.dm;

import gov.usgs.ngwmn.dm.cache.Specifier;

import javax.servlet.ServletException;

// TODO Should this extend ServletException or RuntimeException?
public class DataNotAvailableException extends ServletException {

	private static final long serialVersionUID = 1L;

	public DataNotAvailableException(String message) {
		super(message);
	}

	public DataNotAvailableException(Specifier spec) {
		super("No data available for " + spec);
	}
	
}
