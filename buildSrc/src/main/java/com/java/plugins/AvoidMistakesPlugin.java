package com.java.plugins;

import com.android.build.gradle.AppExtension;
import com.java.pluginExtends.AvoidMistakesExtend;
import com.java.transforms.ASMAvoidMistakeTransform;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.util.Collections;

/**
 * 目前无法使用
 * 避免失误
 * 疑问：
 * Plugin
 * afterEvaluate
 * transform
 * 以上的加载顺序 不清楚
 */
public class AvoidMistakesPlugin implements Plugin<Project> {

    @Override
    public void apply(Project target) {
        AppExtension appExtension = target.getExtensions()
                .findByType(AppExtension.class);
        System.out.println("AvoidMistakesPlugin =====> init ");
        //创建指定扩展
        target.getExtensions().create("avoidMistake", AvoidMistakesExtend.class);


        target.afterEvaluate(new Action<Project>() {
            //项目评估之后回调
            @Override
            public void execute(Project project1) {
                AvoidMistakesExtend avoidExtension = project1.getExtensions().getByType(AvoidMistakesExtend.class);
                avoidExtension.init();
                System.out.println(" AvoidMistakesPlugin ---> " + avoidExtension.toString());

            }
        });
//
        appExtension.registerTransform(new ASMAvoidMistakeTransform(target), Collections.EMPTY_LIST);
    }
}
