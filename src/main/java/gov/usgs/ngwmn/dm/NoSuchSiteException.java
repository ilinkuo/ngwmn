package gov.usgs.ngwmn.dm;

import gov.usgs.ngwmn.dm.cache.Specifier;

import javax.servlet.ServletException;

// TODO Should this extend ServletException or RuntimeException?
public class NoSuchSiteException extends ServletException {

	private static final long serialVersionUID = 1L;

	public NoSuchSiteException(String message) {
		super(message);
	}

	public NoSuchSiteException(Specifier spec) {
		super("No site found for " + spec);
	}
	
}
