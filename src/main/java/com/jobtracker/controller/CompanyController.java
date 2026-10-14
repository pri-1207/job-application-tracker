package com.jobtracker.controller;

import com.jobtracker.dto.CompanyDTO;
import com.jobtracker.model.Company;
import com.jobtracker.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing companies.
 *
 * <p>Provides endpoints for CRUD operations and company search functionality.</p>
 */
@RestController
@RequestMapping("/api/v1/companies")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Companies", description = "Company management endpoints")
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping
    @Operation(summary = "Get all companies", description = "Retrieve all companies with optional sorting")
    public ResponseEntity<List<Company>> getAllCompanies(
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDir) {
        return ResponseEntity.ok(companyService.getAllCompanies(sortBy, sortDir));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get company by ID", description = "Retrieve a single company by its ID")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id) {
        return ResponseEntity.ok(companyService.getCompanyById(id));
    }

    @GetMapping("/search")
    @Operation(summary = "Search companies", description = "Search companies by name (case-insensitive)")
    public ResponseEntity<List<Company>> searchCompanies(@RequestParam String name) {
        return ResponseEntity.ok(companyService.searchByName(name));
    }

    @PostMapping
    @Operation(summary = "Create a company", description = "Create a new company entry")
    public ResponseEntity<Company> createCompany(@Valid @RequestBody CompanyDTO dto) {
        Company created = companyService.createCompany(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a company", description = "Update an existing company by ID")
    public ResponseEntity<Company> updateCompany(@PathVariable Long id,
                                                  @Valid @RequestBody CompanyDTO dto) {
        return ResponseEntity.ok(companyService.updateCompany(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a company", description = "Delete a company by ID")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }
}
