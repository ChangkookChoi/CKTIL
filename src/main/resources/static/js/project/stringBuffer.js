/*String Buffer (동적태그를 쉽게 만들기 위해 하는 것) */
var StringBuffer = function(){
	this.buffer = new Array();
};
StringBuffer.prototype.append = function(str){
	this.buffer[this.buffer.length] = str;
};
StringBuffer.prototype.toString = function(){
	return this.buffer.join("");
};
