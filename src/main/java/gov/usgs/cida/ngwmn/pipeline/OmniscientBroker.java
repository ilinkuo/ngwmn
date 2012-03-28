package gov.usgs.cida.ngwmn.pipeline;

import gov.usgs.cida.ngwmn.pipeline.Packager.MultiInvoker;

import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;

public class OmniscientBroker {
	DataFetcher fetcher;
	private Packager packager;
	
	public void handle(Specification spec, OutputStream out) {
		if (spec.isMultiSpec()) {
			handleMultiSpec(spec, out);
			return;
		}
		
		XMLStreamReader xIn = fetcher.configureInput(spec);
		XMLSreamWriter xOut = configureOutput(spec, out);
		
		Invoker invoker = null;
		if (spec.needsFlattening()) {
			invoker = Flattener.makeInvoker(spec);
		} else {
			invoker = XMLCopyer.makeInvoker();
		}
		
		invoker.xInvoke(xIn, xOut);
		
		
	}

	private void handleMultiSpec(Specification spec, OutputStream out) {
		MultiInvoker invoker = packager.makeMultiInvoker(spec);
		
		CloneableIterator<Specification> sites = spec.getSiteIterator();
		Iterator<InputStreamTuple> logs = fetcher.fetchMulti(spec, WellDataType.LOG);
		Iterator<InputStreamTuple> waterLevels = fetcher.fetchMulti(spec, WellDataType.WATER_LEVEL);
		Iterator<InputStreamTuple> waterQuality = fetcher.fetchMulti(spec, WellDataType.WATER_QUALITY);
		
		// TODO Think through the OutputStream manipulations
		invoker.invoke(sites, out, logs, waterLevels, waterQuality);
		
	}

	private XMLSreamWriter configureOutput(Specification spec, OutputStream out) {
		Map<String, String> headers = parseHeaders(spec);
		setHeaders(out, headers);
		
		if (spec.isZipped()) {
			out = ZipTransformer.transform(out);
		}
		// more stuff can be done later
		return XMLOutputAdapter.adapt(out);
	}

	private void setHeaders(OutputStream out, Map<String, String> headers) {
		// TODO Auto-generated method stub
		
	}

	private Map<String, String> parseHeaders(Specification spec) {
		// TODO Auto-generated method stub
		return null;
	}


}
