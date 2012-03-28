package gov.usgs.ngwmn.ik.transformers.impl;

import java.io.OutputStream;
import java.util.zip.ZipOutputStream;

import gov.usgs.ngwmn.ik.transformers.OutOutTransformer;

public class ZipTransformer implements OutOutTransformer{

	@Override
	public OutputStream transform(OutputStream out) {
		return new ZipOutputStream(out);
	}
	
	
}
