package cn.lu.boot.quicker.core;

import cn.lu.boot.quicker.core.model.DataModel;
import cn.lu.boot.quicker.core.model.JavaClassModel;
import cn.lu.boot.quicker.dto.ClassInfoDTO;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lutiehua on 2017/9/26.
 */
public abstract class GeneratedJavaClassFile extends GeneratedFile {

    /**
     * 项目名称
     */
    protected String projectName;

    /**
     * 基础类名（可添加后缀）
     */
    protected String classBaseName;

    /**
     * 类注释
     */
    protected String classRemark;

    /**
     * 作者
     */
    protected String classAuthor;

    /**
     * 日期
     */
    protected String classDate;

    /**
     * 文件名（生成的类文件名，全路径）
     *
     */
    protected String fileName;

    /**
     * 数据（生成类文件的数据模型）
     *
     */
    protected JavaClassModel model;

    /**
     * 需要导入的包
     *
     */
    protected Map<String, String> importPackage = new HashMap<>();

    /**
     * 构造函数
     */
    public GeneratedJavaClassFile(ClassInfoDTO classInfo) {
        this.projectName = classInfo.getProjectName();
        this.classBaseName = classInfo.getClassName();
        this.classRemark = classInfo.getClassRemark();
        this.classAuthor = classInfo.getClassAuthor();
        this.classDate = classInfo.getClassDate();
    }

    @Override
    public DataModel getDataModel() {
        return model;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    /**
     * 解析依赖包
     *
     * @param type
     * @return
     */
    public String parseJavaImportType(String type) {
        if (importPackage.containsKey(type)) {
            return importPackage.get(type);
        } else {
            int pos = type.lastIndexOf(".");
            if (pos > 0) {
                String javaType = type.substring(pos + 1);
                importPackage.put(type, javaType);
                return javaType;
            } else {
                return type;
            }
        }
    }

}