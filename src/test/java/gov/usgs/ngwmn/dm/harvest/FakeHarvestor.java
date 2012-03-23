package gov.usgs.ngwmn.dm.harvest;

import gov.usgs.ngwmn.dm.DataFetcher;
import gov.usgs.ngwmn.dm.cache.Specifier;
import gov.usgs.ngwmn.dm.io.FakeInputInvoker;
import gov.usgs.ngwmn.dm.io.Pipeline;

public class FakeHarvestor implements DataFetcher {

	@Override
	public boolean configureInput(Specifier spec, Pipeline pipe)
			throws Exception {
		
		if (spec.getAgencyID().contains("FAIL")) {
			return false;
		}
		pipe.setInvoker(new FakeInputInvoker(spec));
		return true;
	}

}
