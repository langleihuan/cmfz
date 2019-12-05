package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import java.util.Date;

/**
 * Created by HIAPAD on 2019/11/28.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class Log {
    @Id
    private String id;
    private String admin;
    private String action;
    private Date time;
    private String status;
}
