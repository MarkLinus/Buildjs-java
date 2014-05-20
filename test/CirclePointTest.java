package buildjs;

import buildjs.Buildjs;

public class CirclePointTest {
	private CirclePointTest() {}
	
	public static void main(String[] args) throws Exception {
		String js = "/*buildjs\n" +
			" * @def VERSION \"1.0.0\"\n" +
			" * @def AUTHOR \"Danilo Valente\"\n" +
			" */\n" +
			"var Point = /*buildjs @inc Point.js*/\n" +
			"var Circle = function (x, y) {\n" +
			"	this.point = new Point(x, y);\n" +
			"};\n" +
			"Circle.prototype.author = AUTHOR;\n" +
			"Circle.prototype.version = VERSION;\n";
		String output = Buildjs.build(js, "./test/");
		System.out.println(output);
	}
}
