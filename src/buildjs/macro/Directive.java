package buildjs.macro;

public class Directive {
	protected String name;
	protected String[] args;
	
	public Directive(String name, String[] args) {
		this.name = name;
		this.args = new String[args.length];
		System.arraycopy(args, 0, this.args, 0, args.length);
	}
	
	public String name() {
		return this.name;
	}
	
	public String[] args() {
		String[] args = new String[this.args.length];
		System.arraycopy(this.args, 0, args, 0, this.args.length);
		return args;
	}
}
