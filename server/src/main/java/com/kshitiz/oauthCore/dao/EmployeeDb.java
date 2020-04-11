package com.kshitiz.oauthCore.dao;

import com.kshitiz.oauthCore.model.Employee;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class EmployeeDb {
    private static HashMap<Integer, Employee> employeesMap = new HashMap<>();
    static {
        employeesMap.put(1, new Employee(1, "Kshitiz"));
        employeesMap.put(2, new Employee(2, "Raj"));
        employeesMap.put(3, new Employee(3, "Rahul"));
    }
    public List<Employee> getAll() {
        return new ArrayList<>(employeesMap.values());
    }

    public Optional<Employee> getById(final int id) {
        return Optional.ofNullable(employeesMap.get(id));
    }
}
