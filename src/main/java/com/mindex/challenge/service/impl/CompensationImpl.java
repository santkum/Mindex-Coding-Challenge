package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.entities.ReadCompensationEntity;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompensationImpl implements CompensationService {

    private static final Logger LOG = LoggerFactory.getLogger(CompensationImpl.class);

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CompensationRepository compensationRepository;

    @Override
    public Compensation create(Compensation compensation) {
        LOG.debug("Creating compensation: [{}]", compensation);
        return compensationRepository.insert(compensation);
    }

    @Override
    public ReadCompensationEntity read(String id) {
        LOG.debug("Reading compensation with employeeId: [{}]", id);
        Employee employee = null;
        try {
            employee = employeeService.read(id);
        } catch (RuntimeException e) {
            return new ReadCompensationEntity(1, "No Employee with this ID found!");
        }
        Compensation compensation = compensationRepository.findByEmployee(employee);

        if (compensation == null) {
            return new ReadCompensationEntity(2, "No Compensation record found for this Employee!");
        }
        return new ReadCompensationEntity(compensation.getEmployee(), compensation.getSalary(), compensation.getEffectiveDate());
    }
}