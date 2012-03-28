package gov.usgs.ngwmn.ik.simple;

import java.io.OutputStream;

public class Broker2 {
	/**
	 * Expect this to be called from a servlet
	 * 
	 * @param spec
	 * @param out
	 */
	public void handle(Specification spec, OutputStream out) {
		if (spec.isMultiSpec()) {
			handleMultiSpec(spec, out);
			return;
		}
		// handle just a single site request
		
	}

	public void handleMultiSpec(Specification spec, OutputStream out) {
		// TODO Auto-generated method stub
		
	}
}
