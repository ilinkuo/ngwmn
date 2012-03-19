package gov.usgs.ngwmn;

import java.io.InputStream;

public interface DataLoader {
	void loadWellData(String siteId, String typeId, InputStream data);
}
