package com.dc.agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class MyClassFileTransformer implements ClassFileTransformer{
	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
		System.out.println("agentmain load Class  :" + className.replace("/", "."));
		try {
			if(className.replace("/", ".").equals("com.dc.mytest.debug.PointB")) {
				System.out.println("changeB");
				return ChangePointB.dump();
			}
			if(className.replace("/", ".").equals("com.dc.mytest.debug.PointA")) {
				System.out.println("changeA");
				return ChangePointA.dump();
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		return classfileBuffer;
	}
	
}