package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureImplTest {

    private String employeeUrl;
    private Employee testEmployee;
    private String reportingStructureURL;

    @Autowired
    private EmployeeService employeeService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        employeeUrl = "http://localhost:" + port + "/employee";
        reportingStructureURL = "http://localhost:" + port + "reportingStructure/{id}";

        generateEmployees();
    }

    private void generateEmployees() {

        Employee directEmployee1 = new Employee();
        directEmployee1.setFirstName("Smith");

        Employee directEmployee2 = new Employee();
        directEmployee2.setFirstName("Mike");
        directEmployee2.setDirectReports(Arrays.asList(directEmployee1));

        testEmployee = new Employee();
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");
        List<Employee> directEmp = new ArrayList<>();
        directEmp.add(directEmployee2);
        testEmployee.setDirectReports(directEmp);

    }

    @Test
    public void numberOfReportsTest() {
        Employee createdEmployee = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class).getBody();

        Integer expected = 2;
        Integer count = restTemplate.getForEntity(reportingStructureURL, Integer.class, createdEmployee.getEmployeeId()).getBody();
        assertEquals(expected, count);
    }


    @Test
    public void numberOfReportsExceptionTest() {
        Integer expected = -1;
        Integer count = restTemplate.getForEntity(reportingStructureURL, Integer.class, "123").getBody();
        assertEquals(expected, count);
    }
}