package gov.usgs.cida.ngwmn.pipeline;

import java.io.OutputStream;
import java.util.Iterator;

/**
 * DataFetcher's job is to configure the pipeline fragment touching the Cache and Harvester
 * @author ilinkuo
 *
 */
public class DataFetcher {

	private Cache cache;
	private Harvester harvester;

	public XMLStreamReader fetchSingle(Specification spec) {
		spec = spec.getFirst();
		// The following sequence can change quite a bit, depending on where you want the intelligence
		if (!spec.isCacheBypassed() && cache.contains(spec)) {
			return cache.retrieve(spec);
		} else {
			OutputStream loadToCache = cache.getLoader(spec);
			// Note that this is a pipeline fragment, in the sense that it has no invoker
			// Pipeline fragments can be passed around quite easily.
			// There's no need to pass around the entire pipeline
			Job job = makeSingleJob(spec);
			return Tee.fork(harvester.retrieveSingle(job), loadToCache);
		}
	}

	private Job makeSingleJob(Specification spec) {
		// TODO Auto-generated method stub
		return null;
	}

	public Iterator<InputStreamTuple> fetchMulti(final Specification specs,
			WellDataType log) {

		return new Iterator<InputStreamTuple>() {
			CloneableIterator<Specification> sites = specs.getSiteIterator();

			@Override
			public boolean hasNext() {
				return sites.hasNext();
			}

			@Override
			public InputStreamTuple next() {
				if (sites.hasNext()) {
					Specification spec = sites.next();
					// TODO Hm, things are a little fuzzy here
//					 return fetchSingle(spec);
				}
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void remove() {
				// TODO Auto-generated method stub
				
			}
			
		};


	}

	public XMLStreamReader configureInput(Specification spec) {
		// TODO Auto-generated method stub
		return null;
	}

}
