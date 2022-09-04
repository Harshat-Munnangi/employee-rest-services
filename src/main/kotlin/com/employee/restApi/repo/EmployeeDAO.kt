package com.employee.restApi.repo

import com.employee.restApi.model.Employee
import com.employee.restApi.model.EmployeeList
import org.springframework.stereotype.Repository

@Repository
class EmployeeDAO(private val employeeList: EmployeeList) {

    fun getEmpList() = employeeList.getEmpList()

    fun getEmpById(empId: String) = employeeList.getEmpById(empId)

    fun createEmployee(emp: Employee): List<Employee> = employeeList.addEmp(emp)

    fun updateEmployee(emp: Employee): List<Employee> = employeeList.updateEmpList(emp)

    fun deleteById(id: String): Boolean = employeeList.deleteEmp(id)
}