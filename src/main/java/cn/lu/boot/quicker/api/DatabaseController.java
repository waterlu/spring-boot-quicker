package cn.lu.boot.quicker.api;

import cn.lu.boot.quicker.common.ResponseResult;
import cn.lu.boot.quicker.dto.DatabaseInfoDTO;
import cn.lu.boot.quicker.util.DBType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by lutiehua on 2017/9/22.
 */
@RestController
@RequestMapping("/api/database")
public class DatabaseController {

    @RequestMapping(value = "/connect", method = RequestMethod.POST)
    public ResponseResult connect(@RequestBody DatabaseInfoDTO databaseInfoDTO) throws Exception {
        DriverManager.setLoginTimeout(10);
        DBType dbType = DBType.valueOf(databaseInfoDTO.getDbType());
        Class.forName(dbType.getDriverClass());
        String url = getConnectionURL(databaseInfoDTO);
        Connection connection = DriverManager.getConnection(url, databaseInfoDTO.getDbUsername(),
                databaseInfoDTO.getDbPassword());
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(ResponseResult.OK);
        responseResult.setData(connection.getSchema());
        return responseResult;
    }

    private String getConnectionURL(DatabaseInfoDTO databaseInfoDTO) throws ClassNotFoundException {
        DBType dbType = DBType.valueOf(databaseInfoDTO.getDbType());
        String connectionRUL = String.format(dbType.getConnectionUrlPattern(), databaseInfoDTO.getDbIP(),
                databaseInfoDTO.getDbPort(), databaseInfoDTO.getDbName());
        return connectionRUL;
    }

}
