package com.solvd.citytransportationsystemproject.dao.mysql;

import com.solvd.citytransportationsystemproject.dao.IDriverDao;
import com.solvd.citytransportationsystemproject.models.Driver;
import com.solvd.citytransportationsystemproject.utils.ConnectionPool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DriverDao extends MySQLDao<Driver> implements IDriverDao {
    private static final Logger LOGGER = LogManager.getLogger(DriverDao.class);
    private static final String CREATE_ENTITY_SQL = "INSERT INTO Driver (id, license_number, years_of_experience, person_id) VALUES (?, ?, ?, ?)";
    private static final String GET_ENTITY_BY_ID_SQL = "SELECT * FROM Driver WHERE id = ?";
    private static final String UPDATE_ENTITY_SQL = "UPDATE Driver SET license_number = ?, years_of_experience = ?, person_id = ? WHERE id = ?";
    private static final String DELETE_ENTITY_SQL = "DELETE FROM Driver WHERE id = ?";
    private static final String GET_ENTITY_BY_PERSON_ID_SQL = "SELECT * FROM Driver WHERE person_id = ?";
    private static final String GET_ALL_ENTITIES_SQL = "SELECT * FROM Driver";


    @Override
    public Driver createEntity(Driver entity){
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(CREATE_ENTITY_SQL);
            statement.setLong(1, entity.getId());
            statement.setString(2, entity.getLicenseNumber());
            statement.setInt(3, entity.getYearsOfExperience());
            statement.setLong(4, entity.getPersonId());
            statement.executeUpdate();
        } catch (Exception e) {
            LOGGER.info(e);
        } finally {
            closeResource(statement);
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return entity;
    }


    @Override
    public Driver getEntityById(long id){
        PreparedStatement statement = null;
        Connection connection = null;
        Driver driver = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(GET_ENTITY_BY_ID_SQL);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            driver = resultSetToObject(resultSet);
        } catch (Exception e) {
            LOGGER.info(e);
        } finally {
            closeResource(statement);
            closeResource(resultSet);
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return driver;
    }


    @Override
    public void updateEntity(Driver entity){
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(UPDATE_ENTITY_SQL);
            statement.setString(1, entity.getLicenseNumber());
            statement.setInt(2, entity.getYearsOfExperience());
            statement.setLong(3, entity.getPersonId());
            statement.setLong(5, entity.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            LOGGER.info(e);
        } finally {
            closeResource(statement);
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    @Override
    public void deleteEntity(long id){
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(DELETE_ENTITY_SQL);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            LOGGER.info(e);
        } finally {
            closeResource(statement);
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    @Override
    public Driver getDriverByPersonId(long personId){
        PreparedStatement statement = null;
        Connection connection = null;
        Driver driver = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(GET_ENTITY_BY_PERSON_ID_SQL);
            statement.setLong(1, personId);
            resultSet = statement.executeQuery();
            driver = resultSetToObject(resultSet);
        } catch (Exception e){
            LOGGER.info(e);
        } finally {
            closeResource(statement);
            closeResource(resultSet);
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return driver;
    }

    @Override
    public List<Driver> getAllDrivers(){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Driver> drivers = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(GET_ALL_ENTITIES_SQL);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                Driver driver = new Driver();
                driver.setId(resultSet.getLong("id"));
                driver.setLicenseNumber(resultSet.getString("license_number"));
                driver.setYearsOfExperience(resultSet.getInt("years_of_experience"));
                driver.setPersonId(resultSet.getLong("person_id"));
                drivers.add(driver);
            }
        } catch (Exception e) {
            LOGGER.info(e);
        } finally {
            closeResource(statement);
            closeResource(resultSet);
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return drivers;
    }


    @Override
    protected Driver resultSetToObject(ResultSet resultSet) {
        Driver driver = null;
        try {
            while (resultSet.next()) {
                driver = new Driver();
                driver.setId(resultSet.getLong("id"));
                driver.setLicenseNumber(resultSet.getString("license_number"));
                driver.setYearsOfExperience(resultSet.getInt("years_of_experience"));
                driver.setPersonId(resultSet.getLong("person_id"));
            }
        } catch (Exception e) {
            LOGGER.info(e);
        }
        return driver;
    }

}