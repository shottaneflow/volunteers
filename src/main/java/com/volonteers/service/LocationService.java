package com.volonteers.service;


import com.volonteers.model.Location;

import java.util.List;

public interface LocationService {
    List<Location> saveLocations(List<Location> locations);
}
