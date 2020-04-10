package com.howtodoinjava.rest.controllers;

import com.howtodoinjava.auth.User;
import com.howtodoinjava.dao.EmployeeDb;
import com.howtodoinjava.model.Employee;
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
    private final EmployeeDb db;

    /**
     * Constructor.
     * @param empDb
     */
    public EmployeeController(final EmployeeDb empDb) {
        db = empDb;
    }

    /**
     * gets list of all employees.
     * @param user
     * @return list of employees
     */
    @GET
    public List<Employee> getEmployees(@Auth final User user) {
        return db.getAll();
    }

    /**
     * Just a test.
     * @param user
     * @return all employees
     */
    @GET
    @Path("/test")
    public Response getEmployeess(@Auth final User user) {
        return Response.ok(db.getAll()).build();
    }

    /**
     * gets an employeed with id.
     * @param id
     * @return an employee
     */
    @GET
    @Path("/{id}")
    public Employee getEmployee(@PathParam("id") final int id) {
        Employee employee = db.getById(id)
            .orElseThrow(() -> new NotFoundException("Employee with id = " + id + " was not found!"));
        return employee;
    }
}
