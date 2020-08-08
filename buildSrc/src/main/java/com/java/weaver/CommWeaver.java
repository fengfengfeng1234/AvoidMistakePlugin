package com.java.weaver;

import com.android.build.gradle.AppExtension;
import com.java.byteCode.AvoidMistakeAdapter;
import com.java.pluginExtends.AvoidMistakesExtend;
import com.quinn.hunter.transform.asm.BaseWeaver;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.io.IOException;
import java.io.InputStream;

public class CommWeaver extends BaseWeaver {

    private AppExtension appExtension;
    private AvoidMistakesExtend avoidMistakesExtend;

    public CommWeaver(AppExtension appExtension) {
        this.appExtension = appExtension;
    }

    @Override
    public void setExtension(Object extension) {
        if (extension == null) {
            throw new NullPointerException("CommWeaver  AvoidMistakesExtend  ==  null ");
        }
        this.avoidMistakesExtend = (AvoidMistakesExtend) extension;
    }

    @Override
    protected ClassVisitor wrapClassWriter(ClassWriter classWriter) {
        return new AvoidMistakeAdapter(avoidMistakesExtend, classWriter);
    }

    @Override
    public byte[] weaveSingleClassToByteArray(InputStream inputStream) throws IOException {
        return super.weaveSingleClassToByteArray(inputStream);
    }

    /**
     * 根据规则过滤文件
     * 避免多余的解析
     *
     * @param fullQualifiedClassName
     * @return
     */
    @Override
    public boolean isWeavableClass(String fullQualifiedClassName) {
        boolean flag = super.isWeavableClass(fullQualifiedClassName);
        /**
         * fullQualifiedClassName = com.example.myapplication.ui.login.LoginActivity.class
         */
        if (!flag){
            return false;
        }
        boolean isMatch = flag && avoidMistakesExtend.matchClassName(fullQualifiedClassName.replace(".class", ""));
        isWeavableClassLog(fullQualifiedClassName, isMatch);

        return isMatch;
    }


    private void isWeavableClassLog(String fullQualifiedClassName, boolean isMatch) {
//        System.out.println("CommWeaver   isWeavableClass  ===fullQualifiedClassName====>" + fullQualifiedClassName + " isMatch====>" + isMatch);
    }

}
