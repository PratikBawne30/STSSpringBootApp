package com.hb.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.hb.model.Employee;

public interface EmployeeService {

	List<Employee> getAllEmployees();
	
	void saveEmployee(Employee emp);
	
	Employee getEmployeeById(Integer id);
	
	void deleteEmployeeById(Integer id);
	
	Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
	
	
	
}
