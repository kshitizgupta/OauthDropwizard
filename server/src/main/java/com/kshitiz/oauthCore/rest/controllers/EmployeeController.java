package com.kshitiz.oauthCore.rest.controllers;

import com.kshitiz.oauthCore.model.User;
import com.kshitiz.oauthCore.dao.EmployeeDb;
import com.kshitiz.oauthCore.model.Employee;
import io.dropwizard.auth.Auth;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/employees")
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeController {
    //db object
    private final EmployeeDb employeeDb;

    /**
     * Constructor.
     * @param empDb
     */
    public EmployeeController(final EmployeeDb empDb) {
        employeeDb = empDb;
    }

    /**
     * gets list of all employees.
     * @param user
     * @return list of employees
     */
    @GET
    public List<Employee> getEmployees(@Auth final User user) {
        return employeeDb.getAll();
    }

    /**
     * Just a test.
     * @param user
     * @return all employees
     */
    @GET
    @Path("/test")
    public Response getEmployeess(@Auth final User user) {
        return Response.ok(employeeDb.getAll()).build();
    }

    /**
     * gets an employeed with id.
     * @param id
     * @return an employee
     */
    @GET
    @Path("/{id}")
    public Employee getEmployee(@PathParam("id") final int id) {
        Employee employee = employeeDb.getById(id)
            .orElseThrow(() -> new NotFoundException("Employee with id = " + id + " was not found!"));
        return employee;
    }
}
