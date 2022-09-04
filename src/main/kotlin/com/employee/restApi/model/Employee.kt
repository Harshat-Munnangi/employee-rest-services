package com.employee.restApi.model

import com.employee.restApi.exception.EmployeeNotFoundException
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

data class Employee(
    val empNo: String?,
    val empName: String,
    val empSalary: Long,
    val empAge: Int
) {
    init {
        require(empName.isNotBlank()) { "Employee name should not be Blank" }
        require(empSalary > 0) { "Employee salary should be greater than 0" }
        require(empAge >= 18 ) { "Employee age should be greater than or equal to 18" }
    }
}

@Component
class EmployeeList {

    @PostConstruct
    fun setUp() {
        addEmp(Employee(null, "John Doe", 18000, 29))
        addEmp(Employee(null, "Sunny", 59000, 36))
    }

    private val empList: MutableList<Employee> = mutableListOf()

    private var sequence = 1

    private fun generateEmpId() = sequence++

    fun getEmpList() = empList

    fun getEmpById(id: String) = findEmp(id)

    fun updateEmpList(emp: Employee) =
        findEmp(emp.empNo!!)
            ?.let { updateEmp(it, emp) }
            ?: throw EmployeeNotFoundException("Employee details with Emp No: ${emp.empNo} not found to update!")

    fun addEmp(emp: Employee): List<Employee> {
        empList.add(emp.copy(empNo = "${generateEmpId()}"))
        return empList
    }

    fun deleteEmp(empId: String) =
        findEmp(empId)
            ?.let { empList.remove(it) }
            ?: throw EmployeeNotFoundException("Employee details with Emp No: $empId not found to delete!")

    private fun updateEmp(oldEmp: Employee, newEmp: Employee): List<Employee> {
        val index = empList.indexOf(oldEmp)
        empList[index] = newEmp
        return empList
    }

    private fun findEmp(empId: String): Employee? {
        return empList.firstOrNull { it.empNo == empId }
    }
}
