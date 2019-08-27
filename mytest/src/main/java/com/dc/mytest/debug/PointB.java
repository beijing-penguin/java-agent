package com.dc.mytest.debug;

import java.math.BigDecimal;

public class PointB {
	int x, y;

	public int a(int dx, int dy) {
		byte aa = 1;
		short b = 2;
		int a = 1;
		long c = 1;
		BigDecimal dd = new BigDecimal("22222222222222222");
		String e = "e";
		PointB t = new PointB();
		t.a(2, 3);
		return dx + dy;
	}
	
	public int b() {
		byte aa = 1;
		short b = 2222;
		int a = 111111;
		long c = 3;
		BigDecimal dd = new BigDecimal("22222222222222222");
		String e = "e";
		PointB t = new PointB();
		return a + b;
	}
}