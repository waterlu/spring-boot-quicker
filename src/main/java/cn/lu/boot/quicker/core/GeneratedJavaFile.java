package cn.lu.boot.quicker.core;

import cn.lu.boot.quicker.core.model.JavaClassModel;
import cn.lu.boot.quicker.core.model.JavaField;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lutiehua on 2017/9/26.
 */
public abstract class GeneratedJavaFile extends GeneratedFile {

    /**
     * 需要导入的包
     *
     */
    protected Map<String, String> importPackage = new HashMap<>();

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

    /**
     * 生成Get和Set方法
     *
     * @param model
     */
    public void generateGetterAndSetter(JavaClassModel model) {
        for (JavaField javaField : model.getFields()) {

        }
    }

}