package com.java.pluginExtends;

/**
 * 根据ClassName 为 key
 * 对应多个规则
 */
public class AvoidMistakeField {
    public boolean isEnd = false;
    public String fieldName;
    public String fieldValue;
    public String className;

    @Override
    public String toString() {
        return "AvoidMistakeField{" +
                "isEnd=" + isEnd +
                ", fieldName='" + fieldName + '\'' +
                ", fieldValue='" + fieldValue + '\'' +
                ", className='" + className + '\'' +
                '}';
    }
}
