package com.mindex.challenge.service;

import com.mindex.challenge.Exceptions.NoSuchEmployeeException;

public interface ReportingStructureService {
    int numberOfReports(String id) throws NoSuchEmployeeException;
}
