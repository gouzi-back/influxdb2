package com.example.demo.service.impl;

import com.example.demo.pojo.Student;
import com.example.demo.service.TestService;
import com.example.demo.utils.OffsetDateTimeUtils;
import com.influxdb.client.DeleteApi;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private InfluxDBClient client;

    @Value("${spring.influx.org}")
    private String org;
    @Value("${spring.influx.bucket}")
    private String bucket;


    @Override
    public void insertPoint() {
        System.out.println("=============开始插入数据===单数据点插入==========");
        // 写入数据必要的
        WriteApiBlocking writeApi= client.getWriteApiBlocking();
        /*
         * WritePrecision.S
         * 在写入和读取 influxdb 中的数据时，时间戳默认单位是纳秒，
         * 可以通过 precision 参数来指定为其他格式，
         * 比如 rfc3339 (YYYY-MM-DDTHH:MM:SS.nnnnnnnnnZ), h (小时), m (分钟), s (秒), ms (毫秒), u (微妙), ns (纳秒)
         */
        Point point = Point.measurement("testPoint").addTag("testPoint_id","NUM100")
                .addField("testName","测试单数据点插入插入")
                .addField("num",100)
                .time(Instant.now(), WritePrecision.S);
        writeApi.writePoint(bucket,org,point);
    }

    public void insertData(Student student) {
        student.setTime(Instant.now());
        System.out.println("=============开始插入数据======pojo=======");
        WriteApiBlocking writeApi = client.getWriteApiBlocking();
        writeApi.writeMeasurement(bucket, org, WritePrecision.NS, student);
    }

    @Override
    public List<FluxTable> selectData(String bucket) {
        System.out.println("==============开始查询数据================");
        String query = "from(bucket: \""+bucket+"\") |> range(start: -1h) |> filter(fn: (r) => r[\"_measurement\"] == \"test\")";
        List<FluxTable> tables = client.getQueryApi().query(query, org);

        for (FluxTable table : tables) {
            for (FluxRecord record : table.getRecords()) {
                System.out.println(record);
            }
        }
        return tables;
    }

    @Override
    public void deleteData(String measurement,String val) {
        DeleteApi deleteApi = client.getDeleteApi();
        // 结束时间
        OffsetDateTime endDate = OffsetDateTime.now();
        // 开始时间
        OffsetDateTime startDate = OffsetDateTimeUtils.getDateTime(1999, 1, 1);
        String del ="_measurement="+measurement+" AND address="+val;
        deleteApi.delete(startDate,endDate,del,bucket,org);
    }
}