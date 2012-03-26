package gov.usgs.ngwmn.dm;

import gov.usgs.ngwmn.dm.cache.Specifier;

import javax.servlet.ServletException;

// TODO Should this extend ServletException or RuntimeException?
public class SiteNotFoundException extends ServletException {

	private static final long serialVersionUID = 1L;

	public SiteNotFoundException(String message) {
		super(message);
	}

	public SiteNotFoundException(Specifier spec) {
		super("No site found for " + spec);
	}
	
}
