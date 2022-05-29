package com.example.demo.pojo;


import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import lombok.Data;

import java.time.Instant;

@Data
@Measurement(name = "test")
public class Student {
    /**
     * 值
     */
    @Column
    String name;

    /**
     * 标签字段
     */
    @Column(tag = true)
    String address;

    /**
     * 时间戳
     */
    @Column(timestamp = true)
    Instant time;
}
