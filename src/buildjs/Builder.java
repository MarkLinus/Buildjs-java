package buildjs;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.regex.Matcher;

import buildjs.exception.NotEnoughArgumentsException;
import buildjs.exception.UndeclaredDirectiveException;
import buildjs.macro.Block;
import buildjs.macro.Directive;
import buildjs.macro.Macro;

public class Builder {
	protected String input;
	protected HashMap<String, String> replacements;
	
	public Builder(CharSequence input, HashMap<String, String> reps) {
		this.input = input.toString();
		this.replacements = reps;
	}
	
	public void addReplacement(String key, String value) {
		this.replacements.put(key,  value);
	}

	public Builder(CharSequence input) {
		this(input, new HashMap<String, String>());
	}
	
	protected String replace() {
		String str = this.input;
		Set<String> keys = this.replacements.keySet();
		for (String key : keys) {
			str = str.replaceAll(key, this.replacements.get(key));
		}
		return str;
	}
	
	protected LinkedList<String> tokenize(LinkedList<CharSequence> js) {
		LinkedList<String> tokens = new LinkedList<String>();
		Matcher m = RE.BLOCK.matcher(this.input);
		int offset = 0;
		while (m.find()) {
			tokens.add(m.group());
			js.add(this.input.subSequence(offset, m.start()));
			offset = m.end();
		}
		js.add(this.input.subSequence(offset, this.input.length()));
		return tokens;
	}
	
	protected LinkedList<Block> parse(LinkedList<String> tokens) {
		LinkedList<Block> blocks = new LinkedList<Block>();
		while (!tokens.isEmpty()) {
			String token = tokens.remove(0);
			Block block = Block.parseBlock(token);
			blocks.add(block);
		}
		return blocks;
	}
	
	protected StringBuilder execute(LinkedList<Block> blocks, LinkedList<CharSequence> js) throws Exception {
		StringBuilder output = new StringBuilder();
		while (!blocks.isEmpty()) {
			Block b = blocks.remove(0);
			Directive[] d = b.toArray();
			output.append(js.remove(0));
			for (int i = 0; i < d.length; i++) {
				String name = d[i].name();
				Macro m = Buildjs.macros.get(name);
				if (m == null) {
					throw new UndeclaredDirectiveException("Undeclared directive @" + name);
				}
				String[] args = d[i].args();
				if (args.length < m.argc()) {
					throw new NotEnoughArgumentsException("Expected " + m.argc() + " arguments for @" + name + ", but only " + args.length + " were found");
				}
				js.add(output);
				String result = m.execute(args, this, js);
				output = new StringBuilder()
					.append(output)
					.append(result);
			}
		}
		output.append(js.remove(0));
		return output;
	}
	
	public String build() throws Exception {
		LinkedList<CharSequence> js = new LinkedList<CharSequence>();
		LinkedList<String> tokens = this.tokenize(js);
		LinkedList<Block> blocks = this.parse(tokens);
		StringBuilder output = this.execute(blocks, js);
		return output.toString();
	}
	
	public Builder getChildBuilder(CharSequence input) {
		return new Builder(input, this.replacements);
	}
}
