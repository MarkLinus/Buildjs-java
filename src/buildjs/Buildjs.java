package buildjs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.HashMap;

import buildjs.macro.Macro;

public class Buildjs {
	private Buildjs() {}
	
	/*
	 * Macros
	 */
	protected static HashMap<String, Macro> macros = new HashMap<String, Macro>();
	
	public static void addMacro(String name, Macro macro) {
		macros.put(name, macro);
	}
	
	static {
		Buildjs.addMacro("inc", new Macro() {
			public String execute(String[] args, Builder builder, LinkedList<CharSequence> js) throws Exception {
				String path = args[0];
				if (path.matches("^\".*\"$")) {
					path = path.substring(1, path.length() - 1);
				}
				File f = new File(Buildjs.cwd, path).getAbsoluteFile();
				String cwd = f.getParent();
				StringBuffer contents = fread(cwd, path);
				Builder b = builder.getChildBuilder(contents);
				return b.build();
			}
			
			public int argc() {
				return 1;
			}
		});
		
		Buildjs.addMacro("def", new Macro() {
			public String execute(String[] args, Builder builder, LinkedList<CharSequence> js) throws Exception {
				String name = args[0];
				String value = args[1];
				builder.addReplacement(name, value);
				int len = js.size();
				for (int i = 0; i < len; i++) {
					String code = js.remove(0).toString();
					code = code.replaceAll(name, value);
					js.add(code);
				}
				return "";
			}
			
			public int argc() {
				return 2;
			}
		});
	}
	
	/*
	 * File IO
	 */
	protected static StringBuffer fread(String parent, String path) throws IOException {
		BufferedReader input = new BufferedReader(new FileReader(new File(parent, path)), 1);
		StringBuffer contents = new StringBuffer();
		String ls = System.getProperty("line.separator");
		String line = null;
		while ((line = input.readLine()) != null) {
			contents.append(line);
			contents.append(ls);
		}
		input.close();
		return contents;
	}
	
	protected static StringBuffer fread(String path) throws IOException {
		return fread("./", path);
	}
	
	protected static void fwrite(String parent, String path, String contents) throws IOException {
		BufferedWriter output = new BufferedWriter(new FileWriter(new File(parent, path)), 1);
		output.write(contents);
		output.flush();
		output.close();
	}
	
	protected static void fwrite(String path, String contents) throws IOException {
		fwrite("./", path, contents);
	}
	
	/*
	 * Buildjs
	 */
	protected static String cwd = "./";
	
	public static String build(CharSequence input, String cwd) throws Exception {
		Buildjs.cwd = cwd;
		Builder b = new Builder(input);
		return b.build();
	}
	
	public static String build(CharSequence input) throws Exception {
		return build(input, "./");
	}
	
	public static void buildFile(String fin, String fout) throws Exception {
		File f = new File(fin).getAbsoluteFile();
		StringBuffer input = fread(fin);
		String output = build(input, f.getParent());
		fwrite(fout, output);
	}
	
	public static String version() {
		return "1.0.0";
	}
	
	/*
	 * UI
	 */
	protected static void parseArgs(String[] args) throws Exception {
	}
	
	public static void main(String[] args) {
		try {
			if (args.length == 0) {
				throw new Exception("Nothing to do");
			}
			String fin, fout;
			fin = args[0];
			if (args.length == 1) {
				fout = fin + ".out.js";
			} else {
				fout = args[1];
			}
			buildFile(fin, fout);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
