package com.univet.session;

import com.univet.service.PetService;
import com.univet.service.OwnerService;
import com.univet.service.ServiceService;
import com.univet.service.AppointmentService;

public class SessionManager {
    private static SessionManager instance;
    private final PetService petService;
    private final OwnerService ownerService;
    private final ServiceService serviceService;
    private final AppointmentService appointmentService;

    private SessionManager(PetService petService, OwnerService ownerService,
                           ServiceService serviceService, AppointmentService appointmentService) {
        this.petService = petService;
        this.ownerService = ownerService;
        this.serviceService = serviceService;
        this.appointmentService = appointmentService;
    }

    public static void initialize(PetService petService, OwnerService ownerService,
                                  ServiceService serviceService, AppointmentService appointmentService) {
        if (instance == null) {
            instance = new SessionManager(petService, ownerService, serviceService, appointmentService);
        }
    }

    public static SessionManager getInstance() {
        return instance;
    }

    public void saveSessionData() {
        petService.saveChanges();
        ownerService.saveChanges();
        serviceService.saveChanges();
        appointmentService.saveChanges();
    }
}
