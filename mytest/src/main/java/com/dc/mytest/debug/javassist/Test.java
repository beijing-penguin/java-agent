package com.dc.mytest.debug.javassist;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

public class Test {
public static void main(String[] args) throws Throwable {
	ClassPool pool = ClassPool.getDefault();
	CtClass cc = pool.get("com.dc.mytest.debug.javassist.Point");
	CtMethod m = cc.getDeclaredMethod("move");
	m.insertAt(3,"{ System.out.println($1); System.out.println($2); }");
	//cc.writeFile();
	Class<?> clazz=  cc.toClass();
	Point p = (Point) clazz.cast(new Point());
	p.move(1, 2);
}
}
class Point {
	int x, y;

	void move(int dx, int dy) {
		x += dx;
		y += dy;
	}
}