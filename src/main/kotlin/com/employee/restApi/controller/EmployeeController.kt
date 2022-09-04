package com.employee.restApi.controller

import com.employee.restApi.exception.EmployeeNotFoundException
import com.employee.restApi.model.Employee
import com.employee.restApi.repo.EmployeeDAO
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1")
class EmployeeController(private val employeeDAO: EmployeeDAO) {

    @GetMapping("/employees")
    fun getAllEmployees() = employeeDAO.getEmpList()

    @GetMapping("/employee/{id}")
    fun getEmployeeById(@PathVariable("id") id: String): ResponseEntity<*> {
        return employeeDAO.getEmpById(id)?.let { ok(it) } ?: throw EmployeeNotFoundException("Employee details with Emp No: $id not found!")
    }

    @PostMapping("/save")
    fun saveEmployee(@RequestBody emp: Employee): ResponseEntity<*> {
        val empList = emp.empNo?.let { employeeDAO.updateEmployee(emp) } ?: employeeDAO.createEmployee(emp)
        return ok(empList)
    }

    @DeleteMapping("/delete/{id}")
    fun deleteById(@PathVariable("id") id: String): ResponseEntity<*> {
        val isDeleted = employeeDAO.deleteById(id)
        return if (isDeleted) ok("Employee deleted successfully!")
        else ResponseEntity.noContent().build<String>()
    }
}