package com.jtw.csfrm.Test;

public class UrlEntity
{

    private enum Driver
    {
        mysql("mysql", "jdbc:mysql"),
        oracle("oracle", "odbc:oracle");
        private String name;
        private String driver;
        private Driver(String name, String driver)
        {
            this.name = name;
            this.driver = driver;
        }
        public String getDriver()
        {
            return this.driver;
        }

    }
    private String defaultDriver = "mysql";

    private String defaultIp = "localhost";

    private int defaultPort = 3306;

    private String driver;

    private String ip;

    private int port;

    private String dataBase;

    private String userName;

    private String password;

    public UrlEntity(String userName, String password, String dataBase)
    {
        this.ip = this.defaultIp;
        this.driver = this.defaultDriver;
        this.port = this.defaultPort;
        this.userName = userName;
        this.password = password;
        this.dataBase = dataBase;
    }

    public UrlEntity(String driver, String ip, int port, String dataBase, String userName, String password)
    {
        this(userName,password,dataBase);
        this.driver = driver;
        this.ip = ip;
        this.port = port;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDataBase() {
        return dataBase;
    }

    public void setDataBase(String dataBase)
    {
        this.dataBase = dataBase;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(Driver.mysql.getDriver()).append("://")
                .append(ip).append(":").append(port).append("/")
                .append(dataBase).append("?").append("user=")
                .append(userName).append("&").append("password=").append(password)
                .append("&useSSL=true");
        return sb.toString();
    }
}
