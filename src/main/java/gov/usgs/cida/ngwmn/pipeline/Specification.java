package gov.usgs.cida.ngwmn.pipeline;


public class Specification {

	public boolean isMultiSpec() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean needsFlattening() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isZipped() {
		// TODO Auto-generated method stub
		return false;
	}

	public CloneableIterator<Specification> getSiteIterator() {
		return new CloneableIterator<Specification>();
	}

	public boolean iterateBySites() {
		// TODO Auto-generated method stub
		return false;
	}

	public Specification getFirst() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isCacheBypassed() {
		// TODO Auto-generated method stub
		return false;
	}

}
