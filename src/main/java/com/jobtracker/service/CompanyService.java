package com.jobtracker.service;

import com.jobtracker.dto.CompanyDTO;
import com.jobtracker.exception.BadRequestException;
import com.jobtracker.exception.ResourceNotFoundException;
import com.jobtracker.model.Company;
import com.jobtracker.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service layer for managing {@link Company} entities.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class CompanyService {

    private final CompanyRepository companyRepository;

    /**
     * Retrieve all companies, optionally sorted.
     */
    @Transactional(readOnly = true)
    public List<Company> getAllCompanies(String sortBy, String sortDir) {
        Sort sort = Sort.by(Sort.Direction.fromString(
                sortDir != null ? sortDir : "asc"),
                sortBy != null ? sortBy : "name");
        return companyRepository.findAll(sort);
    }

    /**
     * Retrieve a single company by ID.
     */
    @Transactional(readOnly = true)
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company", id));
    }

    /**
     * Search companies by name.
     */
    @Transactional(readOnly = true)
    public List<Company> searchByName(String name) {
        return companyRepository.findByNameContainingIgnoreCase(name);
    }

    /**
     * Create a new company.
     */
    public Company createCompany(CompanyDTO dto) {
        if (companyRepository.existsByNameIgnoreCase(dto.getName())) {
            throw new BadRequestException(
                    "Company with name '" + dto.getName() + "' already exists");
        }
        Company company = Company.builder()
                .name(dto.getName())
                .industry(dto.getIndustry())
                .website(dto.getWebsite())
                .location(dto.getLocation())
                .notes(dto.getNotes())
                .build();
        return companyRepository.save(company);
    }

    /**
     * Update an existing company.
     */
    public Company updateCompany(Long id, CompanyDTO dto) {
        Company company = getCompanyById(id);
        company.setName(dto.getName());
        company.setIndustry(dto.getIndustry());
        company.setWebsite(dto.getWebsite());
        company.setLocation(dto.getLocation());
        company.setNotes(dto.getNotes());
        return companyRepository.save(company);
    }

    /**
     * Delete a company by ID.
     */
    public void deleteCompany(Long id) {
        if (!companyRepository.existsById(id)) {
            throw new ResourceNotFoundException("Company", id);
        }
        companyRepository.deleteById(id);
    }
}
