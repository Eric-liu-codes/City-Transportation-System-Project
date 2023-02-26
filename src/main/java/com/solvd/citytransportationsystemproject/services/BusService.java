package com.solvd.citytransportationsystemproject.services;

import com.solvd.citytransportationsystemproject.dao.IBusDao;
import com.solvd.citytransportationsystemproject.dao.mysql.BusDao;
import com.solvd.citytransportationsystemproject.models.Bus;

import java.util.List;

public class BusService {
    private IBusDao busDao;

    public BusService() {
        busDao = new BusDao();
    }

    public Bus createBus(Bus bus) {
        return busDao.createEntity(bus);
    }

    public Bus getBusById(long id) {
        return busDao.getEntityById(id);
    }

    public void updateBus(Bus bus) {
        busDao.updateEntity(bus);
    }

    public void deleteBusById(long id) {
        busDao.deleteEntity(id);
    }

    public Bus getBusByBusNumber(Integer busNumber) {
        return busDao.getBusByBusNumber(busNumber);
    }

    public List<Bus> getAllBuses() {
        return busDao.getAllBuses();
    }
}
