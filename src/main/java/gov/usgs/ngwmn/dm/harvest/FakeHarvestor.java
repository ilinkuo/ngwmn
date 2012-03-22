package gov.usgs.ngwmn.dm.harvest;

import gov.usgs.ngwmn.dm.DataFetcher;
import gov.usgs.ngwmn.dm.cache.Specifier;
import gov.usgs.ngwmn.dm.io.Pipeline;

public class FakeHarvestor implements DataFetcher {

	@Override
	public boolean fetchWellData(Specifier spec, Pipeline pipe)
			throws Exception {
		return false;
	}

}
