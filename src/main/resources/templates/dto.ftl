package ${classPackage};

<#list imports as import>
import ${import.name};
</#list>

/**
 * ${classRemark}
 *
 * Create by ${author} on ${date}
 *
 */
public class ${className}DTO {

    /**
     * 跟踪日志用
     *
     */
    private String traceID;

    /**
     * 请求来源
     *
     */
    private String callSystemID;
    <#list fields as field>

    /**
     * ${field.remark}
     *
     */
    <#list field.annotations as annotation>
    @${annotation.show}
    </#list>
    private ${field.type} ${field.name};
    </#list>

    public String getTraceID() {
        return this.traceID;
    }

    public void setTraceID(String traceID) {
        this.traceID = traceID;
    }

    public String getCallSystemID() {
        return this.callSystemID;
    }

    public void setCallSystemID(String callSystemID) {
        this.callSystemID = callSystemID;
    }
    <#list fields as field>

    public ${field.type} get${field.name?cap_first}() {
        return ${field.name};
    }

    public void set${field.name?cap_first}(${field.type} ${field.name}) {
        this.${field.name} = ${field.name};
    }
    </#list>

}