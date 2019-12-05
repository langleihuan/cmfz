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
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class User implements Serializable {
    @Id
    private String id;
    private String head;
    private String username;
    private String password;
    private String salt;
    private String status;
    private String name;
    private String sex;
    private String phone;
    private String address;
    private String signature;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date lastlogintime;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date create_time;
}
