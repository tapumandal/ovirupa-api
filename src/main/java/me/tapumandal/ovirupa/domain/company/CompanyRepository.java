package me.tapumandal.ovirupa.domain.company;

import me.tapumandal.ovirupa.repository.Repository;

public interface CompanyRepository extends Repository<Company> {

    public Company getCompanyFirstTime(int companyId);
}
