package com.employee.restApi.controller

import com.employee.restApi.model.Employee
import com.employee.restApi.repo.EmployeeDAO
import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

private val empList = mutableListOf(
    Employee("2", "Example2", 2090, 18),
    Employee("3", "Example3", 12233, 36),
    Employee("1", "Example1", 22222, 56)
)

class EmployeeControllerTest {

    private val employeeDAO: EmployeeDAO = mockk()

    private var mockMvc = MockMvcBuilders.standaloneSetup(EmployeeController(employeeDAO)).build()

    @Test
    fun `get empty employees list`() {
        every { employeeDAO.getEmpList() } returns mutableListOf()
        mockMvc.perform(
            get("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().string("[]"))
    }

    @Test
    fun `get all employees list`() {
        every { employeeDAO.getEmpList() } returns empList
        mockMvc.perform(
            get("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().string("[{\"empNo\":\"2\",\"empName\":\"Example2\",\"empSalary\":2090,\"empAge\":18},{\"empNo\":\"3\",\"empName\":\"Example3\",\"empSalary\":12233,\"empAge\":36},{\"empNo\":\"1\",\"empName\":\"Example1\",\"empSalary\":22222,\"empAge\":56}]"))
    }

    @Test
    fun `get employee by Id`() {
        every { employeeDAO.getEmpById(any()) } returns empList[2]
        mockMvc.perform(
            get("/api/v1/employee/3")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().string("{\"empNo\":\"1\",\"empName\":\"Example1\",\"empSalary\":22222,\"empAge\":56}"))
    }

    @Test
    fun `should create employee`() {
        every { employeeDAO.createEmployee(any()) } returns empList
        mockMvc.perform(
            post("/api/v1/save")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(ObjectMapper().writeValueAsString(empList[0].copy(empNo = null)))
        )
            .andExpect(status().isOk)
            .andExpect(content().string("[{\"empNo\":\"2\",\"empName\":\"Example2\",\"empSalary\":2090,\"empAge\":18},{\"empNo\":\"3\",\"empName\":\"Example3\",\"empSalary\":12233,\"empAge\":36},{\"empNo\":\"1\",\"empName\":\"Example1\",\"empSalary\":22222,\"empAge\":56}]"))
        verify(exactly = 1) { employeeDAO.createEmployee(any()) }
        verify(exactly = 0) { employeeDAO.updateEmployee(any()) }
    }

    @Test
    fun `should update employee`() {
        every { employeeDAO.updateEmployee(any()) } returns empList
        mockMvc.perform(
            post("/api/v1/save")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(ObjectMapper().writeValueAsString(empList[0].copy(empSalary = 2345566)))
        )
            .andExpect(status().isOk)
            .andExpect(content().string("[{\"empNo\":\"2\",\"empName\":\"Example2\",\"empSalary\":2090,\"empAge\":18},{\"empNo\":\"3\",\"empName\":\"Example3\",\"empSalary\":12233,\"empAge\":36},{\"empNo\":\"1\",\"empName\":\"Example1\",\"empSalary\":22222,\"empAge\":56}]"))
        verify(exactly = 0) { employeeDAO.createEmployee(any()) }
        verify(exactly = 1) { employeeDAO.updateEmployee(any()) }
    }

    @Test
    fun `should delete employee`() {
        every { employeeDAO.deleteById(any()) } returns true
        mockMvc.perform(
            delete("/api/v1/delete/2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().string("Employee deleted successfully!"))
    }

    @Test
    fun `should send not found status for delete employee`() {
        every { employeeDAO.deleteById(any()) } returns false
        mockMvc.perform(
            delete("/api/v1/delete/234")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isNotFound)
    }
}