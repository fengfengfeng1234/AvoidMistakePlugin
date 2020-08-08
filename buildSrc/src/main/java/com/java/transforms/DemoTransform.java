package com.java.transforms;

import com.android.build.api.transform.DirectoryInput;
import com.android.build.api.transform.Format;
import com.android.build.api.transform.JarInput;
import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.api.transform.TransformOutputProvider;
import com.android.build.gradle.internal.pipeline.TransformManager;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.gradle.internal.impldep.org.sonatype.aether.spi.connector.Transfer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;
import java.util.function.Consumer;

import groovy.io.FileType;
import groovy.lang.Closure;
import groovy.transform.stc.ClosureParams;
import groovy.transform.stc.SimpleType;

/**
 * Transform每次都是将一个输入进行处理，然后将处理结果输出，
 * 而输出的结果将会作为另一个Transform的输入。
 * 每个 Transform 会处理某些特定的资源流，
 * 如何指定需要处理的资源流是通过 getInputTypes 与 getScopes 一起决定的。
 */
public class DemoTransform extends Transform {


    /**
     * 指明本Transform的名字，随意，但是不能包含某些特殊字符，否则会报错。
     *
     * @return
     */
    @Override
    public String getName() {
        return DemoTransform.class.getSimpleName();
    }

    /**
     * 输入文件的类型
     * 可供我们去处理的有两种类型, 分别是编译后的java代码, 以及资源文件
     */
    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS;
    }

    /**
     * 指明Transform的作用域，
     * 例如，返回 TransformManager.SCOPE_FULL_PROJECT 表示配置 Transform 的作用域为全工程。
     *
     * @return
     */
    @Override
    public Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT;
    }


    /**
     * 是否支持增量
     * 如果支持增量执行，则变化内容可能包含，修改/删除/添加 文件列表
     *
     * @return
     */
    @Override
    public boolean isIncremental() {
        return false;
    }


    /**
     * 用于处理具体的输入输出，核心操作都在这里。
     * 上例中，配置 Transform 的输入类型为 Class，
     * 作用域为全工程，因此在transform方法中，
     * inputs 会传入工程内所有的 class 文件
     */
    @Override
    public void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException, FileNotFoundException {
        super.transform(transformInvocation);
        System.out.println(DemoTransform.class.getSimpleName()+ "   start ");
        transformInvocation.getInputs().forEach(new Consumer<TransformInput>() {
            @Override
            public void accept(TransformInput transformInput) {
                /**
                 * 遍历目录
                 * 文件夹里面包含的是我们手写的类以及
                 * R.class
                 * BuildConfig.class
                 * 以及R$XXX.class等
                 */
                transformInput.getDirectoryInputs().
                        forEach(new Consumer<DirectoryInput>() {
                            @Override
                            public void accept(DirectoryInput directoryInput) {

                                File file = directoryInput
                                        .getFile();


                                try {
                                    eachFileRecurse(file, FileType.ANY, new Call<File>() {
                                        @Override
                                        public void call(File f) {
                                            if (checkFileName(f.getName())) {
                                              //  injectClassFile(file);
                                                //可以检测.class 文件读取
                                                System.out.println("FileName = "+f.getName() + " path = "+ file.getAbsolutePath());

                                            }
                                        }
                                    });
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                copyDirectory(directoryInput, transformInvocation.getOutputProvider());
                            }
                        });


                /**
                 *  只是针对jar文件
                 *  防止同名冲突
                 *  疑问： 每一个transform 都要这样做 ?
                 */
//                transformInput.getJarInputs().forEach(new Consumer<JarInput>() {
//                    @Override
//                    public void accept(JarInput jarInput) {
//                        try {
//                            copy(jarInput, transformInvocation.getOutputProvider());
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
            }
        });
    }

    private void copy(JarInput jarInput, TransformOutputProvider outputProvider) throws IOException {
        String jarName = jarInput.getName();
        System.out.println("jar = " + jarInput.getFile().getAbsolutePath());
        String md5Name = DigestUtils.md5Hex(jarInput.getFile().getAbsolutePath());
        if (jarName.endsWith(".jar")) {
            jarName = jarName.substring(0, jarName.length() - 4);
        }
        File dest = outputProvider.getContentLocation(jarName + md5Name, jarInput.getContentTypes(), jarInput.getScopes(), Format.JAR);
        FileUtils.copyFile(jarInput.getFile(), dest);
    }

    private void copyDirectory(DirectoryInput directoryInput, TransformOutputProvider outputProvider) {
        // 获取output目录
        File dest = outputProvider.getContentLocation(directoryInput.getName(),
                directoryInput.getContentTypes(), directoryInput.getScopes(), Format.DIRECTORY);
        // 将input的目录复制到output指定目录
        try {
            FileUtils.copyDirectory(directoryInput.getFile(), dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private boolean checkFileName(String name) {
        return name.endsWith(".class") && !name.startsWith("R$") &&
                "R.class" != name && "BuildConfig.class" != name;
    }

    public static void eachFileRecurse(File self, FileType fileType, @ClosureParams(value = SimpleType.class, options = {"java.io.File"}) Call call) throws FileNotFoundException, IllegalArgumentException {
        checkDir(self);
        File[] files = self.listFiles();
        if (files != null) {
            File[] var4 = files;
            int var5 = files.length;

            for (int var6 = 0; var6 < var5; ++var6) {
                File file = var4[var6];
                if (file.isDirectory()) {
                    if (fileType != FileType.FILES) {
                        call.call(file);
                    }
                    eachFileRecurse(file, fileType, call);
                } else if (fileType != FileType.DIRECTORIES) {
                    call.call(file);
                }
            }

        }
    }

    interface Call<T> {
        public void call(T arguments);
    }

    private static void checkDir(File dir) throws FileNotFoundException, IllegalArgumentException {
        if (!dir.exists()) {
            throw new FileNotFoundException(dir.getAbsolutePath());
        } else if (!dir.isDirectory()) {
            throw new IllegalArgumentException("The provided File object is not a directory: " + dir.getAbsolutePath());
        }
    }
}
