package cn.lu.boot.quicker.core.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lutiehua on 2017/9/26.
 */
public class JavaClassModel extends DataModel {

    /**
     * 包名
     */
    protected String classPackage;

    /**
     * 类注释
     */
    protected String classRemark;

    /**
     * 类名
     */
    protected String className;

    /**
     * 需要引入的包
     */
    protected List<JavaImport> imports = new ArrayList<>();

    /**
     * 类成员变量
     */
    protected List<JavaField> fields = new ArrayList<>();

    /**
     * 类成员方法
     */
    protected List<JavaMethod> methods = new ArrayList<>();

    public String getClassPackage() {
        return classPackage;
    }

    public void setClassPackage(String classPackage) {
        this.classPackage = classPackage;
    }

    public String getClassRemark() {
        return classRemark;
    }

    public void setClassRemark(String classRemark) {
        this.classRemark = classRemark;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<JavaImport> getImports() {
        return imports;
    }

    public void setImports(List<JavaImport> imports) {
        this.imports = imports;
    }

    public List<JavaField> getFields() {
        return fields;
    }

    public void setFields(List<JavaField> fields) {
        this.fields = fields;
    }

    public List<JavaMethod> getMethods() {
        return methods;
    }

    public void setMethods(List<JavaMethod> methods) {
        this.methods = methods;
    }
}
