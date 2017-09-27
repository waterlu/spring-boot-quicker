package cn.lu.boot.quicker.core;

import cn.lu.boot.quicker.common.DBDataType;
import cn.lu.boot.quicker.core.model.JavaAnnotation;
import cn.lu.boot.quicker.core.model.JavaClassModel;
import cn.lu.boot.quicker.core.model.JavaField;
import cn.lu.boot.quicker.core.model.JavaImport;
import cn.lu.boot.quicker.core.util.DBUtil;
import cn.lu.boot.quicker.core.util.DirUtil;
import cn.lu.boot.quicker.dto.ClassInfoDTO;
import cn.lu.boot.quicker.dto.DatabaseInfoDTO;
import cn.lu.boot.quicker.dto.PackageInfoDTO;
import cn.lu.boot.quicker.entity.DBField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lutiehua on 2017/9/26.
 */
public class GeneratedJavaDTOClass extends GeneratedJavaDatabaseClass {

    /**
     * 类名
     */
    protected String className;

    /**
     * 包名
     */
    protected String packageName;

    /**
     * 构造函数
     *
     * @param classInfo
     */
    public GeneratedJavaDTOClass(ClassInfoDTO classInfo) {
        super(classInfo);
    }

    public GeneratedJavaDTOClass(ClassInfoDTO classInfo, DatabaseInfoDTO databaseInfo) {
        super(classInfo, databaseInfo);
    }

    public GeneratedJavaDTOClass(ClassInfoDTO classInfo, DatabaseInfoDTO databaseInfo, PackageInfoDTO packageInfo) {
        super(classInfo, databaseInfo);
        this.className = DBUtil.toJavaClassName(classBaseName) + "DTO";
        String javaFileName = className + ".java";
        this.packageName = packageInfo.getBasePackage() + "." + packageInfo.getDtoPackage();
        String packageDir = DirUtil.package2Dir(packageName);
        this.fileName = String.format("%s/%s/%s/%s/%s", rootDir, projectName, packageInfo.getJavaDir(), packageDir, javaFileName);

        this.classRemark += "数据转换对象类";
    }

    @Override
    public String getTemplateName() {
        return "dto.ftl";
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

            if(!field.isNullable()) {
                JavaAnnotation annotation = new JavaAnnotation();
                String importName = super.parseJavaImportType("org.hibernate.validator.constraints.NotEmpty");
                annotation.setName(importName);
                javaField.getAnnotations().add(annotation);
            }

            if (super.isCharType(field.getDataType())) {
                JavaAnnotation annotation = new JavaAnnotation();
                String importName = super.parseJavaImportType("javax.validation.constraints.Size");
                annotation.setName(importName);
//                if (field.getDataType() == DBDataType.CHAR) {
//                    annotation.addProp("min", Integer.toString(field.getColumnSize()));
//                } else {
//                    annotation.addProp("min", "0");
//                }
                annotation.addProp("max", Integer.toString(field.getColumnSize()));
                javaField.getAnnotations().add(annotation);
            }

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
