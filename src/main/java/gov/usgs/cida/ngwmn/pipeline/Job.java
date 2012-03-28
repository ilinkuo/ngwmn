package gov.usgs.cida.ngwmn.pipeline;

import java.util.Iterator;

public class Job {
	public static Job makeSingleSpecJob(Specification spec) {
		return null;
	}
	
	public static Job makeCompositeJob(Iterator<Specification> specs) {
		return null;
	}
	
	public static Job makeSingleSpecJob(Specification spec, StatusHandler statusHandler) {
		return null;
	}
	
	public static Job makeCompositeJob(Iterator<Specification> specs, Iterator<StatusHandler> statusHandlers) {
		return null;
	}
	
	
}
