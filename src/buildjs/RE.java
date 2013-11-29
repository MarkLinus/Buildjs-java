package buildjs;

import java.util.regex.Pattern;

public final class RE {
	private RE() {}

	public final static String LINE_BREAK = "^(\\r|\\n)$";

	public final static String LBESCAPE = "\\\\\\r?+\\n";
	
	public final static Pattern TOKENIZER = Pattern.compile("@\\w+|\".*?\"|'.*?'|[\\w_$]+|" + LBESCAPE + "|[\\s\\S]");
	
	public final static String IGN_SPACES = "^(" + LBESCAPE + "|[ \\t])+$";
	
	public final static String IGN_ALL = "^(" + LBESCAPE + "|[ \\t\\r\\n])+$";
	
	public final static Pattern BLOCK = Pattern.compile("/\\*buildjs.*?\\*/", Pattern.DOTALL);
}
