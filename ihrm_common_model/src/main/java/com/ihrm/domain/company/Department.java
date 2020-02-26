package com.ihrm.domain.company;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: 846602483
 * @Date: 2019-8-4 12:40
 * @Version 1.0
 */
@Entity
@Table(name = "co_department")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department implements Serializable {
    private static final long serialVersionUID = 3336958971747736461L;

    //ID
    @Id
    private String id;

    //父级ID
    private String pid;

    //企业ID
    private String companyId;

    //部门名称
    private String name;

    //部门编码，同级部门不可重复
    private String code;

    //负责人ID
    private String managerId;

    //负责人名称
    private String manager;

    //介绍
    private String introduce;

    //创建时间
    private Date createTime;

}
