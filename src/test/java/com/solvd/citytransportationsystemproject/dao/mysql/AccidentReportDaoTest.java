package com.solvd.citytransportationsystemproject.dao.mysql;

import com.solvd.citytransportationsystemproject.models.AccidentReport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;

import java.sql.Date;
import java.util.List;

import static junit.framework.TestCase.*;

public class AccidentReportDaoTest {

    private AccidentReportDao accidentReportDao = new AccidentReportDao();
    private static final Logger LOGGER = LogManager.getLogger(AccidentReportDaoTest.class);

//    @Test
//    public void testCreateEntity() {
//        AccidentReport accidentReport = new AccidentReport();
//        accidentReport.setId(7);
//        accidentReport.setDate(Date.valueOf("2022-12-05"));
//        accidentReport.setDescription("Car accident on Main St.");
//        accidentReport.setPersonId(7);
//        accidentReport.setVehicleId(7);
//
//        AccidentReport createdAccidentReport = accidentReportDao.createEntity(accidentReport);
//
//        assertNotNull(createdAccidentReport);
//        assertEquals(accidentReport.getId(), createdAccidentReport.getId());
//        assertEquals(accidentReport.getDate(), createdAccidentReport.getDate());
//        assertEquals(accidentReport.getDescription(), createdAccidentReport.getDescription());
//        assertEquals(accidentReport.getPersonId(), createdAccidentReport.getPersonId());
//        assertEquals(accidentReport.getVehicleId(), createdAccidentReport.getVehicleId());
//    }


//    @Test
//    public void testGetEntityById() {
//        // Create a test accident report and insert it into the database
//        AccidentReport testReport = new AccidentReport();
//        testReport.setId(6);
//        testReport.setDate(Date.valueOf("2022-04-05"));
//        testReport.setDescription("Accident at Crown Center");
//        testReport.setPersonId(6);
//        testReport.setVehicleId(6);
//        AccidentReport createdReport = accidentReportDao.createEntity(testReport);
//
//        // Retrieve the report by ID
//        AccidentReport retrievedReport = accidentReportDao.getEntityById(createdReport.getId());
//
//        // Assert that the retrieved report is not null
//        Assertions.assertNotNull(retrievedReport);
//
//        // Assert that the retrieved report's properties match the input report's properties
//        Assertions.assertEquals(createdReport.getId(), retrievedReport.getId());
//        Assertions.assertEquals(createdReport.getDate(), retrievedReport.getDate());
//        Assertions.assertEquals(createdReport.getDescription(), retrievedReport.getDescription());
//        Assertions.assertEquals(createdReport.getPersonId(), retrievedReport.getPersonId());
//        Assertions.assertEquals(createdReport.getVehicleId(), retrievedReport.getVehicleId());
//    }


//    @Test
//    public void testUpdateEntity() throws Exception {
//        // Create a test AccidentReport object
//        AccidentReport report = new AccidentReport();
//        report.setId(6);
//        report.setDate(Date.valueOf("2022-01-01"));
//        report.setDescription("Bus turned over on freeway");
//        report.setPersonId(6);
//        report.setVehicleId(6);
//
//        // Update the AccidentReport object using the DAO's updateEntity method
//        AccidentReportDao dao = new AccidentReportDao();
//        dao.updateEntity(report);
//
//        // Retrieve the updated AccidentReport from the database
//        AccidentReport updatedReport = dao.getEntityById(6);
//
//        // Verify that the AccidentReport was updated correctly
//        assertEquals(Date.valueOf("2022-01-01"), updatedReport.getDate());
//        assertEquals("Bus turned over on freeway", updatedReport.getDescription());
//        assertEquals(6, updatedReport.getPersonId());
//        assertEquals(6, updatedReport.getVehicleId());
//    }

//    @Test
//    public void testDeleteEntity() {
//        // Insert a test record into the database
//        AccidentReport report = new AccidentReport();
//        report.setId(23);
//        report.setDate(Date.valueOf("2022-12-05"));
//        report.setDescription("Car accident on Main St.");
//        report.setPersonId(7);
//        report.setVehicleId(7);
//
//        AccidentReportDao dao = new AccidentReportDao();
//        dao.createEntity(report);
//
//        // Delete the test record from the database
//        dao.deleteEntity(report.getId());
//
//        // Verify that the record was deleted
//        assertNull(dao.getEntityById(report.getId()));
//    }


//    @Test
//    public void testGetAccidentReportByDate() {
//        // Set up the test case
//        AccidentReportDao dao = new AccidentReportDao();
//        AccidentReport expectedReport = new AccidentReport();
//        expectedReport.setId(9);
//        expectedReport.setDate(Date.valueOf("2023-02-03"));
//        expectedReport.setDescription("Train's brakes failed");
//        expectedReport.setPersonId(9);
//        expectedReport.setVehicleId(9);
//        dao.createEntity(expectedReport);
//
//        // Call the method being tested
//        AccidentReport actualReport = dao.getAccidentReportByDate(Date.valueOf("2023-02-03"));
//
//        // Verify the results
//        assertNotNull(actualReport);
//        assertEquals(expectedReport.getId(), actualReport.getId());
//        assertEquals(expectedReport.getDate(), actualReport.getDate());
//        assertEquals(expectedReport.getDescription(), actualReport.getDescription());
//        assertEquals(expectedReport.getPersonId(), actualReport.getPersonId());
//        assertEquals(expectedReport.getVehicleId(), actualReport.getVehicleId());
//    }

//    @Test
//    public void testGetAccidentReportByVehicleId() {
//        // Set up the test case
//        AccidentReportDao dao = new AccidentReportDao();
//        AccidentReport expectedReport = new AccidentReport();
//        expectedReport.setId(10);
//        expectedReport.setDate(Date.valueOf("2022-11-23"));
//        expectedReport.setDescription("Accident on freeway");
//        expectedReport.setPersonId(10);
//        expectedReport.setVehicleId(10);
//        dao.createEntity(expectedReport);
//
//        // Call the method being tested
//        AccidentReport actualReport = dao.getAccidentReportByVehicleId(10);
//
//        // Verify the results
//        assertNotNull(actualReport);
//        assertEquals(expectedReport.getId(), actualReport.getId());
//        assertEquals(expectedReport.getDate(), actualReport.getDate());
//        assertEquals(expectedReport.getDescription(), actualReport.getDescription());
//        assertEquals(expectedReport.getPersonId(), actualReport.getPersonId());
//        assertEquals(expectedReport.getVehicleId(), actualReport.getVehicleId());
//    }


    @Test
    public void testGetAllAccidentReports() {
        // Set up the test case
        AccidentReportDao dao = new AccidentReportDao();
        List<AccidentReport> reports = dao.getAllAccidentReports();
        assertTrue(reports.size() > 0);
        AccidentReport report1 = reports.get(0);

        // Call the method being tested
        List<AccidentReport> actualReports = dao.getAllAccidentReports();


        // Verify the results
        assertNotNull(actualReports);
        assertEquals(reports.size(), actualReports.size());
        assertTrue(actualReports.contains(report1));
    }


}
