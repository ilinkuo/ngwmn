package gov.usgs.cida.ngwmn.pipeline;

import gov.usgs.cida.ngwmn.pipeline.Packager.MultiInvoker.IterationMode;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Packager {

	public MultiInvoker makeMultiInvoker(Specification spec) {
		// TODO Auto-generated method stub
		
		MultiInvoker invoker = new MultiInvoker();
		if (spec.iterateBySites()) {
			invoker.setIterationMode(IterationMode.BY_SITES);
		} else if (spec.iterateBySites()) {
			invoker.setIterationMode(IterationMode.BY_DATATYPE);
		}
		// do other configuration
		return invoker;
	}
	
	public static class MultiInvoker extends Invoker {
		public static enum IterationMode {BY_SITES, BY_DATATYPE}

		private IterationMode mode;;

		public void invoke(Iterator<Specification> specs, OutputStream out,
				Iterator<InputStreamTuple>... streams) {
			
			if (mode == IterationMode.BY_DATATYPE) {
				
			} else { // BY_SITE
				Map<WellDataType, InputStreamTuple> currentStream = new HashMap<WellDataType, InputStreamTuple>();
				while(specs.hasNext()) {
					Specification spec = specs.next();
					for (WellDataType type: WellDataType.values()) {
						// check if the current stream for the datatype is null
						// if null, get the next one
						// if still null, then continue
						// check if it is for the same site
						// if no, continue
						// if yes, make other checks before writing to the stream
					}
				}
			}

			
		}

		public void setIterationMode(IterationMode bySites) {
			// TODO Auto-generated method stub
			
		}
		
	}

}
