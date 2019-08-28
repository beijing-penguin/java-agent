package com.dc.mytest.debug;

import java.util.List;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

public class MainThread {

	public static void main(String[] args) throws InterruptedException {
		PointA aa = new PointA();
		PointB bb = new PointB();
		PointB bcc = new PointB();
		bb.toString();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				List<VirtualMachineDescriptor> list = VirtualMachine.list();  
		        for (VirtualMachineDescriptor vmd : list) {  
		            System.out.println("pid:" + vmd.id() + ":" + vmd.displayName());  
		        }
				while(true) {
					try {
						bcc.b();
						System.out.println(aa.b()+"--"+bb.b());
					} catch (Throwable e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}