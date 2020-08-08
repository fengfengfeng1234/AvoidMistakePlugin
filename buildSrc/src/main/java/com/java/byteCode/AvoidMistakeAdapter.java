package com.java.byteCode;

import com.java.exception.NotFindFieldException;
import com.java.pluginExtends.AvoidMistakeField;
import com.java.pluginExtends.AvoidMistakeMethod;
import com.java.pluginExtends.AvoidMistakesExtend;

import org.gradle.internal.file.FileException;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.util.ArrayList;

public class AvoidMistakeAdapter extends ClassVisitor {
    AvoidMistakesExtend avoidMistakesExtend;
    AvoidMistakeField avoidMistakeField;
    /**
     * 当前类型
     */
    private String className;

    String matchClass;
    /**
     * 当前类的父类 假如存在的话
     */
    private String superName;

    public AvoidMistakeAdapter(AvoidMistakesExtend avoidMistakes, ClassVisitor classVisitor) {
        super(Opcodes.ASM7, classVisitor);
        this.avoidMistakesExtend = avoidMistakes;
    }

    @Override
    public void visit(int version, int access, String className, String signature, String superName, String[] interfaces) {
        super.visit(version, access, className, signature, superName, interfaces);
        logVisit(version, access, className, signature, superName, interfaces);
        this.className = className;
        this.superName = superName;
        matchClass = className.replace("/", ".");
    }


    /**
     * 方法回调
     *
     * @return
     */
    @Override
    public MethodVisitor visitMethod(int access, String methodName, String desc, String signature, String[] exceptions) {
        //从传进来的ClassWriter中读取MethodVisitor
        MethodVisitor mv = cv.visitMethod(access, methodName, desc, signature, exceptions);
        boolean isMatch = avoidMistakesExtend.matchClassName(matchClass);
        System.out.println("AvoidMistakeAdapter  visitMethod  isMatch = " + isMatch);
        if (isMatch) {
            AvoidMistakeMethod avoidMistakeMethod = avoidMistakesExtend.methods.get(matchClass);
            if (avoidMistakeMethod != null && avoidMistakeMethod.methodParentName.equals(methodName)) {
                System.out.println("AvoidMistakeAdapter  visitMethod " + " methodName =  " + methodName + "desc = " + desc + " avoidMistakeMethod = " + avoidMistakeMethod.toString());
                return mv == null ? null : new AvoidMistakeMethod2Adapter(avoidMistakeMethod, mv, className, 500, access, methodName, desc);
            }
            System.out.println("AvoidMistakeAdapter  visitMethod  access =" + access + " methodName =  " + methodName + "desc = " + desc);
        }
        return mv;
    }


    @Override
    public void visitEnd() {
        super.visitEnd();
        System.out.println("AvoidMistakeAdapter  visitEnd ");
        if (avoidMistakeField != null && !avoidMistakeField.isEnd) {
            throw new NotFindFieldException("配置字段值和预期不一致 avoidMistakeField = " + avoidMistakeField.toString());
        }

    }

    @Override
    public FieldVisitor visitField(int access, String fileName, String desc, String signature, Object value) {
        FieldVisitor mv = cv.visitField(access, fileName, desc, signature, value);
        boolean isMatch = avoidMistakesExtend.matchClassName(matchClass);
        logVisitFile(className, access, fileName, desc, signature, value, isMatch);

        if (isMatch) {

            avoidMistakeField = avoidMistakesExtend.fields.get(matchClass);
            if (avoidMistakeField != null) {
                if (avoidMistakeField.fieldName.equals(fileName)) {
                    if (avoidMistakeField.fieldValue.equals(value)) {
                        avoidMistakeField.isEnd = true;
                    }
                }
            }
        }
        System.out.println("AvoidMistakeAdapter  visitMethod  isMatch = " + isMatch);

        return mv;
    }


    private void logVisitFile(String className, int access, String name, String desc, String signature, Object value, boolean isMatch) {
        System.out.println("AvoidMistakeAdapter   logVisitFile  ===matched====>" + "  className===" + className + "   access===" + access + "   methodName===" + name + "   desc===" + desc + "   signature===" + signature + " value = " + value + "  isMatch = " + isMatch);
    }


    private void logVisit(int version, int access, String className, String signature, String superName, String[] interfaces) {
        //System.out.println("AvoidMistakeAdapter   visit  ===matched====>" + " version===" + version + "  className===" + className + "   access===" + access + "   superName===" + superName + "   interfaces===" + interfaces + "   signature===" + signature);
    }


}
