package gov.usgs.ngwmn.dm;

import java.io.IOException;
import java.io.OutputStream;

/** Copy the stream to two destinations.
 * If either destination fails, the operation fails.
 * 
 * @author rhayes
 *
 */
public class TeeOutputStream extends OutputStream {

	private OutputStream d1;
	private OutputStream d2;
	
	public TeeOutputStream(OutputStream d1, OutputStream d2) {
		this.d1 = d1;
		this.d2 = d2;
	}
	
	public void close() throws IOException {
		try {
			d1.close();
		} finally {
			d2.close();
		}
	}
	
	public void flush() throws IOException {
		try {
			d1.flush();
		} finally {
			d2.flush();
		}
	}
	
	public void write(byte[] arg0, int arg1, int arg2) throws IOException {
		try {
			d1.write(arg0, arg1, arg2);
		} finally {
			d2.write(arg0, arg1, arg2);
		}
	}
	
	public void write(byte[] b) throws IOException {
		try {
			d1.write(b);
		} finally {
			d2.write(b);
		}
	}
	
	public void write(int arg0) throws IOException {
		try {
			d1.write(arg0);
		} finally {
			d2.write(arg0);
		}
	}
	
}
