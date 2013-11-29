package buildjs.macro;

import java.util.LinkedList;

public class Block extends LinkedList<Directive> {
	private static final long serialVersionUID = -8761073884525359516L;

	public Block() {
		super();
	}
	
	public Directive[] toArray() {
		Directive[] d = new Directive[this.size()];
		this.toArray(d);
		return d;
	}
	
	public static Block parseBlock(String code) {
		return new BlockParser(code).parse();
	}
}
