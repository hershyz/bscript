# only voids are supported in bscript core
# voids must be declared before they are called.

num a = 5
num b = 11

void doMath
	num c = a + b
	println(c)
;

doMath()
