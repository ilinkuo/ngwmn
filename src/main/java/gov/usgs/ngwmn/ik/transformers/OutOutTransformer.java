package gov.usgs.ngwmn.ik.transformers;

import java.io.OutputStream;

public interface OutOutTransformer {// genericize if possible

	public OutputStream transform(OutputStream out);
}
