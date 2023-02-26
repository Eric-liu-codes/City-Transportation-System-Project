package com.solvd.citytransportationsystemproject.dao.mysql;

import com.solvd.citytransportationsystemproject.dao.IAccidentReportDao;
import com.solvd.citytransportationsystemproject.models.AccidentReport;
import com.solvd.citytransportationsystemproject.utils.ConnectionPool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AccidentReportDao extends MySQLDao<AccidentReport> implements IAccidentReportDao {
    private static final Logger LOGGER = LogManager.getLogger(AccidentReportDao.class);

    @Override
    public AccidentReport createEntity(AccidentReport entity){
        String sql = "INSERT INTO AccidentReport (id, date, description, person_id, vehicle_id) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(1, entity.getId());
            statement.setDate(2, entity.getDate());
            statement.setString(3, entity.getDescription());
            statement.setLong(4, entity.getPersonId());
            statement.setLong(5, entity.getVehicleId());
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
    public AccidentReport getEntityById(long id){
        String sql = "SELECT * FROM AccidentReport WHERE id = ?";
        PreparedStatement statement = null;
        Connection connection = null;
        AccidentReport accidentReport = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            accidentReport = resultSetToObject(resultSet);
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
        return accidentReport;
    }

    @Override
    public void updateEntity(AccidentReport entity){
        String sql = "UPDATE AccidentReport SET date = ?, description = ?, person_id = ?, vehicle_id = ? WHERE id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setDate(1, entity.getDate());
            statement.setString(2, entity.getDescription());
            statement.setLong(3, entity.getPersonId());
            statement.setLong(4, entity.getVehicleId());
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
        String sql = "DELETE FROM AccidentReport WHERE id = ?";
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
    public AccidentReport getAccidentReportByDate(Date date){
        String sql = "SELECT * FROM AccidentReport WHERE date = ?";
        PreparedStatement statement = null;
        Connection connection = null;
        AccidentReport accidentReport = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setDate(1, date);
            resultSet = statement.executeQuery();
            accidentReport = resultSetToObject(resultSet);
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
        return accidentReport;
    }



    @Override
    public AccidentReport getAccidentReportByVehicleId(long vehicleId){
        String sql = "SELECT * FROM AccidentReport WHERE vehicle_id = ?";
        PreparedStatement statement = null;
        Connection connection = null;
        AccidentReport accidentReport = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(1, vehicleId);
            resultSet = statement.executeQuery();
            accidentReport = resultSetToObject(resultSet);
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
        return accidentReport;
    }

    @Override
    public List<AccidentReport> getAllAccidentReports(){
        String sql = "SELECT * FROM AccidentReport";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<AccidentReport> accidentReports = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                AccidentReport accidentReport = new AccidentReport();
                accidentReport.setId(resultSet.getLong("id"));
                accidentReport.setDate(resultSet.getDate("date"));
                accidentReport.setDescription(resultSet.getString("description"));
                accidentReport.setPersonId(resultSet.getLong("person_id"));
                accidentReport.setVehicleId(resultSet.getLong("vehicle_id"));
                accidentReports.add(accidentReport);
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
        return accidentReports;
    }


    @Override
    protected AccidentReport resultSetToObject(ResultSet resultSet) {
        AccidentReport accidentReport = null;
        try{
            while (resultSet.next()){
                accidentReport = new AccidentReport();
                accidentReport.setId(resultSet.getLong("id"));
                accidentReport.setDate(resultSet.getDate("date"));
                accidentReport.setDescription(resultSet.getString("description"));
                accidentReport.setPersonId(resultSet.getLong("person_id"));
                accidentReport.setVehicleId(resultSet.getLong("vehicle_id"));
            }
        } catch (Exception e) {
            LOGGER.info(e);
        }
        return accidentReport;
    }
}
