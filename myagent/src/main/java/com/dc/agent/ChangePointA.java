package com.dc.agent;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.ConstantDynamic;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.TypePath;

public class ChangePointA implements Opcodes {

	public static byte[] dump() throws Exception {

		ClassWriter classWriter = new ClassWriter(0);
		FieldVisitor fieldVisitor;
		MethodVisitor methodVisitor;
		AnnotationVisitor annotationVisitor0;

		classWriter.visit(V1_8, ACC_PUBLIC | ACC_SUPER, "com/dc/mytest/debug/PointA", null, "java/lang/Object", null);

		classWriter.visitSource("PointA.java", null);

		{
			fieldVisitor = classWriter.visitField(0, "x", "I", null, null);
			fieldVisitor.visitEnd();
		}
		{
			fieldVisitor = classWriter.visitField(0, "y", "I", null, null);
			fieldVisitor.visitEnd();
		}
		{
			methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
			methodVisitor.visitCode();
			Label label0 = new Label();
			methodVisitor.visitLabel(label0);
			methodVisitor.visitLineNumber(6, label0);
			methodVisitor.visitVarInsn(ALOAD, 0);
			methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
			methodVisitor.visitInsn(RETURN);
			Label label1 = new Label();
			methodVisitor.visitLabel(label1);
			methodVisitor.visitLocalVariable("this", "Lcom/dc/mytest/debug/PointA;", null, label0, label1, 0);
			methodVisitor.visitMaxs(1, 1);
			methodVisitor.visitEnd();
		}
		{
			methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "a", "(II)I", null, null);
			methodVisitor.visitCode();
			Label label0 = new Label();
			methodVisitor.visitLabel(label0);
			methodVisitor.visitLineNumber(10, label0);
			methodVisitor.visitInsn(ICONST_1);
			methodVisitor.visitVarInsn(ISTORE, 3);
			Label label1 = new Label();
			methodVisitor.visitLabel(label1);
			methodVisitor.visitLineNumber(11, label1);
			methodVisitor.visitInsn(ICONST_2);
			methodVisitor.visitVarInsn(ISTORE, 4);
			Label label2 = new Label();
			methodVisitor.visitLabel(label2);
			methodVisitor.visitLineNumber(12, label2);
			methodVisitor.visitInsn(ICONST_1);
			methodVisitor.visitVarInsn(ISTORE, 5);
			Label label3 = new Label();
			methodVisitor.visitLabel(label3);
			methodVisitor.visitLineNumber(13, label3);
			methodVisitor.visitInsn(LCONST_1);
			methodVisitor.visitVarInsn(LSTORE, 6);
			Label label4 = new Label();
			methodVisitor.visitLabel(label4);
			methodVisitor.visitLineNumber(14, label4);
			methodVisitor.visitTypeInsn(NEW, "java/math/BigDecimal");
			methodVisitor.visitInsn(DUP);
			methodVisitor.visitLdcInsn("22222222222222222");
			methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/math/BigDecimal", "<init>", "(Ljava/lang/String;)V",
					false);
			methodVisitor.visitVarInsn(ASTORE, 8);
			Label label5 = new Label();
			methodVisitor.visitLabel(label5);
			methodVisitor.visitLineNumber(15, label5);
			methodVisitor.visitLdcInsn("e");
			methodVisitor.visitVarInsn(ASTORE, 9);
			Label label6 = new Label();
			methodVisitor.visitLabel(label6);
			methodVisitor.visitLineNumber(16, label6);
			methodVisitor.visitTypeInsn(NEW, "com/dc/mytest/debug/PointA");
			methodVisitor.visitInsn(DUP);
			methodVisitor.visitMethodInsn(INVOKESPECIAL, "com/dc/mytest/debug/PointA", "<init>", "()V", false);
			methodVisitor.visitVarInsn(ASTORE, 10);
			Label label7 = new Label();
			methodVisitor.visitLabel(label7);
			methodVisitor.visitLineNumber(17, label7);
			methodVisitor.visitVarInsn(ALOAD, 10);
			methodVisitor.visitInsn(ICONST_2);
			methodVisitor.visitInsn(ICONST_3);
			methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "com/dc/mytest/debug/PointA", "a", "(II)I", false);
			methodVisitor.visitInsn(POP);
			Label label8 = new Label();
			methodVisitor.visitLabel(label8);
			methodVisitor.visitLineNumber(18, label8);
			methodVisitor.visitVarInsn(ILOAD, 1);
			methodVisitor.visitVarInsn(ILOAD, 2);
			methodVisitor.visitInsn(IADD);
			methodVisitor.visitInsn(IRETURN);
			Label label9 = new Label();
			methodVisitor.visitLabel(label9);
			methodVisitor.visitLocalVariable("this", "Lcom/dc/mytest/debug/PointA;", null, label0, label9, 0);
			methodVisitor.visitLocalVariable("dx", "I", null, label0, label9, 1);
			methodVisitor.visitLocalVariable("dy", "I", null, label0, label9, 2);
			methodVisitor.visitLocalVariable("aa", "B", null, label1, label9, 3);
			methodVisitor.visitLocalVariable("b", "S", null, label2, label9, 4);
			methodVisitor.visitLocalVariable("a", "I", null, label3, label9, 5);
			methodVisitor.visitLocalVariable("c", "J", null, label4, label9, 6);
			methodVisitor.visitLocalVariable("dd", "Ljava/math/BigDecimal;", null, label5, label9, 8);
			methodVisitor.visitLocalVariable("e", "Ljava/lang/String;", null, label6, label9, 9);
			methodVisitor.visitLocalVariable("t", "Lcom/dc/mytest/debug/PointA;", null, label7, label9, 10);
			methodVisitor.visitMaxs(3, 11);
			methodVisitor.visitEnd();
		}
		{
			methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "b", "()I", null,
					new String[] { "java/lang/Throwable" });
			methodVisitor.visitCode();
			Label label0 = new Label();
			methodVisitor.visitLabel(label0);
			methodVisitor.visitLineNumber(21, label0);
			methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
			methodVisitor.visitVarInsn(LSTORE, 1);
			Label label1 = new Label();
			methodVisitor.visitLabel(label1);
			methodVisitor.visitLineNumber(22, label1);
			methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Math", "random", "()D", false);
			methodVisitor.visitLdcInsn(new Double("10.0"));
			methodVisitor.visitInsn(DMUL);
			methodVisitor.visitInsn(D2L);
			methodVisitor.visitLdcInsn(new Long(500L));
			methodVisitor.visitInsn(LMUL);
			methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Thread", "sleep", "(J)V", false);
			Label label2 = new Label();
			methodVisitor.visitLabel(label2);
			methodVisitor.visitLineNumber(23, label2);
			methodVisitor.visitInsn(ICONST_1);
			methodVisitor.visitVarInsn(ISTORE, 3);
			Label label3 = new Label();
			methodVisitor.visitLabel(label3);
			methodVisitor.visitLineNumber(24, label3);
			methodVisitor.visitIntInsn(SIPUSH, 2222);
			methodVisitor.visitVarInsn(ISTORE, 4);
			Label label4 = new Label();
			methodVisitor.visitLabel(label4);
			methodVisitor.visitLineNumber(25, label4);
			methodVisitor.visitLdcInsn(new Integer(411111));
			methodVisitor.visitVarInsn(ISTORE, 5);
			Label label5 = new Label();
			methodVisitor.visitLabel(label5);
			methodVisitor.visitLineNumber(26, label5);
			methodVisitor.visitLdcInsn(new Long(3L));
			methodVisitor.visitVarInsn(LSTORE, 6);
			Label label6 = new Label();
			methodVisitor.visitLabel(label6);
			methodVisitor.visitLineNumber(27, label6);
			methodVisitor.visitTypeInsn(NEW, "java/math/BigDecimal");
			methodVisitor.visitInsn(DUP);
			methodVisitor.visitLdcInsn("22222222222222222");
			methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/math/BigDecimal", "<init>", "(Ljava/lang/String;)V",
					false);
			methodVisitor.visitVarInsn(ASTORE, 8);
			Label label7 = new Label();
			methodVisitor.visitLabel(label7);
			methodVisitor.visitLineNumber(28, label7);
			methodVisitor.visitLdcInsn("e");
			methodVisitor.visitVarInsn(ASTORE, 9);
			Label label8 = new Label();
			methodVisitor.visitLabel(label8);
			methodVisitor.visitLineNumber(29, label8);
			methodVisitor.visitTypeInsn(NEW, "com/dc/mytest/debug/PointA");
			methodVisitor.visitInsn(DUP);
			methodVisitor.visitMethodInsn(INVOKESPECIAL, "com/dc/mytest/debug/PointA", "<init>", "()V", false);
			methodVisitor.visitVarInsn(ASTORE, 10);
			Label label9 = new Label();
			methodVisitor.visitLabel(label9);
			methodVisitor.visitLineNumber(30, label9);
			methodVisitor.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
			methodVisitor.visitTypeInsn(NEW, "java/lang/StringBuilder");
			methodVisitor.visitInsn(DUP);
			methodVisitor.visitLdcInsn("\u65b9\u6cd5\u6267\u884c\u65f6\u95f4cost=");
			methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "(Ljava/lang/String;)V",
					false);
			methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
			methodVisitor.visitVarInsn(LLOAD, 1);
			methodVisitor.visitInsn(LSUB);
			methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append",
					"(J)Ljava/lang/StringBuilder;", false);
			methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;",
					false);
			methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V",
					false);
			Label label10 = new Label();
			methodVisitor.visitLabel(label10);
			methodVisitor.visitLineNumber(31, label10);
			methodVisitor.visitVarInsn(ILOAD, 5);
			methodVisitor.visitInsn(IRETURN);
			Label label11 = new Label();
			methodVisitor.visitLabel(label11);
			methodVisitor.visitLocalVariable("this", "Lcom/dc/mytest/debug/PointA;", null, label0, label11, 0);
			methodVisitor.visitLocalVariable("time", "J", null, label1, label11, 1);
			methodVisitor.visitLocalVariable("aa", "B", null, label3, label11, 3);
			methodVisitor.visitLocalVariable("b", "S", null, label4, label11, 4);
			methodVisitor.visitLocalVariable("a", "I", null, label5, label11, 5);
			methodVisitor.visitLocalVariable("c", "J", null, label6, label11, 6);
			methodVisitor.visitLocalVariable("dd", "Ljava/math/BigDecimal;", null, label7, label11, 8);
			methodVisitor.visitLocalVariable("e", "Ljava/lang/String;", null, label8, label11, 9);
			methodVisitor.visitLocalVariable("t", "Lcom/dc/mytest/debug/PointA;", null, label9, label11, 10);
			methodVisitor.visitMaxs(6, 11);
			methodVisitor.visitEnd();
		}
		classWriter.visitEnd();

		return classWriter.toByteArray();
	}
}
