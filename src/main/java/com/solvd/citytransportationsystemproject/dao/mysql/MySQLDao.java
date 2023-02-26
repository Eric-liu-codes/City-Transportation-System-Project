package com.solvd.citytransportationsystemproject.dao.mysql;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;

abstract public class MySQLDao<T> {

    private static final Logger LOGGER = LogManager.getLogger(MySQLDao.class);
    protected abstract T resultSetToObject(ResultSet resultSet);

    protected void closeResource(AutoCloseable resource){
        if (resource != null){
            try {
                resource.close();
            } catch (Exception e) {
                LOGGER.info(e);
            }
        }
    }
}