package com.example.demo.sec.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * DepartTree
 *
 * @author 彭彭
 * @date 6/26/2021
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DepartTree implements Serializable {
    private Long deptId;
    private  String title;
    private Long parentId;
    private List<DepartTree> children;
}
