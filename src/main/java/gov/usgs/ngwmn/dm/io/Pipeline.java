package gov.usgs.ngwmn.dm.io;


import java.io.IOException;
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
	
	public void invoke() throws IOException {
		invoker.invoke(is,os);
	}
	
	
}
