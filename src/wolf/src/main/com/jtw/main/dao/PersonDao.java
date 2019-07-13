package com.jtw.main.dao;

import org.springframework.stereotype.Repository;

@Repository
public interface PersonDao {
    public String getName(int id);

    public String getPassword(String username);
}
