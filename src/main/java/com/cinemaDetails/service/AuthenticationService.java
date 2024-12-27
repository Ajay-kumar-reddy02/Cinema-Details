package com.cinemaDetails.service;

import com.cinemaDetails.payload.VisitorsDTO;
import jakarta.validation.Valid;

public interface AuthenticationService {
    String checkVisitor(@Valid VisitorsDTO visitorsDTO);

    String registerVisitor(@Valid VisitorsDTO visitorsDTO);
}
