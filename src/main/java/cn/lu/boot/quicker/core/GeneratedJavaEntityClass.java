package cn.lu.boot.quicker.core;

import cn.lu.boot.quicker.core.model.JavaClassModel;
import cn.lu.boot.quicker.core.model.JavaField;
import cn.lu.boot.quicker.core.model.JavaImport;
import cn.lu.boot.quicker.dto.ClassInfoDTO;
import cn.lu.boot.quicker.dto.DatabaseInfoDTO;
import cn.lu.boot.quicker.dto.PackageInfoDTO;
import cn.lu.boot.quicker.entity.DBField;
import cn.lu.boot.quicker.core.util.DBUtil;
import cn.lu.boot.quicker.core.util.DirUtil;

import java.util.*;

/**
 * Created by lutiehua on 2017/9/26.
 */
public class GeneratedJavaEntityClass extends GeneratedJavaDatabaseClass {

    /**
     * 类名
     */
    protected String className;

    /**
     * 包名
     */
    protected String packageName;

    public GeneratedJavaEntityClass(ClassInfoDTO classInfo) {
        super(classInfo);
    }

    public GeneratedJavaEntityClass(ClassInfoDTO classInfo, DatabaseInfoDTO databaseInfo) {
        super(classInfo, databaseInfo);
    }

    public GeneratedJavaEntityClass(ClassInfoDTO classInfo, DatabaseInfoDTO databaseInfo, PackageInfoDTO packageInfo) {
        super(classInfo, databaseInfo);
        this.className = DBUtil.toJavaClassName(classBaseName);
        String javaFileName = className + ".java";
        this.packageName = packageInfo.getBasePackage() + "." + packageInfo.getEntityPackage();
        String packageDir = DirUtil.package2Dir(packageName);
        this.fileName = String.format("%s/%s/%s/%s/%s", rootDir, projectName, packageInfo.getJavaDir(), packageDir, javaFileName);

        this.classRemark += "实体类";
    }

    @Override
    public String getTemplateName() {
        return "entity.ftl";
    }

    @Override
    public boolean parse() throws Exception {
        model = new JavaClassModel();
        model.setAuthor(classAuthor);
        model.setDate(classDate);
        model.setClassName(className);
        model.setClassRemark(classRemark);
        model.setClassPackage(packageName);

        List<JavaField> javaFieldList = new ArrayList<>();
        List<DBField> fieldList = getTableColumns();
        for (DBField field : fieldList) {
            JavaField javaField = new JavaField();
            javaField.setName(DBUtil.toJavaPropertyName(field.getColumnName()));
            String fieldType = DBUtil.toJavaType(field.getTypeName());
            fieldType = super.parseJavaImportType(fieldType);
            javaField.setType(fieldType);
            javaField.setRemark(field.getRemarks());
            javaFieldList.add(javaField);
        }

        model.setFields(javaFieldList);

        if (importPackage.size() > 0) {
            List<JavaImport> importList = new ArrayList<>();
            for (Map.Entry<String, String> entry : importPackage.entrySet()) {
                JavaImport javaImport = new JavaImport();
                javaImport.setName(entry.getKey());
                importList.add(javaImport);
            }

            model.setImports(importList);
        }

        return true;
    }

}
