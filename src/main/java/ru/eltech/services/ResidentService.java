package ru.eltech.services;

import org.springframework.stereotype.Service;
import ru.eltech.entity.Resident;
import ru.eltech.repositories.ResidentRepository;

import java.util.List;

@Service
public class ResidentService {

    private final ResidentRepository residentRepository;

    public ResidentService(ResidentRepository residentRepository) {
        this.residentRepository = residentRepository;
    }

    public List<Resident> getAllResidents() {
        return residentRepository.findAll();
    }

    public Resident createResident(Resident resident) {
        return residentRepository.save(resident);
    }
}
