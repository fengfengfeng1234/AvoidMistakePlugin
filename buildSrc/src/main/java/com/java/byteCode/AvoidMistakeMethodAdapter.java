package com.java.byteCode;

import com.java.pluginExtends.AvoidMistakeMethod;
import com.java.pluginExtends.AvoidMistakesExtend;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class AvoidMistakeMethodAdapter extends MethodVisitor {
    AvoidMistakeMethod avoidMistakeMethod;
    String methodName;

    public AvoidMistakeMethodAdapter(MethodVisitor methodVisitor, AvoidMistakeMethod avoidMistakeMethod, String methodName) {
        super(Opcodes.ASM7, methodVisitor);
        this.avoidMistakeMethod = avoidMistakeMethod;
        this.methodName=methodName;
    }

    @Override
    public void visitLdcInsn(Object value) {
        super.visitLdcInsn(value);
        System.out.println("AvoidMistakeMethodAdapter visitLdcInsn value  = " + value + " methodName  "+ methodName);
    }

    /**
     * 开始访问方法代码
     */
    @Override
    public void visitCode() {
        super.visitCode();
        System.out.println("AvoidMistakeMethodAdapter  visitCode   ");
    }

    /**
     * 方法整体结束
     */
    @Override
    public void visitEnd() {
        super.visitEnd();
    }

    @Override
    public void visitInsn(int opcode) {
        super.visitInsn(opcode);
        System.out.println("AvoidMistakeMethodAdapter visitInsn  opcode  = " + opcode);
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
    }
}
