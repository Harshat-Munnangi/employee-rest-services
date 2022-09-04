package com.employee.restApi.controller

import com.employee.restApi.exception.EmployeeNotFoundException
import com.employee.restApi.model.Employee
import com.employee.restApi.model.EmployeeList
import com.employee.restApi.repo.EmployeeDAO
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus.OK

private val employee = Employee("1", "Harshat", 235357, 28)

@SpringBootTest
class EmployeeControllerIntTest {

    @Autowired
    private lateinit var employeeDAO: EmployeeDAO

    @Autowired
    private lateinit var employeeList: EmployeeList

    @Autowired
    private lateinit var employeeController: EmployeeController

    @BeforeEach
    fun setUp() {
        this.employeeList = EmployeeList()
    }

    @Test
    fun `get all employees`() {
        val employeeList = employeeController.getAllEmployees()
        assertThat(employeeList.size).isGreaterThan(0)
    }

    @Test
    fun `get employee by id`() {
        val response = employeeController.getEmployeeById("1")
        assertThat(response.statusCode).isEqualTo(OK)
        assertThat(response.body).isNotNull
    }

    @Test
    fun `should throw an exception when employee id not found`() {
        val response = assertThrows<EmployeeNotFoundException> { employeeController.getEmployeeById("1345") }
        assertThat(response.message).isEqualTo("Employee details with Emp No: 1345 not found!")
    }

    @Test
    fun `should update employee if employee id exists`() {
        val response = employeeController.saveEmployee(employee)
        assertThat(response.statusCode).isEqualTo(OK)
        assertThat(response.body).isNotNull
        assertThat(response.body.toString()).contains("Employee(empNo=1, empName=Harshat, empSalary=235357, empAge=28)")
    }

    @Test
    fun `should throw an exception when employee id is not found to update`() {
        val response = assertThrows<EmployeeNotFoundException> { employeeController.saveEmployee(employee.copy(empNo = "123dc")) }
        assertThat(response.message).isEqualTo("Employee details with Emp No: 123dc not found to update!")
    }

    @Test
    fun `should create employee if employee id is null`() {
        val response = employeeController.saveEmployee(employee.copy(empNo = null))
        assertThat(response.statusCode).isEqualTo(OK)
        assertThat(response.body).isNotNull
        assertThat(response.body.toString()).contains("Employee(empNo=3, empName=Harshat, empSalary=235357, empAge=28)")
    }

    @Test
    fun `should delete employee if employee id exists`() {
        val response = employeeController.deleteById("2")
        assertThat(response.statusCode).isEqualTo(OK)
        assertThat(response.body).isNotNull
        assertThat(response.body.toString()).contains("Employee deleted successfully!")
    }

    @Test
    fun `should throw an exception when employee id not exists`() {
        val response = assertThrows<EmployeeNotFoundException> { employeeController.deleteById("2sf3") }
        assertThat(response.message).isEqualTo("Employee details with Emp No: 2sf3 not found to delete!")
    }
}