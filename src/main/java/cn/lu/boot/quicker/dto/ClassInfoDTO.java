package cn.lu.boot.quicker.dto;

/**
 * Created by lutiehua on 2017/9/27.
 */
public class ClassInfoDTO {

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 基础类名（可添加后缀）
     */
    private String className;

    /**
     * 类注释
     */
    private String classRemark;

    /**
     * 作者
     */
    private String classAuthor;

    /**
     * 日期
     */
    private String classDate;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassRemark() {
        return classRemark;
    }

    public void setClassRemark(String classRemark) {
        this.classRemark = classRemark;
    }

    public String getClassAuthor() {
        return classAuthor;
    }

    public void setClassAuthor(String classAuthor) {
        this.classAuthor = classAuthor;
    }

    public String getClassDate() {
        return classDate;
    }

    public void setClassDate(String classDate) {
        this.classDate = classDate;
    }
}
