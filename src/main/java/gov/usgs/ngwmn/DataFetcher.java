package gov.usgs.ngwmn;

import java.io.InputStream;

public interface DataFetcher {
	InputStream fetchWellData(String siteId, String typeId) throws Exception;
}
