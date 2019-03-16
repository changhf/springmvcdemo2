package com.changhf.domain;

import java.io.Serializable;

/**
 * @author changhuafeng
 * @version V1.0.0
 * @since 2017/09/18
 */
public class SysUser implements Serializable {
    private static final long serialVersionUID = -3090098028104099771L;

    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
