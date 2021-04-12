package com.mindex.challenge.service;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.entities.ReadCompensationEntity;

public interface CompensationService {
    Compensation create(Compensation compensation);

    ReadCompensationEntity read(String employeeId);
}