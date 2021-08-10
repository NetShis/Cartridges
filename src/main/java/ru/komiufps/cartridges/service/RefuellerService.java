package ru.komiufps.cartridges.service;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.komiufps.cartridges.entity.Refueller;
import ru.komiufps.cartridges.repository.RefuellerRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RefuellerService {
    private final RefuellerRepository refuellerRepository;

    public Refueller getDefaultRefueller() {
        List<Refueller> refuellerList = refuellerRepository.findAll();
        List<Refueller> refuellerListDefault = new ArrayList<>();

        for (Refueller refueller : refuellerList) {
            if (refueller.isDefault())
                refuellerListDefault.add(refueller);
        }

        if (refuellerListDefault.size() == 1)
            return refuellerListDefault.get(0);
        else if (refuellerListDefault.size() > 1)
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "В базе установленно несколько заправщиков по умолчанию!");
        else
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Заправщик по умолчанию не выбран!");
    }
}

