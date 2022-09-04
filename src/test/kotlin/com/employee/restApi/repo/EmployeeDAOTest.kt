package com.employee.restApi.repo

import com.employee.restApi.exception.EmployeeNotFoundException
import com.employee.restApi.model.Employee
import com.employee.restApi.model.EmployeeList
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


private val empList = mutableListOf(
    Employee("2", "Example2", 2090, 18),
    Employee("3", "Example3", 12233, 36),
    Employee("1", "Example1", 22222, 56)
)

class EmployeeDAOTest {

    private val employeeList: EmployeeList = mockk()

    private val employeeDAO = EmployeeDAO(employeeList)

    @Test
    fun `get all employees`() {
        every { employeeList.getEmpList() } returns empList
        val response = employeeDAO.getEmpList()
        assertThat(response).isNotEmpty
        assertThat(response.size).isEqualTo(3)
    }

    @Test
    fun `get empty list of employees`() {
        every { employeeList.getEmpList() } returns mutableListOf()
        val response = employeeDAO.getEmpList()
        assertThat(response).isEmpty()
    }

    @Test
    fun `get employee by Id`() {
        every { employeeList.getEmpById(any()) } returns null
        val response = employeeDAO.getEmpById("3")
        assertThat(response).isNull()
    }

    @Test
    fun `add employee to storage`() {
        every { employeeList.addEmp(any()) } returns empList
        val response = employeeDAO.createEmployee(empList[0].copy(empNo = null))
        assertThat(response).isNotEmpty
        verify(exactly = 1) { employeeList.addEmp(any()) }
    }

    @Test
    fun `update employee to storage`() {
        every { employeeList.updateEmpList(any()) } returns empList
        val response = employeeDAO.updateEmployee(empList[0].copy(empAge = 19))
        assertThat(response).isNotEmpty
        verify(exactly = 1) { employeeList.updateEmpList(any()) }
    }

    @Test
    fun `should throw exception when id not found to update`() {
        every { employeeList.updateEmpList(any()) } throws EmployeeNotFoundException("Not found")
        assertThrows<EmployeeNotFoundException> { employeeDAO.updateEmployee(empList[1]) }
        verify(exactly = 1) { employeeList.updateEmpList(any()) }
    }

    @Test
    fun `delete employee from storage`() {
        every { employeeList.deleteEmp(any()) } returns true
        val response = employeeDAO.deleteById("2")
        assertThat(response).isTrue
        verify(exactly = 1) { employeeList.deleteEmp(any()) }
    }

    @Test
    fun `should throw exception when id not found to delete`() {
        every { employeeList.deleteEmp(any()) } throws EmployeeNotFoundException("Not found")
        assertThrows<EmployeeNotFoundException> { employeeDAO.deleteById("2") }
        verify(exactly = 1) { employeeList.deleteEmp(any()) }
    }
}