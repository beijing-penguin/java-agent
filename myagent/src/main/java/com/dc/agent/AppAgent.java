package com.dc.agent;

import java.lang.instrument.Instrumentation;

public class AppAgent {
	public static MyClassFileTransformer cft = new MyClassFileTransformer();
	public static void agentmain(String args, Instrumentation inst) throws Exception {
		System.out.println("Agent Main called..agentArgs="+args);
		inst.addTransformer(cft, true);
		inst.retransformClasses(ClassLoader.getSystemClassLoader().loadClass(args));
		
		inst.removeTransformer(cft);
	}

	public static void premain(String args, Instrumentation inst) throws Exception {
		System.out.println("premain Pre Args:" + args);
	}
}
