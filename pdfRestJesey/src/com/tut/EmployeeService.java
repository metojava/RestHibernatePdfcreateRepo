package com.tut;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

@Path("/emps")
public class EmployeeService {

	EmployeeDao empDao = new EmployeeDao();

	@GET
	@Path("/getAll")
	@Produces("application/xml")
	public List<Employee> getEmployees() {
		return empDao.getEmployees();
	}

	@GET
	@Path("/getAllJson")
	@Produces("application/json")
	public List<Employee> getEmployeesJson() {
		return empDao.getEmployees();
	}

	/*
	@GET
	@Path("/getPdf")
	@Produces("application/pdf")
	public Response getEmployeesPdf() throws IOException {
		ByteArrayOutputStream empbs = empDao.getEmployeesOutputStream(empDao
				.getEmployees());
		String fileName = "emps.pdf";
		FileOutputStream fs = new FileOutputStream(fileName);
		fs.write(empbs.toByteArray());
		fs.close();
		return Response.ok(empbs, MediaType.APPLICATION_OCTET_STREAM)
				.header("Content-Disposition", "attachment; filename=doc.pdf")
				.build();

	}
	*/
	
	@GET
	@Path("/getPdfb")
	@Produces("application/pdf")
	public Response getEmpsPdf() {
		empDao.writeToPdfFile(empDao.getEmployees());
		File file = new File(System.getProperty("user.dir")+"\\"+"Employees.pdf");
		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition", "attachment; filename=doc.pdf");
		return response.build();
	}
}
