package com.solvd.citytransportationsystemproject.myBatisRunner;

import java.io.IOException;
import java.io.Reader;
import java.util.logging.Logger;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
public class MyBatisRunner {
    private static final Logger LOGGER = Logger.getLogger(MyBatisRunner.class.getName());
    private static SqlSessionFactory sqlSessionFactory;
    static {
        try {
            String resource = "/TransportionSystem/src/main/resources/config.xml";
            Reader reader = Resources.getResourceAsReader(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            LOGGER.info(String.valueOf(e));
        }
    }
    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }
}
