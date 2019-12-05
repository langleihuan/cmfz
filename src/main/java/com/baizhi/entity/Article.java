package com.baizhi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Article implements Serializable {
    @Id
    private String id;
    private String name;
    private String picpath;
    private String content;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date create_time;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date publish_date;
    private String status;
    private String master_id;
    private Master master;
}
