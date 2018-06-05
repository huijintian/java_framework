package com.client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengtian on 2018/6/5
 */
@RestController
public class EmployeeDetailController {

    @GetMapping("/getEmployeeDetail/")
    public Employee getEmployeeDetail(@RequestParam("id") Integer id) {
        System.out.println("Getting employee detail...");
        List<Employee> list = new ArrayList<>();

        //sample data
        list.add(new Employee(1, "Alex"));
        list.add(new Employee(2, "Robin"));

        return list.get(id - 1);
    }
}
