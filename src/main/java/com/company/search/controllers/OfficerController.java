package com.company.search.controllers;

import com.company.search.dtos.OfficerDTO;
import com.company.search.entities.Officer;
import com.company.search.services.OfficerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class OfficerController {

    private final OfficerService officerService;
    // Define endpoints for officer lookup

    @GetMapping("/officer/{officerId}")
    public Officer getOfficer(@PathVariable Long officerId) {
        return officerService.findById(officerId);
    }

    @PostMapping("/officer/save")
    public ResponseEntity<String> saveOfficer(@RequestBody List<OfficerDTO> officerDTOs) {
        officerService.saveOfficer(officerDTOs);
        return ResponseEntity.ok("Company saved successfully");
    }
}
