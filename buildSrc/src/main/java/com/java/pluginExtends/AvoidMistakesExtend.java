package com.java.pluginExtends;

import com.quinn.hunter.transform.RunVariant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AvoidMistakesExtend {

    public RunVariant runVariant = RunVariant.ALWAYS;
    /**
     * 字段检测
     * class->FieldName->FieldValue
     */
    public HashMap<String, AvoidMistakeField> fields = new HashMap<String, AvoidMistakeField>();
    /**
     * 方法是否使用
     * class->MethodParentName->desc->useMethodName
     */
    public HashMap<String, AvoidMistakeMethod> methods = new HashMap<String, AvoidMistakeMethod>();

    public List<String> classNames = new ArrayList<>();

    public void init() {
        for (String className : fields.keySet()) {
            classNames.add(className);
        }
        for (String className : methods.keySet()) {
            classNames.add(className);
        }
    }


    public boolean matchClassName(String fullPath) {

        for (String str : classNames) {
            if (fullPath.equals(str)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "AvoidMistakesExtend{" +
                "runVariant=" + runVariant +
                ", fields=" + fields +
                ", methods=" + methods +
                '}';
    }
}