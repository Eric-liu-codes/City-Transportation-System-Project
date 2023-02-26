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

    @Override
    public Driver createEntity(Driver entity){
        String sql = "INSERT INTO Driver (id, license_number, years_of_experience, person_id) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(1, entity.getId());
            statement.setString(2, entity.getLicenseNumber());
            statement.setInt(3, entity.getYearsOfExperience());
            statement.setLong(4, entity.getPersonId());
            statement.executeUpdate();
        } catch (Exception e) {
            LOGGER.info(e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    ConnectionPool.getInstance().releaseConnection(connection);
                }
            } catch (Exception e) {
                LOGGER.info(e);
            }
        }
        return entity;
    }


    @Override
    public Driver getEntityById(long id){
        String sql = "SELECT * FROM Driver WHERE id = ?";
        PreparedStatement statement = null;
        Connection connection = null;
        Driver driver = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            driver = resultSetToObject(resultSet);
        } catch (Exception e) {
            LOGGER.info(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    ConnectionPool.getInstance().releaseConnection(connection);
                }
            } catch (Exception e) {
                LOGGER.info(e);
            }
        }
        return driver;
    }


    @Override
    public void updateEntity(Driver entity){
        String sql = "UPDATE Driver SET license_number = ?, years_of_experience = ?, person_id = ? WHERE id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, entity.getLicenseNumber());
            statement.setInt(2, entity.getYearsOfExperience());
            statement.setLong(3, entity.getPersonId());
            statement.setLong(5, entity.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            LOGGER.info(e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    ConnectionPool.getInstance().releaseConnection(connection);
                }
            } catch (Exception e) {
                LOGGER.info(e);
            }
        }
    }

    @Override
    public void deleteEntity(long id){
        String sql = "DELETE FROM Driver WHERE id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            LOGGER.info(e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    ConnectionPool.getInstance().releaseConnection(connection);
                }
            } catch (Exception e) {
                LOGGER.info(e);
            }
        }
    }

    @Override
    public Driver getDriverByPersonId(long personId){
        String sql = "SELECT * FROM Driver WHERE person_id = ?";
        PreparedStatement statement = null;
        Connection connection = null;
        Driver driver = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(1, personId);
            resultSet = statement.executeQuery();
            driver = resultSetToObject(resultSet);
        } catch (Exception e){
            LOGGER.info(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    ConnectionPool.getInstance().releaseConnection(connection);
                }
            } catch (Exception e) {
                LOGGER.info(e);
            }
        }
        return driver;
    }

    @Override
    public List<Driver> getAllDrivers(){
        String sql = "SELECT * FROM Driver";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Driver> drivers = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
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
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    ConnectionPool.getInstance().releaseConnection(connection);
                }
            } catch (Exception e) {
                LOGGER.info(e);
            }
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
