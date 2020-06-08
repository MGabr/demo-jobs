package com.github.mgabr.demojobs.company;

import com.github.mgabr.demojobs.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentCompanyService implements CompanyService {

    private final CompanyDocumentMapper companyMapper;

    private final CompanyDocumentRepository companyRepository;

    @Override
    public String create(CompanyCreateDTO company) {
        var companyDoc = companyMapper.toDocument(company);
        return companyRepository.save(companyDoc).getId().toString();
    }

    @Override
    public void update(String companyId, CompanyDTO company) {
        var companyDoc = companyRepository.findById(companyId).orElseThrow(NotFoundException::new);
        var updatedCompanyDoc = companyMapper.toDocument(company, companyDoc);
        companyRepository.save(updatedCompanyDoc);
    }

    @Override
    public CompanyDTO get(String companyId) {
        var companyDoc = companyRepository.findById(companyId).orElseThrow(NotFoundException::new);
        return companyMapper.toDTO(companyDoc);
    }
}
