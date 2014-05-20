/*buildjs
 * @def VERSION "1.0.0"
 * @def AUTHOR "Danilo Valente"
 */
var Point = /*buildjs @inc Point.js*/
var Circle = function (x, y) {
	this.center = new Point(x, y);
};
Circle.prototype.author = AUTHOR;
Circle.prototype.version = VERSION;
