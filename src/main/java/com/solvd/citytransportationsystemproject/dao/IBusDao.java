package com.solvd.citytransportationsystemproject.dao;

import com.solvd.citytransportationsystemproject.models.Bus;

import java.util.List;

public interface IBusDao extends IBaseDao<Bus> {
    Bus getBusByBusNumber(Integer busNumber);
    List<Bus> getAllBuses();
}
