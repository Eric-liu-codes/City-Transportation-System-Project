package com.solvd.citytransportationsystemproject.dao.mysql;

import com.solvd.citytransportationsystemproject.dao.IRouteDao;
import com.solvd.citytransportationsystemproject.models.Route;
import com.solvd.citytransportationsystemproject.utils.ConnectionPool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RouteDao extends MySQLDao<Route> implements IRouteDao {
    private static final Logger LOGGER = LogManager.getLogger(RouteDao.class);

    @Override
    public Route createEntity(Route entity) {
        String sql = "INSERT INTO Route (id, name, vehicle_id) VALUES (?, ?, ?)";
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(1, entity.getId());
            statement.setString(2, entity.getName());
            statement.setLong(3, entity.getVehicleId());
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
    public Route getEntityById(long id) {
        String sql = "SELECT * FROM Route WHERE id = ?";
        PreparedStatement statement = null;
        Connection connection = null;
        Route route = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            route = resultSetToObject(resultSet);
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
        return route;
    }


    @Override
    public void updateEntity(Route entity){
        String sql = "UPDATE Route SET name = ?, vehicle_id = ? WHERE id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, entity.getName());
            statement.setLong(2, entity.getVehicleId());
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
    public void deleteEntity(long id) {
        String sql = "DELETE FROM Route WHERE id = ?";
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
    public Route getRouteByName(String name){
        String sql = "SELECT * FROM Route WHERE name = ?";
        PreparedStatement statement = null;
        Connection connection = null;
        Route route = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            route = resultSetToObject(resultSet);
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
        return route;
    }

    @Override
    public Route getRouteByVehicleId(long vehicleId){
        String sql = "SELECT * FROM Route WHERE vehicle_id = ?";
        PreparedStatement statement = null;
        Connection connection = null;
        Route route = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(1, vehicleId);
            resultSet = statement.executeQuery();
            route = resultSetToObject(resultSet);
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
        return route;
    }

    @Override
    public List<Route> getAllRoutes(){
        String sql = "SELECT * FROM Route";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Route> routes = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                Route route = new Route();
                route.setId(resultSet.getLong("id"));
                route.setName(resultSet.getString("name"));
                route.setVehicleId(resultSet.getLong("vehicle_id"));
                routes.add(route);
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
        return routes;
    }

    @Override
    protected Route resultSetToObject(ResultSet resultSet) {
        Route route = null;
        try{
            while (resultSet.next()){
                route = new Route();
                route.setId(resultSet.getLong("id"));
                route.setName(resultSet.getString("name"));
                route.setVehicleId(resultSet.getLong("vehicle_id"));
            }
        } catch (Exception e) {
            LOGGER.info(e);
        }
        return route;
    }



}


