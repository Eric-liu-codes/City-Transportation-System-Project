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
    private static final String CREATE_ENTITY_SQL = "INSERT INTO AccidentReport (id, date, description, person_id, vehicle_id) VALUES (?, ?, ?, ?, ?)";
    private static final String GET_ENTITY_BY_ID_SQL = "SELECT * FROM AccidentReport WHERE id = ?";
    private static final String UPDATE_ENTITY_SQL = "UPDATE AccidentReport SET date = ?, description = ?, person_id = ?, vehicle_id = ? WHERE id = ?";
    private static final String DELETE_ENTITY_SQL = "DELETE FROM AccidentReport WHERE id = ?";
    private static final String GET_ENTITY_BY_DATE_SQL = "SELECT * FROM AccidentReport WHERE date = ?";
    private static final String GET_ENTITY_BY_VEHICLE_ID_SQL = "SELECT * FROM AccidentReport WHERE vehicle_id = ?";
    private static final String GET_ALL_ENTITIES_SQL = "SELECT * FROM AccidentReport";

    @Override
    public AccidentReport createEntity(AccidentReport entity){
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(CREATE_ENTITY_SQL);
            statement.setLong(1, entity.getId());
            statement.setDate(2, entity.getDate());
            statement.setString(3, entity.getDescription());
            statement.setLong(4, entity.getPersonId());
            statement.setLong(5, entity.getVehicleId());
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
    public AccidentReport getEntityById(long id){
        PreparedStatement statement = null;
        Connection connection = null;
        AccidentReport accidentReport = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(GET_ENTITY_BY_ID_SQL);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            accidentReport = resultSetToObject(resultSet);
        } catch (Exception e) {
            LOGGER.info(e);
        } finally {
            closeResource(statement);
            closeResource(resultSet);
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return accidentReport;
    }

    @Override
    public void updateEntity(AccidentReport entity){
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(UPDATE_ENTITY_SQL);
            statement.setDate(1, entity.getDate());
            statement.setString(2, entity.getDescription());
            statement.setLong(3, entity.getPersonId());
            statement.setLong(4, entity.getVehicleId());
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
    public AccidentReport getAccidentReportByDate(Date date){
        PreparedStatement statement = null;
        Connection connection = null;
        AccidentReport accidentReport = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(GET_ENTITY_BY_DATE_SQL);
            statement.setDate(1, date);
            resultSet = statement.executeQuery();
            accidentReport = resultSetToObject(resultSet);
        } catch (Exception e){
            LOGGER.info(e);
        } finally {
            closeResource(statement);
            closeResource(resultSet);
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return accidentReport;
    }



    @Override
    public AccidentReport getAccidentReportByVehicleId(long vehicleId){
        PreparedStatement statement = null;
        Connection connection = null;
        AccidentReport accidentReport = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(GET_ENTITY_BY_VEHICLE_ID_SQL);
            statement.setLong(1, vehicleId);
            resultSet = statement.executeQuery();
            accidentReport = resultSetToObject(resultSet);
        } catch (Exception e){
            LOGGER.info(e);
        } finally {
            closeResource(statement);
            closeResource(resultSet);
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return accidentReport;
    }

    @Override
    public List<AccidentReport> getAllAccidentReports(){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<AccidentReport> accidentReports = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(GET_ALL_ENTITIES_SQL);
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
                closeResource(statement);
                closeResource(resultSet);
                ConnectionPool.getInstance().releaseConnection(connection);
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