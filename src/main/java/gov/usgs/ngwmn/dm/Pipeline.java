package gov.usgs.ngwmn.dm;


import java.io.InputStream;
import java.io.OutputStream;

public class Pipeline {
	Invoker invoker;
	InputStream is;
	OutputStream os;
	
	public void setInputStream(InputStream in) {
		is = in;
	}
	
	public void setOutputStream(OutputStream out) {
		os = out;
	}
	public OutputStream getOutputStream() {
		return os;
	}
	
	public void setInvoker(Invoker invoke) {
		invoker = invoke;
	}
	
	public void invoke() {
		invoker.invoke(is,os);
	}
	
	
}
