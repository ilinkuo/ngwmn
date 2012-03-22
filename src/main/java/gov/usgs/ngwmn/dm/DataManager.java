package gov.usgs.ngwmn.dm;

import gov.usgs.ngwmn.WellDataType;
import gov.usgs.ngwmn.dm.cache.Loader;
import gov.usgs.ngwmn.dm.cache.Retriever;
import gov.usgs.ngwmn.dm.cache.Specifier;
import gov.usgs.ngwmn.dm.cache.fs.FileCache;
import gov.usgs.ngwmn.dm.harvest.FakeHarvestor;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataManager extends HttpServlet {

	private static final long serialVersionUID = 2L;
	private DataBroker db;
	private Logger logger = LoggerFactory.getLogger(DataManager.class);
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		String basedir = config.getInitParameter("FSCache.basedir");
		
		FileCache c = new FileCache();
		File bd = new File(basedir);
		c.setBasedir(bd);
		db = new DataBroker();
		db.setRetriever( new Retriever(c) );
		db.setLoader(new Loader(c));
		
		db.setHarvester(new FakeHarvestor());
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		Specifier spec = parseSpecifier(req);
		checkSpec(spec);
		
		try {
			// resp.setContentType("application/xml");
			resp.setContentType("text/plain");
			resp.setCharacterEncoding("utf8");
			
			ServletOutputStream puttee = resp.getOutputStream();
			try {
				logger.info("Getting well data for {}", spec);
				db.fetchWellData(spec, puttee);
			} catch (Exception e) {
				logger.error("Problem getting well data", e);
			} finally {
				puttee.close();
			}
		} finally {
			logger.info("Done with request for specifier {}", spec);
		}
		
	}

	/** Parse a specifier from the request.
	 * This may get arbitrarily complex, but the specifier should not be evaluated here.
	 * The specifer is a query and can be constructed without touching the cache.
	 * 
	 * @param req
	 * @return
	 */
	private Specifier parseSpecifier(HttpServletRequest req) {
		String featureID = req.getParameter("featureID");
		
		Specifier spec = new Specifier();
		spec.setFeatureID(featureID);
		
		String type = req.getParameter("type");
		if (type == null) {
			type = "ALL";
		}
		WellDataType wdt = WellDataType.valueOf(type);
		spec.setTypeID(wdt);
		
		String agency = req.getParameter("agencyID");
		if (agency == null) {
			agency = "USGS";
		}
		spec.setAgencyID(agency);
		
		return spec;
	}

	private void checkSpec(Specifier spec) throws ServletException {
		if (null == spec.getFeatureID() || spec.getFeatureID().isEmpty()) {
			throw new ServletException("No feature identified by input");			
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Specifier spec = parseSpecifier(req);
		checkSpec(spec);
		
//		db.put(spec, req.getInputStream());
	}

}
