package com.jtw.main.service.impl;

import com.jtw.main.dao.PersonDao;
import com.jtw.main.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PersonServiceImpl implements PersonService{
    @Autowired
    private PersonDao personDao;

    public String getName(int id) {

        return this.personDao.getName(id);
    }

    @Override
    public String getPassword(String username) {
        return this.personDao.getPassword(username);
    }
}
