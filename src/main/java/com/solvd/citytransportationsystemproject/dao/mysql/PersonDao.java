package com.solvd.citytransportationsystemproject.dao.mysql;

import com.solvd.citytransportationsystemproject.dao.IPersonDao;
import com.solvd.citytransportationsystemproject.models.Person;
import com.solvd.citytransportationsystemproject.utils.ConnectionPool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PersonDao extends MySQLDao<Person> implements IPersonDao {
    private static final Logger LOGGER = LogManager.getLogger(PersonDao.class);
    private static final String CREATE_ENTITY_SQL = "INSERT INTO Person (id, first_name, last_name, address, phone_number, email) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String GET_ENTITY_BY_ID_SQL = "SELECT * FROM Person WHERE id = ?";
    private static final String UPDATE_ENTITY_SQL = "UPDATE Person SET first_name = ?, last_name = ?, address = ?, phone_number = ?, email = ? WHERE id = ?";
    private static final String DELETE_ENTITY_SQL = "DELETE FROM Person WHERE id = ?";
    private static final String GET_ENTITY_BY_LAST_NAME_SQL = "SELECT * FROM Person WHERE last_name = ?";
    private static final String GET_ALL_ENTITIES_SQL = "SELECT * FROM Person";

    @Override
    public Person createEntity(Person entity){
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(CREATE_ENTITY_SQL);
            statement.setLong(1, entity.getId());
            statement.setString(2, entity.getFirstName());
            statement.setString(3, entity.getLastName());
            statement.setString(4, entity.getAddress());
            statement.setString(5, entity.getPhoneNumber());
            statement.setString(6, entity.getEmail());
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
    public Person getEntityById(long id){
        PreparedStatement statement = null;
        Connection connection = null;
        Person person = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(GET_ENTITY_BY_ID_SQL);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            person = resultSetToObject(resultSet);
        } catch (Exception e) {
            LOGGER.info(e);
        } finally {
            closeResource(statement);
            closeResource(resultSet);
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return person;
    }


    @Override
    public void updateEntity(Person entity){
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(UPDATE_ENTITY_SQL);
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setString(3, entity.getAddress());
            statement.setString(4, entity.getPhoneNumber());
            statement.setString(5, entity.getEmail());
            statement.setLong(6, entity.getId());
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
    public Person getPersonByLastName(String lastName){
        PreparedStatement statement = null;
        Connection connection = null;
        Person person = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(GET_ENTITY_BY_LAST_NAME_SQL);
            statement.setString(1, lastName);
            resultSet = statement.executeQuery();
            person = resultSetToObject(resultSet);
        } catch (Exception e){
            LOGGER.info(e);
        } finally {
            closeResource(statement);
            closeResource(resultSet);
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return person;
    }

    @Override
    public List<Person> getAllPersons(){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Person> personList = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(GET_ALL_ENTITIES_SQL);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                Person person = new Person();
                person.setId(resultSet.getLong("id"));
                person.setFirstName(resultSet.getString("first_name"));
                person.setLastName(resultSet.getString("last_name"));
                person.setAddress(resultSet.getString("address"));
                person.setPhoneNumber(resultSet.getString("phone_number"));
                person.setEmail(resultSet.getString("email"));
                personList.add(person);
            }
        } catch (Exception e) {
            LOGGER.info(e);
        } finally {
            closeResource(statement);
            closeResource(resultSet);
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return personList;
    }


    @Override
    protected Person resultSetToObject(ResultSet resultSet) {
        Person person = null;
        try{
            while (resultSet.next()){
                person = new Person();
                person.setId(resultSet.getLong("id"));
                person.setFirstName(resultSet.getString("first_name"));
                person.setLastName(resultSet.getString("last_name"));
                person.setAddress(resultSet.getString("address"));
                person.setPhoneNumber(resultSet.getString("phone_number"));
                person.setEmail(resultSet.getString("email"));
            }
        } catch (Exception e) {
            LOGGER.info(e);
        }
        return person;
    }


}
