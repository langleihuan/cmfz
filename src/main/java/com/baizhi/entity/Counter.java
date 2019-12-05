package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Counter implements Serializable {
    @Id
    private String id;
    private String name;
    private Integer count;
    private Date create_time;
    private String user_id;
    private String course_id;
}
