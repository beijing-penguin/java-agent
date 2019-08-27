package com.dc.mytest.asm;

import org.objectweb.asm.util.ASMifier;

public class Test {
	public static void main(String[] args) throws Throwable {
		ASMifier.main(new String[] {"com.dc.mytest.debug.PointA"});
	}
}
