package com.solvd.citytransportationsystemproject.dao.mysql;

import com.solvd.citytransportationsystemproject.dao.ITrainDao;
import com.solvd.citytransportationsystemproject.models.Train;
import com.solvd.citytransportationsystemproject.utils.ConnectionPool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TrainDao extends MySQLDao<Train> implements ITrainDao {
    private static final Logger LOGGER = LogManager.getLogger(TrainDao.class);

    @Override
    public Train createEntity(Train entity){
        String sql = "INSERT INTO Train (id, vehicle_id, train_headcode) VALUES (?, ?, ?)";
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(1, entity.getId());
            statement.setLong(2, entity.getVehicleId());
            statement.setString(3, entity.getTrainHeadcode());
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
    public Train getEntityById(long id){
        String sql = "SELECT * FROM Train WHERE id = ?";
        PreparedStatement statement = null;
        Connection connection = null;
        Train train = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            train = resultSetToObject(resultSet);
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
        return train;
    }


    @Override
    public void updateEntity(Train entity){
        String sql = "UPDATE Train SET vehicle_id = ?, train_headcode = ? WHERE id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(1, entity.getVehicleId());
            statement.setString(2, entity.getTrainHeadcode());
            statement.setLong(3, entity.getId());
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
        String sql = "DELETE FROM Train WHERE id = ?";
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
    public Train getTrainByTrainHeadcode(String trainHeadcode){
        String sql = "SELECT * FROM Train WHERE train_headcode = ?";
        PreparedStatement statement = null;
        Connection connection = null;
        Train train = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, trainHeadcode);
            resultSet = statement.executeQuery();
            train = resultSetToObject(resultSet);
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
        return train;
    }


    @Override
    public List<Train> getAllTrains(){
        String sql = "SELECT * FROM Train";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Train> trains = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                Train train = new Train();
                train.setId(resultSet.getLong("id"));
                train.setVehicleId(resultSet.getLong("vehicle_id"));
                train.setTrainHeadcode(resultSet.getString("train_headcode"));
                trains.add(train);
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
        return trains;
    }


    @Override
    protected Train resultSetToObject(ResultSet resultSet) {
        Train train = null;
        try{
            while (resultSet.next()){
                train = new Train();
                train.setId(resultSet.getLong("id"));
                train.setVehicleId(resultSet.getLong("vehicle_id"));
                train.setTrainHeadcode(resultSet.getString("train_headcode"));
            }
        } catch (Exception e) {
            LOGGER.info(e);
        }
        return train;
    }



}
