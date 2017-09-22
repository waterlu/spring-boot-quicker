package cn.lu.boot.quicker.dto;

import cn.lu.boot.quicker.entity.ProjectDependecy;

import java.util.List;

/**
 * Created by lutiehua on 2017/9/22.
 */
public class ProjectInfoDTO {

    private String groupId;

    private String artifactId;

    private String version;

    private String name;

    private String description;

    private String javaVersion;

    private String springBootVersion;

    private List<ProjectDependecy> dependencies;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpringBootVersion() {
        return springBootVersion;
    }

    public void setSpringBootVersion(String springBootVersion) {
        this.springBootVersion = springBootVersion;
    }

    public String getJavaVersion() {
        return javaVersion;
    }

    public void setJavaVersion(String javaVersion) {
        this.javaVersion = javaVersion;
    }

    public List<ProjectDependecy> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<ProjectDependecy> dependencies) {
        this.dependencies = dependencies;
    }
}
