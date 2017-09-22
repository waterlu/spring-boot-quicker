package cn.lu.boot.quicker.api;

import cn.lu.boot.quicker.common.ResponseResult;
import cn.lu.boot.quicker.dto.DatabaseInfoDTO;
import cn.lu.boot.quicker.entity.Table;
import cn.lu.boot.quicker.util.DBType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
        List<Table> tableList = getTableNames(databaseInfoDTO);
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(ResponseResult.OK);
        responseResult.setData(tableList);
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

    private List<Table> getTableNames(DatabaseInfoDTO databaseInfoDTO) throws Exception {
        Connection connection = getConnection(databaseInfoDTO);
        List<Table> tables = new ArrayList<>();
        DatabaseMetaData metaData = connection.getMetaData();
        String[] types = { "TABLE", "VIEW" };
        ResultSet resultSet = metaData.getTables(null, databaseInfoDTO.getDbUsername().toUpperCase(), null, types);
        while (resultSet.next()) {
            Table table = new Table();
            table.setName(resultSet.getString("TABLE_NAME"));
            table.setType(resultSet.getString("TABLE_TYPE"));
            table.setRemark(resultSet.getString("REMARKS"));
            tables.add(table);
        }
        return tables;
    }
}
