package com.github.mgabr.demojobs.company;

import com.github.mgabr.demojobs.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DocumentCompanyService implements CompanyService {

    private final CompanyDocumentMapper companyMapper;

    private final CompanyDocumentRepository companyRepository;

    @Override
    public void upsert(CompanyDTO company) {
        companyRepository.save(companyMapper.toDocument(company));
    }

    @Override
    public CompanyDTO get(String companyId) {
        return companyRepository
                .findById(companyId)
                .map(companyMapper::toDTO)
                .orElseThrow(NotFoundException::new);
    }
}
