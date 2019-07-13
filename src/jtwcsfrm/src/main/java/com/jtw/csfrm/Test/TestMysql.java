package com.jtw.csfrm.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.sql.*;
import java.util.List;

public class TestMysql
{
    private Connection connection = null;
    static
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public TestMysql(UrlEntity urlEntity) throws SQLException
    {
        this.connection = DriverManager.getConnection(urlEntity.getUrl());
    }

    public void createDataBase(String dataBaseName, Charset charset)
    {

    }

    public void executeSql(String sqlPath)
    {

        try
        {
            File sqlFile = new File(sqlPath);
            InputStream in = null;
            BufferedReader bufferedReader = null;
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(sqlFile)));
            String line = null;
            while ((line = bufferedReader.readLine())!=null)
            {
                PreparedStatement statement = connection.prepareStatement(line);
                statement.setInt(1,16);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next())
                {
                    Object object = resultSet.getObject(1);
                    System.out.println(object);
                    Object object2 = resultSet.getObject(2);
                    System.out.println(object2);
                    Object object3 = resultSet.getObject(3);
                    System.out.println(object3);
                }
            }


        }  catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void executeSql(List<String> sqlPaths)
    {
        for(String sqlPath:sqlPaths)
        {
            executeSql(sqlPath);
        }
    }
    public static void main(String[] args) throws SQLException
    {
        UrlEntity url = new UrlEntity("mysql","localhost",3306,"wolf","root","123456");
        System.out.println(url.getUrl());
        TestMysql testMysql = new TestMysql(url);
        testMysql.executeSql("d:\\test.sql");

    }
}
