package cn.lu.boot.quicker.core.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lutiehua on 2017/9/26.
 */
public class JavaField {

    private String scope;

    private String type;

    private String name;

    private String remark;

    private List<JavaAnnotation> annotations = new ArrayList<>();

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<JavaAnnotation> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<JavaAnnotation> annotations) {
        this.annotations = annotations;
    }
}
