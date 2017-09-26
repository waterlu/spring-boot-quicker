package cn.lu.boot.quicker.api;

import cn.lu.boot.quicker.common.ResponseResult;
import cn.lu.boot.quicker.dto.DatabaseInfoDTO;
import cn.lu.boot.quicker.entity.DBField;
import cn.lu.boot.quicker.entity.DBTable;
import cn.lu.boot.quicker.common.DBType;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by lutiehua on 2017/9/22.
 */
@RestController
@RequestMapping("/api/database")
public class DatabaseController {

    @RequestMapping(value = "/connect", method = RequestMethod.POST)
    public ResponseResult connect(@RequestBody DatabaseInfoDTO databaseInfoDTO) throws Exception {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(ResponseResult.OK);
        responseResult.setData(getConnection(databaseInfoDTO).getSchema());
        return responseResult;
    }

    @RequestMapping(value = "/tables", method = RequestMethod.POST)
    public ResponseResult getTables(@RequestBody DatabaseInfoDTO databaseInfoDTO) throws Exception {
        List<DBTable> tableList = getTableNames(databaseInfoDTO);
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(ResponseResult.OK);
        responseResult.setData(tableList);
        return responseResult;
    }

    @RequestMapping(value = "/tables/{tableName}", method = RequestMethod.POST)
    public ResponseResult getTableColumns(@PathVariable String tableName, @RequestBody DatabaseInfoDTO databaseInfoDTO) throws Exception {
        List<DBField> fieldList = getTableColumns(databaseInfoDTO, tableName);
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(ResponseResult.OK);
        responseResult.setData(fieldList);
        return responseResult;
    }

    private Connection getConnection(DatabaseInfoDTO databaseInfoDTO) throws Exception {
        DriverManager.setLoginTimeout(3);
        DBType dbType = DBType.valueOf(databaseInfoDTO.getDbType());
        Class.forName(dbType.getDriverClass());
        String url = getConnectionURL(databaseInfoDTO);
        Properties props =new Properties();
        props.setProperty("user", databaseInfoDTO.getDbUsername());
        props.setProperty("password", databaseInfoDTO.getDbPassword());
        //设置可以获取remarks信息
        props.setProperty("remarks", "true");
        //设置可以获取tables remarks信息
        props.setProperty("useInformationSchema", "true");
        Connection connection = DriverManager.getConnection(url, props);
        return connection;
    }

    private String getConnectionURL(DatabaseInfoDTO databaseInfoDTO) throws ClassNotFoundException {
        DBType dbType = DBType.valueOf(databaseInfoDTO.getDbType());
        String connectionRUL = String.format(dbType.getConnectionUrlPattern(), databaseInfoDTO.getDbIP(),
                databaseInfoDTO.getDbPort(), databaseInfoDTO.getDbName());
        return connectionRUL;
    }

    private List<DBTable> getTableNames(DatabaseInfoDTO databaseInfoDTO) throws Exception {
        Connection connection = getConnection(databaseInfoDTO);
        List<DBTable> tables = new ArrayList<>();
        DatabaseMetaData metaData = connection.getMetaData();
        String[] types = { "TABLE", "VIEW" };
        ResultSet resultSet = metaData.getTables(null, databaseInfoDTO.getDbUsername().toUpperCase(), null, types);
        while (resultSet.next()) {
            DBTable table = new DBTable();
            table.setName(resultSet.getString("TABLE_NAME"));
            table.setType(resultSet.getString("TABLE_TYPE"));
            table.setRemark(resultSet.getString("REMARKS"));
            tables.add(table);
        }
        return tables;
    }

    private List<DBField> getTableColumns(DatabaseInfoDTO databaseInfoDTO, String tableName) throws Exception {
        Connection connection = getConnection(databaseInfoDTO);
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
