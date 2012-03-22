package gov.usgs.ngwmn.dm.io;

import java.io.IOException;
import java.io.OutputStream;

public class OpCountOutputStream extends OutputStream {
	private OutputStream delegate;
	int closeCt = 0;
	int writeCt = 0;
	int writeByteCt = 0;
	int flushCt = 0;
	
	public OpCountOutputStream(OutputStream delegate) {
		super();
		this.delegate = delegate;
	}

	public void close() throws IOException {
		closeCt++;
		delegate.close();
	}

	public void flush() throws IOException {
		flushCt++;
		delegate.flush();
	}

	public void write(byte[] b, int off, int len) throws IOException {
		writeCt++;
		writeByteCt += len;
		delegate.write(b, off, len);
	}

	public void write(byte[] b) throws IOException {
		writeCt ++;
		writeByteCt += b.length;
		delegate.write(b);
	}

	public void write(int b) throws IOException {
		writeCt ++;
		writeByteCt += 1;
		delegate.write(b);
	}

	public int getCloseCt() {
		return closeCt;
	}

	public int getWriteCt() {
		return writeCt;
	}

	public int getWriteByteCt() {
		return writeByteCt;
	}

	public int getFlushCt() {
		return flushCt;
	}
	
	
}