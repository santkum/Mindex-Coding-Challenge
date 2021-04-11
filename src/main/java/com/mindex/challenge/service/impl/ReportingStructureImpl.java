package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReportingStructureImpl implements ReportingStructureService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureImpl.class);

    @Autowired
    private EmployeeService employeeService = new EmployeeServiceImpl();

    @Override
    public int numberOfReports(String id) {
        LOG.debug("Counting number of reports with id [{}]", id);
        int count = 0;
        // Counting all the direct reports through BFS
        Queue<Employee> queue = new LinkedList<>();
        Set<Employee> added = new HashSet<>();
        queue.add(employeeService.read(id));
        while (!queue.isEmpty()) {
            Employee emp = queue.poll();
            added.add(emp);
            List<Employee> directReports = emp.getDirectReports();
            // Reached last level of employees
            if (directReports != null) {
                count += directReports.size();

                for (Employee directEmp : directReports) {
                    // Skipping to add an employee if his direct roportees are already added
                    if (!added.contains(directEmp)) {
                        queue.add(directEmp);
                    }
                }
            }
        }
        return count;
    }
}
