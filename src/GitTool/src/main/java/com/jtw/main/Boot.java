package com.jtw.main;

import com.jtw.main.po.Branch;
import com.jtw.main.service.Command;
import com.jtw.main.service.DosCommand;
import com.jtw.main.service.ShellCommand;
import com.jtw.main.service.email.EmailServer;
import com.jtw.main.service.email.SMTPEmail;
import org.apache.poi.hssf.model.Workbook;

import javax.mail.Session;
import javax.mail.internet.AddressException;
import java.io.*;
import java.util.List;
import java.util.Properties;

public class Boot
{

    private static final long ONE_DAY = 60 * 60 * 24 * 1000L;

    private static final String shellCmd = "";

    private static final String DosCmd = "";

    private static final String OS_NAME = "os.name";

    private String configPath;

    private long cyclePerid;

    private EmailServer emailServer;

    private static final String USEAGE = "UseAge:\n"
            +"  -f path of properties";

    public static void main(String[] args) throws FileNotFoundException, AddressException {
        Boot boot = new Boot();
        boot.init(args);
        boot.start();
    }

    public void init(String[] args) throws FileNotFoundException, AddressException {
        //1.检查输入的参数是否有效
        if (args.length < 2 || args.length ==0)
        {
            System.out.println(USEAGE);
            System.exit(1);
        }
        if (!"-f".equals(args[0]))
        {
            System.out.println(USEAGE);
            System.exit(1);
        }
        configPath = args[1];
        File configFile = new File(configPath);
        if (!configFile.exists())
        {
            throw new FileNotFoundException("config file path is not exists!");
        }
        //2.初始化配置信息
        Properties properties = loadProperties();
        Object cyclePeriodValue = properties.get("cycle.period");
        if (cyclePeriodValue == null)
        {
            cyclePerid = 3L;
        }
        else
        {
            cyclePerid = Long.parseLong((String)cyclePeriodValue);
        }
        emailServer = new SMTPEmail(properties);
    }

    public void start()
    {
        Command command = null;
        String env = System.getProperty(OS_NAME);
        List<String> results;
        List<Branch> branches;
        while (true)
        {
            try
            {
                if (env.toLowerCase().contains("windows"))
                {
                    command = new DosCommand();
                }
                else
                {
                    command = new ShellCommand();
                }
                results = command.exeCmd("");
                //解析数据
                branches = parseResults(results);
                //生成数据表格
                generateExcel(branches);
                //发送电子邮件
                emailServer.send();
                Thread.currentThread().sleep(cyclePerid * ONE_DAY);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void generateExcel(List<Branch> branches)
    {

    }

    private List<Branch> parseResults(List<String> results)
    {

        return null;
    }

    private Properties loadProperties()
    {
        Properties properties = new Properties();
        InputStream in = null;
        try
        {
            in = new FileInputStream(configPath);
            properties.load(in);
            return properties;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (null != in)
            {
                try
                {
                    in.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return properties;
    }

}
