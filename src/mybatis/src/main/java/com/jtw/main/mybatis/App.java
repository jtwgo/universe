package com.jtw.main.mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.jtw.main.mybatis.dao.UserDao;
import com.jtw.main.mybatis.po.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class App
{
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    private SqlSessionFactory sessionFactory;

    private SqlSession session;
    @Before
    public void prepare()
    {
        LOGGER.debug("开始mybatis实验");

        try
        {
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            session = sessionFactory.openSession();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


    }

    @After
    public void destory()
    {
        sessionFactory = null;
        LOGGER.debug("结束mybatis实验");
    }

    @Test
    public void query()
    {
        UserDao userDao = session.getMapper(UserDao.class);
        LOGGER.error(userDao.queryAllUsers().toString());
//        session.clearCache();
        LOGGER.error(userDao.queryAllUsers().toString());
        session.close();
    }

    @Test
    public void insert()
    {
        session = sessionFactory.openSession();
        UserDao userDao = session.getMapper(UserDao.class);
        User user = new User();
        user.setId(23);
        user.setAge(10);
        user.setEmail("test@fds.com");
        user.setPassword("123");
        user.setUserName("黎明");
        userDao.createUser(user);
        session.commit();

    }

    @Test
    public void delete()
    {
        session = sessionFactory.openSession();
        UserDao userDao = session.getMapper(UserDao.class);
        userDao.deleteUser(3);
        session.commit();
        session.close();

    }
    @Test
    public void rollback()
    {
        session = sessionFactory.openSession();
        UserDao userDao = session.getMapper(UserDao.class);
        User user = new User();
        user.setId(5);
        user.setAge(10);
        user.setEmail("aa@fds.com");
        user.setPassword("jfdkl");
        user.setUserName("黎明");
        userDao.createUser(user);
        session.commit();
        session.rollback();
    }

    @Test
    public void selectOne()
    {
        session = sessionFactory.openSession();
        String id = sessionFactory.getConfiguration().getDatabaseId();
        System.out.println(id);
//        System.out.println(session.selectList(UserDao.class.getCanonicalName() + ".queryAllUsers"));
    }

    @Test
    public void countCustomName()
    {
        SqlSession sqlSession = sessionFactory.openSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        int countNum = userDao.countCustomName("黎明");
        System.out.println(countNum);
    }
}
