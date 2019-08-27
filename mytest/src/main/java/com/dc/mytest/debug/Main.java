package com.dc.mytest.debug;

import com.sun.tools.attach.VirtualMachine;

public class Main {
	public static void main(String[] args) throws Exception {
		VirtualMachine vm = null;
		String agentjarpath = "F:\\eclipse-workspace\\myagent\\target\\myagent.jar"; // agentjar路径
		vm = VirtualMachine.attach("14236");// 目标JVM的进程ID（PID）
				vm.loadAgent(agentjarpath,"com.dc.mytest.debug.PointA");
		vm.detach();
	}
}
