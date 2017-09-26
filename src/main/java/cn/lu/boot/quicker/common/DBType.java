package cn.lu.boot.quicker.common;

public enum DBType {
	Oracle("oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@%s:%d:%s"),
    MySQL("com.mysql.jdbc.Driver", "jdbc:mysql://%s:%d/%s?useSSL=false&characterEncoding=utf-8"),
    PostgreSQL("org.postgresql.Driver", "jdbc:postgresql://%s:%d/%s");

	private final String driverClass;
    private final String connectionUrlPattern;

    DBType(String driverClass, String connectionUrlPattern) {
        this.driverClass = driverClass;
        this.connectionUrlPattern = connectionUrlPattern;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public String getConnectionUrlPattern() {
        return connectionUrlPattern;
    }
}