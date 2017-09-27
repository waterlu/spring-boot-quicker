package cn.lu.boot.quicker.core;

import cn.lu.boot.quicker.common.DBDataType;
import cn.lu.boot.quicker.common.DBType;
import cn.lu.boot.quicker.dto.ClassInfoDTO;
import cn.lu.boot.quicker.dto.DatabaseInfoDTO;
import cn.lu.boot.quicker.entity.DBField;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by lutiehua on 2017/9/27.
 */
public abstract class GeneratedJavaDatabaseClass extends GeneratedJavaClassFile {

    /**
     * 数据库连接
     */
    protected DatabaseInfoDTO databaseInfo;

    /**
     * 数据库表名
     */
    protected String tableName;

    /**
     * 构造函数
     *
     * @param classInfo
     */
    public GeneratedJavaDatabaseClass(ClassInfoDTO classInfo) {
        super(classInfo);
    }

    public GeneratedJavaDatabaseClass(ClassInfoDTO classInfo, DatabaseInfoDTO databaseInfo) {
        super(classInfo);
        this.tableName = classInfo.getClassName();
        this.databaseInfo = databaseInfo;
    }

    protected boolean isCharType(int type) {
        if (type == DBDataType.CHAR) {
            return true;
        }

        if (type == DBDataType.VARCHAR) {
            return true;
        }

        if (type == DBDataType.TEXT) {
            return true;
        }

        return false;
    }

    protected boolean isIntegerType(int type)
    {
        if (type == DBDataType.INTEGER) {
            return true;
        }

        if (type == DBDataType.TINYINT) {
            return true;
        }

        if (type == DBDataType.SMALLINT) {
            return true;
        }

        if (type == DBDataType.BIGINT) {
            return true;
        }

        return false;
    }

    /**
     * 获取数据库连接
     *
     * @return
     * @throws Exception
     */
    protected Connection getConnection() throws Exception {
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

    protected String getConnectionURL() throws ClassNotFoundException {
        DBType dbType = DBType.valueOf(databaseInfo.getDbType());
        String connectionRUL = String.format(dbType.getConnectionUrlPattern(), databaseInfo.getDbIP(),
                databaseInfo.getDbPort(), databaseInfo.getDbName());
        return connectionRUL;
    }

    /**
     * 读取表结构
     *
     * @return
     * @throws Exception
     */
    protected List<DBField> getTableColumns() throws Exception {
        Connection connection = getConnection();
        DatabaseMetaData metaData = connection.getMetaData();
        String primaryKeyFiledName = getTablePrimaryKey(metaData, tableName);
        ResultSet resultSet = metaData.getColumns(null, null, tableName, null);
        List<DBField> fieldList = new ArrayList<>();
        while (resultSet.next()) {
            DBField field = new DBField();
            field.setColumnName(resultSet.getString("COLUMN_NAME"));
            field.setDataType(resultSet.getInt("DATA_TYPE"));
            field.setTypeName(resultSet.getString("TYPE_NAME"));
            field.setColumnSize(resultSet.getInt("COLUMN_SIZE"));
            field.setDecimalDigits(resultSet.getInt("DECIMAL_DIGITS"));
            field.setNullable(resultSet.getString("IS_NULLABLE").equalsIgnoreCase("YES"));
            field.setRemarks(resultSet.getString("REMARKS"));
            field.setColumnDef(resultSet.getString("COLUMN_DEF"));
            field.setCharOctetLength(resultSet.getInt("CHAR_OCTET_LENGTH"));

            if (null != primaryKeyFiledName) {
                if (field.getColumnName().equalsIgnoreCase(primaryKeyFiledName)) {
                    field.setPrimaryKey(true);
                } else {
                    field.setPrimaryKey(false);
                }
            }
            fieldList.add(field);
        }

        return fieldList;
    }

    /**
     * 读取主键
     *
     * @param metaData
     * @param tableName
     * @return
     * @throws Exception
     */
    protected String getTablePrimaryKey(DatabaseMetaData metaData, String tableName) throws Exception {
        ResultSet rs = metaData.getPrimaryKeys(null, null, tableName);
        while (rs.next()) {
            return rs.getString("COLUMN_NAME");
        }

        return null;
    }
}
