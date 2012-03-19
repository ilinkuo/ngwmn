package gov.usgs.ngwmn.dm;

import gov.usgs.ngwmn.dm.cache.Specifier;
import gov.usgs.ngwmn.dm.cache.fs.FileCache;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DataManager extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private DataBroker db;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		String basedir = config.getInitParameter("FSCache.basedir");
		
		FileCache c = new FileCache();
		File bd = new File(basedir);
		c.setBasedir(bd);
		db = new DataBroker();
		db.setCache(c);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		Specifier spec = parseSpecifier(req);
		
		try {
			// resp.setContentType("application/xml");
			resp.setContentType("text/plain");
			resp.setCharacterEncoding("utf8");
			
			ServletOutputStream puttee = resp.getOutputStream();
			try {
				db.get(spec, puttee);
			} finally {
				puttee.close();
			}
		} finally {
			// log data output
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
		
		return spec;
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
		
		db.put(spec, req.getInputStream());
	}
	
	

}
