package cn.lu.boot.quicker.core;

import cn.lu.boot.quicker.core.model.DataModel;
import cn.lu.boot.quicker.core.model.EntityClassModel;
import cn.lu.boot.quicker.core.model.JavaField;
import cn.lu.boot.quicker.core.model.JavaImport;
import cn.lu.boot.quicker.dto.DatabaseInfoDTO;
import cn.lu.boot.quicker.dto.PackageInfoDTO;
import cn.lu.boot.quicker.dto.TableDTO;
import cn.lu.boot.quicker.entity.DBField;
import cn.lu.boot.quicker.core.util.DBUtil;
import cn.lu.boot.quicker.common.DBType;
import cn.lu.boot.quicker.core.util.DirUtil;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lutiehua on 2017/9/26.
 */
public class GeneratedJavaEntityClass extends GeneratedJavaFile {

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 数据
     */
    private EntityClassModel model;

    /**
     * 数据库连接
     */
    private DatabaseInfoDTO databaseInfo;

    /**
     * 程序包信息
     */
    private PackageInfoDTO packageInfo;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 项目根目录
     */
    private String rootDir = "/tmp";

    public GeneratedJavaEntityClass(DatabaseInfoDTO databaseInfoDTO, TableDTO tableDTO, PackageInfoDTO packageInfoDTO) {
        this.databaseInfo = databaseInfoDTO;
        this.tableName = tableDTO.getName();
        this.remark = tableDTO.getRemark();
        this.packageInfo = packageInfoDTO;
        String javaFileName = tableDTO.getName().toLowerCase() + ".java";
        String packageName = packageInfo.getBasePackage() + "." + packageInfo.getEntityPackage();
        String packageDir = DirUtil.package2Dir(packageName);
        this.fileName = String.format("%s/%s/%s/%s/%s", rootDir, packageInfo.getProjectName(), packageInfo.getJavaDir(), packageDir, javaFileName);
    }

    @Override
    public String getTemplateName() {
        return "entity.ftl";
    }

    @Override
    public DataModel getDataModel() {
        return model;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public boolean parse() throws Exception {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(now);

        model = new EntityClassModel();
        model.setAuthor(packageInfo.getAuthor());
        model.setDate(date);
        model.setClassName(DBUtil.toJavaClassName(tableName));
        model.setClassRemark(remark);
        String packageName = packageInfo.getBasePackage() + "." + packageInfo.getEntityPackage();
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

    private Connection getConnection() throws Exception {
        DriverManager.setLoginTimeout(3);
        DBType dbType = DBType.valueOf(databaseInfo.getDbType());
        Class.forName(dbType.getDriverClass());
        String url = getConnectionURL();
        Properties props = new Properties();
        props.setProperty("user", databaseInfo.getDbUsername());
        props.setProperty("password", databaseInfo.getDbPassword());
        //设置可以获取remarks信息
        props.setProperty("remarks", "true");
        //设置可以获取tables remarks信息
        props.setProperty("useInformationSchema", "true");
        Connection connection = DriverManager.getConnection(url, props);
        return connection;
    }

    private String getConnectionURL() throws ClassNotFoundException {
        DBType dbType = DBType.valueOf(databaseInfo.getDbType());
        String connectionRUL = String.format(dbType.getConnectionUrlPattern(), databaseInfo.getDbIP(),
                databaseInfo.getDbPort(), databaseInfo.getDbName());
        return connectionRUL;
    }

    private List<DBField> getTableColumns() throws Exception {
        Connection connection = getConnection();
        DatabaseMetaData metaData = connection.getMetaData();
        String primaryKeyFiledName = getTablePrimaryKey(metaData, tableName);
        ResultSet resultSet = metaData.getColumns(null, null, tableName, null);
        List<DBField> fieldList = new ArrayList<>();
        while (resultSet.next()) {
            DBField field = new DBField();
            field.setColumnName(resultSet.getString("COLUMN_NAME"));
            field.setTypeName(resultSet.getString("TYPE_NAME"));
            field.setColumnSize(resultSet.getInt("COLUMN_SIZE"));
            field.setDecimalDigits(resultSet.getInt("DECIMAL_DIGITS"));
            field.setIsNullable(resultSet.getString("IS_NULLABLE"));
            field.setRemarks(resultSet.getString("REMARKS"));
            if (null != primaryKeyFiledName) {
                if (field.getColumnName().equalsIgnoreCase(primaryKeyFiledName)) {
                    field.setIsPrimaryKey("Y");
                } else {
                    field.setIsPrimaryKey("");
                }
            }
            fieldList.add(field);
        }

        return fieldList;
    }

    private String getTablePrimaryKey(DatabaseMetaData metaData, String tableName) throws Exception {
        ResultSet rs = metaData.getPrimaryKeys(null, null, tableName);
        while (rs.next()) {
            return rs.getString("COLUMN_NAME");
        }
        return null;
    }
}
