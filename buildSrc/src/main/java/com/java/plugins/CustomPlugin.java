package com.java.plugins;

import com.android.build.gradle.AppExtension;
import com.java.transforms.DemoTransform;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class CustomPlugin implements Plugin<Project> {
    @Override
    public void apply(Project target) {
        System.out.println("task in CustomPlugin start");
        //创建指定扩展 并将project 传入构造函数
//        val doKitExt = project.extensions.create("dokitExt", DoKitExt::class.java)
        /**
         * 找到项目中的 某个继承
         */
        AppExtension obj = target.getExtensions()
                .findByType(AppExtension.class);
        if (obj != null) {
            // 往该扩展中添加 transform
            // 这里其实就是将我们自定义的这个 transform 添加到了集合中
            // 但是这里让我想不明白的是，为什么这个添加 transform 的方法是在 extension 里面
            // 而不是 project 里面，如果像 Java 工程，没有使用有扩展的插件该怎么办

            // 查看源码发现了这样的代码：com.android.build.gradle.internal.TaskManager.createPostCompilationTasks
            // AndroidConfig extension = variantScope.getGlobalScope().getExtension();
            // 它获取到了 android 扩展，然后拿到了其中的所有 transform
            // 嗯，看来这个是针对 Android 构建的
            // test transform
            obj.registerTransform(new DemoTransform());
            System.out.println("task in CustomPlugin end");
        }

    }
}
