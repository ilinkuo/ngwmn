package gov.usgs.ngwmn.dm;

import gov.usgs.ngwmn.WellDataType;
import gov.usgs.ngwmn.dm.cache.Loader;
import gov.usgs.ngwmn.dm.cache.Retriever;
import gov.usgs.ngwmn.dm.cache.Specifier;
import gov.usgs.ngwmn.dm.cache.fs.FileCache;
import gov.usgs.ngwmn.dm.harvest.Harvester;

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

public class DataManagerServlet extends HttpServlet {

	private static final long serialVersionUID = 2L;
	private DataBroker db;
	private Logger logger = LoggerFactory.getLogger(DataManagerServlet.class);
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		String basedir = config.getInitParameter("FSCache.basedir");
		if (basedir == null) {
			throw new ServletException("config parameter FSCache.basedir is required");
		}
		
		FileCache c = new FileCache();
		File bd = new File(basedir);
		try {
			c.setBasedir(bd);
		} catch (IOException ioe) {
			throw new ServletException(ioe);
		}
		
		db = new DataBroker();
		db.setRetriever( new Retriever(c) );
		db.setLoader(    new Loader(c)    );
		db.setHarvester( new Harvester()  );
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		Specifier spec = parseSpecifier(req);
		checkSpec(spec);
		
		String well_name = wellname(spec);
		
		try {
			// resp.setContentType("application/xml");
			resp.setContentType("application/zip");
			resp.setHeader("Content-Disposition", "attachment; filename="+well_name+".zip");
			// resp.setContentType("text/plain");
			// resp.setCharacterEncoding("utf8");
			
			// ensure that buffer size is greater than magic lower limit for
			// non-extant sites
			if (resp.getBufferSize() < 2000) {
				resp.setBufferSize(8*1024); // a reasonable guess at efficiency
			}
			ServletOutputStream puttee = resp.getOutputStream();
			try {
				logger.info("Getting well data for {}", spec);
				db.fetchWellData(spec, puttee);
			} catch (SiteNotFoundException nse) {
				// this may fail, if detected after output buffer has been flushed
				resp.resetBuffer();
				puttee = null;
				resp.sendError(HttpServletResponse.SC_NOT_FOUND, nse.getLocalizedMessage());
			} catch (DataNotAvailableException nda) {
				resp.resetBuffer();
				puttee = null;
				// TODO What's the right error code? 503? 504?
				resp.sendError(HttpServletResponse.SC_GATEWAY_TIMEOUT, nda.getLocalizedMessage());
			} catch (Exception e) {
				logger.error("Problem getting well data", e);
				puttee = null;
				throw new ServletException(e);
			} finally {
				if (puttee != null) {
					puttee.close();
				}
			}
		} finally {
			logger.info("Done with request for specifier {}", spec);
		}
		
	}

	private String wellname(Specifier spec) {
		return spec.getAgencyID() + "_" + spec.getFeatureID();
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
		
		String agency = req.getParameter("agency_cd");
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

}
