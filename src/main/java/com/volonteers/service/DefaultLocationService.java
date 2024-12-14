package com.volonteers.service;

import com.volonteers.model.Location;
import com.volonteers.repository.LocationRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultLocationService implements LocationService {

    private final LocationRepository locationRepository;

    public DefaultLocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }


    @Override
    public List<Location> saveLocations(List<Location> locations) {
        List<String> locationNames = locations.stream()
                .map(Location::getName)
                .collect(Collectors.toList());

        // Получаем существующие локации из базы данных
        List<Location> existingLocations = locationRepository.findAllByNameIn(locationNames);

        // Создаем список новых локаций
        List<Location> newLocations = new ArrayList<>();

        // Находим новые локации, которые еще не существуют в базе данных
        for (Location location : locations) {
            if (existingLocations.stream().noneMatch(existingLocation -> existingLocation.getName().equals(location.getName()))) {
                newLocations.add(location);
            }
        }

        // Сохраняем новые локации в базе данных
        List<Location> savedNewLocations = locationRepository.saveAll(newLocations);

        // Объединяем существующие локации с новыми локациями
        List<Location> allLocations = new ArrayList<>(existingLocations);
        allLocations.addAll(savedNewLocations);

        // Возвращаем полный список локаций, который включает и новые, и уже существующие
        return allLocations;
    }


}
