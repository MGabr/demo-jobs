package com.github.mgabr.demojobs.company;


public interface CompanyService {

    void upsert(CompanyDTO company);

    CompanyDTO get(String companyId);
}
