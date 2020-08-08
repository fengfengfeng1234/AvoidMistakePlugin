package com.java.byteCode;

import com.java.exception.NotFindMethodException;
import com.java.pluginExtends.AvoidMistakeMethod;

import org.gradle.internal.impldep.com.amazonaws.services.kms.model.NotFoundException;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

import java.util.ArrayList;


public class AvoidMistakeMethod2Adapter extends AdviceAdapter {
    AvoidMistakeMethod avoidMistakeMethod;

    private String methodName;
    private String className;
    /**
     * 函数耗时阈值
     */
    private int thresholdTime;
    /**
     * 是否属于静态方法
     */
    private boolean isStaticMethod;

    /**
     * Constructs a new {@link org.objectweb.asm.commons.AdviceAdapter}.
     *
     * @param className     类名
     * @param thresholdTime 时间阈值
     * @param methodVisitor the method visitor to which this adapter delegates calls.
     * @param access        the method's access flags (see {@link Opcodes}).
     * @param methodName    the method's name.
     * @param descriptor    the method's descriptor.
     */
    public AvoidMistakeMethod2Adapter(AvoidMistakeMethod avoidMistakeMethod, MethodVisitor methodVisitor, String className, int thresholdTime, int access, String methodName, String descriptor) {
        super(Opcodes.ASM7, methodVisitor, access, methodName, descriptor);
        this.avoidMistakeMethod = avoidMistakeMethod;
        this.className = className;
        this.thresholdTime = thresholdTime;
        //access值得计算方式为 Opcodes.ACC_PUBLIC & Opcodes.ACC_STATIC
        this.isStaticMethod = (access & Opcodes.ACC_STATIC) != 0;
        System.out.println("AvoidMistakeMethod2Adapter methodName  =   " + methodName);
    }

    @Override
    public void visitLdcInsn(Object value) {
        super.visitLdcInsn(value);
        System.out.println("AvoidMistakeMethod2Adapter value =   " + value);
    }

    @Override
    protected void onMethodEnter() {
        super.onMethodEnter();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("AvoidMistakeMethod2Adapter onMethodEnter  ");
        avoidMistakeMethod.isStart = true;
    }

    @Override
    protected void onMethodExit(int opcode) {
        super.onMethodExit(opcode);
        System.out.println("AvoidMistakeMethod2Adapter onMethodExit opcode =  " + opcode);
        if (!avoidMistakeMethod.isEnd) {
            throw new NotFindMethodException("AvoidMistakeMethod2Adapter  avoidMistakeMethod = " + avoidMistakeMethod.toString());
        }
        System.out.println();
        System.out.println();
        System.out.println();
    }

    @Override
    public void visitIincInsn(int var, int increment) {
        super.visitIincInsn(var, increment);
    }

    @Override
    public void visitMethodInsn(int opcodeAndSource, String owner, String methodName, String descriptor, boolean isInterface) {
        super.visitMethodInsn(opcodeAndSource, owner, methodName, descriptor, isInterface);
        System.out.println
                ("AvoidMistakeMethod2Adapter  visitMethodInsn "
                        + "opcodeAndSource = " + opcodeAndSource
                        + " owner = " + owner
                        + " methodName  = " + methodName
                        + " descriptor = " + descriptor
                        + " isInterface =" + isInterface);

        if (avoidMistakeMethod.useMethodName.equals(methodName)) {
            if (avoidMistakeMethod.desc != null) {
                if (avoidMistakeMethod.desc.equals(descriptor)) {
                    avoidMistakeMethod.isEnd = true;
                } else {
                    avoidMistakeMethod.isEnd = false;
                }
            } else {
                avoidMistakeMethod.isEnd = true;
            }
        }


    }
}
