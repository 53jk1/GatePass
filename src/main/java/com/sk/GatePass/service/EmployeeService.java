package com.sk.GatePass.service;

import com.sk.GatePass.model.Employee;
import com.sk.GatePass.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public List<Employee> getEmployees(){
        return employeeRepository.findAll();
    }

    public Employee addEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public Optional<Employee> updateEmployeeById(Long id, Employee newEmployeeDetails) {
        return employeeRepository.findById(id).map(user -> {
            user.setFirstName(newEmployeeDetails.getFirstName());
            user.setLastName(newEmployeeDetails.getLastName());
            user.setMail(newEmployeeDetails.getMail());
            user.setPassword(newEmployeeDetails.getPassword());
            return employeeRepository.save(user);
        });
    }

    public void deleteEmployeeById(Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
        } else {
            throw new RuntimeException("User not found");
        }
    }
}
