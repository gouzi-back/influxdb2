package com.example.demo.controller;

import com.example.demo.pojo.Student;
import com.example.demo.service.TestService;
import com.influxdb.query.FluxTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TestControlller {

    @Autowired
    private TestService testService;

    @RequestMapping("/insertPoint")
    public void insertPoint( ) {
        testService.insertPoint();
        System.out.println("================插入数据成功===单数据点插入==========");
    }


    @PostMapping("/insert")
    public void insertData( @RequestBody Student student) {
        testService.insertData(student);
        System.out.println("================插入数据成功=============");
    }

    @GetMapping("/select")
    public List<FluxTable> selectData(@RequestParam(name = "bucket")String bucket) {

        System.out.println("===========查询数据完成=============");
        return testService.selectData(bucket);
    }


    @DeleteMapping("/del")
    public void deleteData(@RequestParam(name = "measurement") String measurement,@RequestParam(name = "val")String val) {
        testService.deleteData(measurement,val);
        System.out.println("===========删除数据完成=============");
    }
}
