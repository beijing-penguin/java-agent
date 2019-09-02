# 前言
> 最近去哪网刚开源了Bistoury，一个集线上问题debug和监控于一身工具框架，能很方便的在不停止jvm的情况的下，模拟eclipse的debug功能，并且不会像原始debug那样会阻塞其他进程，只会在断点处监控某一次的请求。bistoury底层借助了阿里的Arthas和唯品会的vjtools，以及asm、javassist字节码指令框架，并增强实现了debug中的断点Breakpoint模式，并以前端ui界面的形式直接展示和操作，可以说用来调试线上问题变得非常方便
### bistoury debug模块技术背景
>[https://github.com/qunarcorp/bistoury/blob/master/docs/cn/debug.md](https://github.com/qunarcorp/bistoury/blob/master/docs/cn/debug.md)
### bistoury中的一个日常笑话
>NASA要发射一个新型火箭，火箭发射升空后发现不行，NASA把火箭拖回来加了两行log，再次发射，发现又不行，又加了两行log发射，发现又不行....

当然这只是一个笑话，但这样的场景在我们的实际开发中却屡见不鲜，多少次系统重启后问题复现失败，多少次我们解决故障的时间就在不断地加log，发布，加log，发布的过程中溜走...

## 我的模拟实现（实战篇，对其中用到的classload技术、asm字节码技术不做科普，请自行了解）
>究竟无侵入debug技术、无侵入埋点、无侵入监控怎么实现的？我对此非常感兴趣，简单说就是获取正在执行jvm进程对应的目录的class文件，修改后重新classload到jvm中。属于jvm在线的字节码增强技术。本文只使用了asm字节码简单实现整个流程。
### 先介绍asm字节码生成、可以略过
>比如要生成com.dc.agent.ChangePointA.java的asm指令代码块，则使用mytest项目中com.dc.mytest.asm.Test.java
```java
package com.dc.mytest.asm;

import org.objectweb.asm.util.ASMifier;

public class Test {
	public static void main(String[] args) throws Throwable {
		ASMifier.main(new String[] {"com.dc.mytest.debug.PointA"});
	}
}

```
### 即便不知道asm代码如何编写也能使用asm框架自带工具ASMifier生成想要的字节码指令代码，如想要在PonitA.java文件中b方法里面动态加入打印方法的执行时间。先编辑b方法
```java
	public int b() {
		long time = System.currentTimeMillis();
		byte aa = 1;
		short b = 2222;
		int a = 111111;
		long c = 3;
		BigDecimal dd = new BigDecimal("22222222222222222");
		String e = "e";
		PointA t = new PointA();
		System.out.println("方法执行时间cost="+(System.currentTimeMillis()-time));
		return a;
	}
```
### 再使用ASMifier.main(new String[] {"com.dc.mytest.debug.PointA"});生成上面这段代码的asm结构，字节码太多，这里只展示部分字节码asm的字节码如下。。

```java
{
methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "b", "()I", null, null);
methodVisitor.visitCode();
Label label0 = new Label();
methodVisitor.visitLabel(label0);
methodVisitor.visitLineNumber(21, label0);
methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
methodVisitor.visitVarInsn(LSTORE, 1);
Label label1 = new Label();
methodVisitor.visitLabel(label1);
methodVisitor.visitLineNumber(22, label1);
methodVisitor.visitInsn(ICONST_1);
methodVisitor.visitVarInsn(ISTORE, 3);
Label label2 = new Label();
methodVisitor.visitLabel(label2);
methodVisitor.visitLineNumber(23, label2);
methodVisitor.visitIntInsn(SIPUSH, 2222);
methodVisitor.visitVarInsn(ISTORE, 4);
Label label3 = new Label();
methodVisitor.visitLabel(label3);
methodVisitor.visitLineNumber(24, label3);
methodVisitor.visitLdcInsn(new Integer(111111));
methodVisitor.visitVarInsn(ISTORE, 5);
Label label4 = new Label();
methodVisitor.visitLabel(label4);
methodVisitor.visitLineNumber(25, label4);
methodVisitor.visitLdcInsn(new Long(3L));
methodVisitor.visitVarInsn(LSTORE, 6);
Label label5 = new Label();
methodVisitor.visitLabel(label5);
methodVisitor.visitLineNumber(26, label5);
methodVisitor.visitTypeInsn(NEW, "java/math/BigDecimal");
methodVisitor.visitInsn(DUP);
methodVisitor.visitLdcInsn("22222222222222222");
methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/math/BigDecimal", "<init>", "(Ljava/lang/String;)V", false);
methodVisitor.visitVarInsn(ASTORE, 8);
Label label6 = new Label();
methodVisitor.visitLabel(label6);
methodVisitor.visitLineNumber(27, label6);
methodVisitor.visitLdcInsn("e");
methodVisitor.visitVarInsn(ASTORE, 9);
Label label7 = new Label();
methodVisitor.visitLabel(label7);
methodVisitor.visitLineNumber(28, label7);
methodVisitor.visitTypeInsn(NEW, "com/dc/mytest/debug/PointA");
methodVisitor.visitInsn(DUP);
methodVisitor.visitMethodInsn(INVOKESPECIAL, "com/dc/mytest/debug/PointA", "<init>", "()V", false);
methodVisitor.visitVarInsn(ASTORE, 10);
Label label8 = new Label();
methodVisitor.visitLabel(label8);
methodVisitor.visitLineNumber(29, label8);
methodVisitor.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
```
### 本次运行的demo，asm字节码指令代码我已经生成完，在com.dc.agent.ChangePointA文件中，将原来PointA的b方法中的代码int a = 111111;修改成init a = 411111，并加上方法执行时间打印。

***
---------------------------------asm介绍割-------------------------------------
***
# 开始本项目案例中的操作（在线修改PointA对象一个方法）
>两个项目：一个正常运行的项目（模拟线上项目运行）和一个agent项目，源代码托管到github

>下载的项目地址：[https://github.com/beijing-penguin/java-agent](https://links.jianshu.com/go?to=https%3A%2F%2Fgithub.com%2Fbeijing-penguin%2Fjava-agent)

>程序运行前，先去myagent项目目录下执行一下mvn clean install（或者idea/eclipse中run maven一下），target目录中会生成对应jar文件，并把Main.java中的F:\\eclipse-workspace\\myagent\\target\\myagent.jar  改成 自己本地的目录的url即可。。pom.xml文件中的jdk自行配置（目前使用jdk12最高版本，其他版本自行修改完打包即可）

>执行流程：先运行MainThread.java得main方法，保持jvm在线，模拟项目正在运行中。然后查看进程pid再去执行Main.java得main方法，执行完毕后，可以看到运行期间PointA内存被动态修改，打印出和原来不一样得结果，即测试完成。
本demo的agent 动态修改了PointA得方法b()中的局部变量int a =111111；改成了int a=411111。并加入一段方法执行cost时间信息打印。（其他更强大的功能，只需要用asm加强实现一下就行，本次只是简单玩法）
###  运行MainThread
```java
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
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}
```
#### 打印如下
>pid:14068:com.dc.mytest.debug.MainThread  
pid:8760:  
111111--113333   
111111--113333  
111111--113333  
111111--113333  
111111--113333  
111111--113333  
111111--113333  
111111--113333  

### 紧接着执行另外一个项目中的main方法，attach方法传入正在运行的目标jvm进程pid
```java
package com.dc.mytest.debug;

import com.sun.tools.attach.VirtualMachine;

public class Main {
	public static void main(String[] args) throws Exception {
		VirtualMachine vm = null;
		String agentjarpath = "F:\\eclipse-workspace\\myagent\\target\\myagent.jar"; // agentjar路径
		vm = VirtualMachine.attach("14068");// 目标JVM的进程ID（PID）
		vm.loadAgent(agentjarpath,"com.dc.mytest.debug.PointA");
		vm.detach();
	}
}
```
MainThread已经发生改变，打印如下：
>pid:14068:com.dc.mytest.debug.MainThread  
pid:8760:  
111111--113333  
111111--113333  
111111--113333  
111111--113333  
111111--113333  
111111--113333  
111111--113333  
111111--113333  
111111--113333  
Agent Main called..agentArgs=com.dc.mytest.debug.PointA  
agentmain load Class  :com.dc.mytest.debug.PointA  
changeA  
411111--113333  
方法执行时间cost=501  
411111--113333  
方法执行时间cost=2000  
411111--113333  
方法执行时间cost=0  
411111--113333  

#### （中间那段字符串只是日志信息，可以忽略不记）到此已经做到了借助agent修改了正在运行中MainThread线程所在jvm中的某个对象方法内部局部变量的值，并实时的打印输出了改变后的413333。并且打印出了执行时间。

### 小结：利用agent和asm汇编级别的字节码技术能轻松改变PointA正在运行的jvm中的任意一块内存区域，在方法前后添加 方法执行时间监控、或是打印方法执行过程中的局部变量的值、或者完成一次热部署、或者打印并修改安卓软件/游戏中的某些属性（wai gua？）等等无侵入的操作 也将变得非常简单

##### 最后，本人已离职，现在北京找一份5年java高级方面的工作。有公司岗位空缺的话的可以和我联系，微信号dc429544557
