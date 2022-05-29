package com.example.demo.service;

import com.example.demo.pojo.Student;
import com.influxdb.query.FluxTable;

import java.util.List;

public interface TestService {
    /**
     * 单数据点插入
     */
    void insertPoint();

    /**
     * 入库 pojo
     * @param student
     */
    void insertData( Student student);

    /**
     * 查询
     * @return
     */
    List<FluxTable> selectData(String bucket);

    /**
     * 删除
     * @param measurement
     * @param val
     */
    void deleteData(String measurement,String val);
}
