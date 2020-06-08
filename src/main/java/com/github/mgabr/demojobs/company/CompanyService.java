package com.github.mgabr.demojobs.company;


public interface CompanyService {

    String create(CompanyCreateDTO company);

    void update(String companyId, CompanyDTO company);

    CompanyDTO get(String companyId);
}
