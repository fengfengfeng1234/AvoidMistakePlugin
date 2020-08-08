package com.java.pluginExtends;

/**
 * 根据ClassName 为 key
 * 对应多个规则
 */
public class AvoidMistakeMethod {
    public boolean isStart = false;
    public boolean isEnd = false;
    public String methodParentName;
    public String useMethodName;
    public String desc;
    public String className;


    @Override
    public String toString() {
        return "AvoidMistakeMethod{" +
                "isStart=" + isStart +
                ", isEnd=" + isEnd +
                ", methodParentName='" + methodParentName + '\'' +
                ", useMethodName='" + useMethodName + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
