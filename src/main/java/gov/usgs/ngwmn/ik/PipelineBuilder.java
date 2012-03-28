package gov.usgs.ngwmn.ik;

public interface PipelineBuilder {
	public void setInvoker(Invoker invoker);
	
	public void prependOutput(OutputProvider out);
	public void appendOutput(OutputProvider out);
	
	public void prependInput(InputProvider in);
	public void appendInput(InputProvider in);
	
	public Pipeline build() throws InvalidPipelineException;
	
	public class InvalidPipelineException extends RuntimeException{}
}
