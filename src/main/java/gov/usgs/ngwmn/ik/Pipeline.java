package gov.usgs.ngwmn.ik;

public abstract class Pipeline {
	private Invoker invoker;
	private InputProvider in;
	private OutputProvider out;

	public Pipeline(InputProvider in, Invoker invoker, OutputProvider out) {
		this.invoker = invoker;
		this.in = in;
		this.out = out;
	};
	
	public void invoke() {
		invoker.invoke(in, out);
	}
	
	
}
