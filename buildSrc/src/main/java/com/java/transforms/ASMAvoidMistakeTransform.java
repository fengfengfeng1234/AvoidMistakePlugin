package com.java.transforms;

import com.android.build.api.transform.Context;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.api.transform.TransformOutputProvider;
import com.android.build.gradle.AppExtension;
import com.java.pluginExtends.AvoidMistakesExtend;
import com.java.weaver.CommWeaver;
import com.quinn.hunter.transform.HunterTransform;
import com.quinn.hunter.transform.RunVariant;

import org.gradle.api.Project;

import java.io.IOException;
import java.util.Collection;

/**
 * 使用ASM 进行 检测
 * ./gradlew clean -Dorg.gradle.daemon=false -Dorg.gradle.debug=true
 * http://quinnchen.cn/2018/09/13/2018-09-13-asm-transform/
 */
public class ASMAvoidMistakeTransform extends HunterTransform {
    private AvoidMistakesExtend avoidMistakesExtension;
    private String extensionName = "avoidMistake";
    private AppExtension appExtension;

    public ASMAvoidMistakeTransform(Project project) {
        super(project);
        appExtension = project.getExtensions()
                .findByType(AppExtension.class);
        avoidMistakesExtension = (AvoidMistakesExtend) project.getExtensions().getByName(extensionName);
        this.bytecodeWeaver=new CommWeaver(appExtension);
        this.bytecodeWeaver.setExtension(avoidMistakesExtension);
    }

    @Override
    protected RunVariant getRunVariant() {
        return avoidMistakesExtension.runVariant;
    }


    @Override
    public void transform(Context context, Collection<TransformInput> inputs, Collection<TransformInput> referencedInputs, TransformOutputProvider outputProvider, boolean isIncremental) throws IOException, TransformException, InterruptedException {
        System.out.println("ASMAvoidMistakeTransform =====> init ");
        super.transform(context, inputs, referencedInputs, outputProvider, isIncremental);
    }
}
