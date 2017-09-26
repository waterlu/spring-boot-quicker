package cn.lu.boot.quicker.dto;

import java.util.List;

/**
 * Created by lutiehua on 2017/9/22.
 */
public class GeneratedInfoDTO {

    /**
     * 项目基本信息
     */
    private ProjectInfoDTO projectInfo;

    /**
     * 项目依赖信息
     */
    private List<DependencyDTO> dependencies;

    /**
     * 数据库连接信息
     */
    private DatabaseInfoDTO databaseInfo;

    /**
     * 数据库表信息
     */
    private List<TableDTO> tables;

    /**
     * 程序包信息
     */
    private PackageInfoDTO packageInfo;

    public List<TableDTO> getTables() {
        return tables;
    }

    public void setTables(List<TableDTO> tables) {
        this.tables = tables;
    }

    public ProjectInfoDTO getProjectInfo() {
        return projectInfo;
    }

    public void setProjectInfo(ProjectInfoDTO projectInfo) {
        this.projectInfo = projectInfo;
    }

    public List<DependencyDTO> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<DependencyDTO> dependencies) {
        this.dependencies = dependencies;
    }

    public DatabaseInfoDTO getDatabaseInfo() {
        return databaseInfo;
    }

    public void setDatabaseInfo(DatabaseInfoDTO databaseInfo) {
        this.databaseInfo = databaseInfo;
    }

    public PackageInfoDTO getPackageInfo() {
        return packageInfo;
    }

    public void setPackageInfo(PackageInfoDTO packageInfo) {
        this.packageInfo = packageInfo;
    }
}
