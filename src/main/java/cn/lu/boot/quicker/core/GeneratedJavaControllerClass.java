package cn.lu.boot.quicker.core;

import cn.lu.boot.quicker.core.model.DataModel;
import cn.lu.boot.quicker.dto.ClassInfoDTO;

/**
 * Created by lutiehua on 2017/9/26.
 */
public class GeneratedJavaControllerClass extends GeneratedJavaClassFile {

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 构造函数
     *
     * @param classInfo
     */
    public GeneratedJavaControllerClass(ClassInfoDTO classInfo) {
        super(classInfo);
    }

    @Override
    public String getTemplateName() {
        return "controller.ftl";
    }

    @Override
    public DataModel getDataModel() {
        return null;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public boolean parse() throws Exception {
        return false;
    }
}
