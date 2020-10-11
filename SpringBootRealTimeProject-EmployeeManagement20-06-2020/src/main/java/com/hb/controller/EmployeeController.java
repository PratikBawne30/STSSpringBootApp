package com.hb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hb.model.Employee;
import com.hb.service.EmployeeService;

@Controller
public class EmployeeController {

	@Autowired
	private EmployeeService empService;
	
	//Spring Boot auto configure view resolver for Thymeleaf from starter dependency
	
	//Display List of employees
	@RequestMapping(value = "/")
	public String viewHomePage(Model model) {
		return findPaginated(1, "firstName", "asc", model);
	}
	
	@RequestMapping(value = "/newEmployee")
	public String newEmployeeForm(Model model) {
		//create model attribute to bind form data, Thymeleaf template will access this empty employee object for binding form data 
		Employee employee = new Employee();
		model.addAttribute("employee",employee);
		return "new_Employee";
	}
	
	@PostMapping(value = "/saveEmployee")
	public String saveEmployee(@ModelAttribute("employee") Employee employee) {
		//save employee to DB
		empService.saveEmployee(employee);
		return "redirect:/";
	}
	
	@RequestMapping(value="/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable (value = "id") Integer id, Model model ) {
		
		//Get Employee from the service
		Employee employee = empService.getEmployeeById(id);
		
		//Set employee as a model attribute to pre-populate the form
		model.addAttribute("employee", employee);
		return "update_employee";
		
	}
	
	@RequestMapping("/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable (value = "id") Integer id) {
		//Call Dleete Employee Method
		this.empService.deleteEmployeeById(id);
		return "redirect:/";
	}
	
	///page/1?sortField=name&sortDir=asc
	
	@RequestMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable (value = "pageNo") int pageNo,
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		int pageSize = 5;
		
		Page<Employee> page = empService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Employee> listEmployees = page.getContent();  //getContent() returns List of employees
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("listEmployees", listEmployees);
		
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
				
		return "index";
	}
	
	
	
	
	
}
