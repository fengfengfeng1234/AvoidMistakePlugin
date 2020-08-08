package com.java.transforms;

import com.android.build.gradle.AppExtension;
import com.java.pluginExtends.AvoidMistakesExtend;
import com.java.weaver.CommWeaver;
import com.quinn.hunter.transform.HunterTransform;
import com.quinn.hunter.transform.RunVariant;

import org.gradle.api.Project;

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

}
