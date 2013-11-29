package buildjs.macro;

import java.util.LinkedList;

import buildjs.Builder;

public interface Macro {
	public String execute(String[] args, Builder builder, LinkedList<CharSequence> js) throws Exception;
	public int argc();
}
