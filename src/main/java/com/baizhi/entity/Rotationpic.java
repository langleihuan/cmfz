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
public class Rotationpic implements Serializable {
    @Id
    private String id;
    private String name;
    private String status;
    private String picpath;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date create_time;
    private String introduce;
    private String link;
}
