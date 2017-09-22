package cn.lu.boot.quicker.dto;

/**
 * Created by lutiehua on 2017/9/22.
 */
public class DatabaseInfoDTO {

    private String dbType;

    private String dbIP;

    private Integer dbPort;

    private String dbName;

    private String dbUsername;

    private String dbPassword;

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getDbIP() {
        return dbIP;
    }

    public void setDbIP(String dbIP) {
        this.dbIP = dbIP;
    }

    public Integer getDbPort() {
        return dbPort;
    }

    public void setDbPort(Integer dbPort) {
        this.dbPort = dbPort;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public void setDbUsername(String dbUsername) {
        this.dbUsername = dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }
}

