package buildjs.macro;

import java.util.LinkedList;
import java.util.regex.Matcher;

import buildjs.RE;

public class BlockParser {
	protected String code;
	protected LinkedList<String> tokens;
	
	public BlockParser(String code) {
		this.code = code.substring(9, code.length() - 2);
	}
	
	protected void tokenize() {
		Matcher m = RE.TOKENIZER.matcher(this.code);
		this.tokens = new LinkedList<String>();
		while (m.find()) {
			this.tokens.add(m.group());
		}
	}
	
	protected void skip(String re) {
		while (!this.tokens.isEmpty() && this.tokens.get(0).matches(re)) {
			this.tokens.remove(0);
		}
	}
	
	protected String parseArg() {
		int[] levels = new int[]{0, 0, 0};
		StringBuilder arg = new StringBuilder();
		while (!this.tokens.isEmpty() && (!tokens.get(0).matches(RE.IGN_ALL) || levels[0] > 0 || levels[1] > 0 || levels[2] > 0)) {
			String token = this.tokens.remove(0);
			switch (token) {
				case "(":
					levels[0]++;
					break;
				case "[":
					levels[1]++;
					break;
				case "{":
					levels[2]++;
					break;
				case ")":
					levels[0]--;
					break;
				case "]":
					levels[1]--;
					break;
				case "}":
					levels[2]--;
					break;
			}
			arg.append(token);
		}
		return arg.toString();
	}
	
	public Block parse() {
		this.tokenize();
		Block block = new Block();
		while (!this.tokens.isEmpty()) {
			String name = this.tokens.remove(0);
			if (name.charAt(0) != '@') {
				continue;
			}
			
			LinkedList<String> arglist = new LinkedList<String>();
			while (!this.tokens.isEmpty()) {
				if (this.tokens.get(0).matches(RE.LINE_BREAK)) {
					break;
				}
				this.skip(RE.IGN_SPACES);
				if (!this.tokens.isEmpty()) {
					arglist.add(this.parseArg());
				}
			}
			String[] args = new String[arglist.size()];
			arglist.toArray(args);

			Directive m = new Directive(name.substring(1), args);
			block.add(m);
			this.skip(RE.IGN_ALL);
		}
		return block;
	}
}
