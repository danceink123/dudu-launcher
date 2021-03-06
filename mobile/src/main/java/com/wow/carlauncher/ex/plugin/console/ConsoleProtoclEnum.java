package com.wow.carlauncher.ex.plugin.console;

import com.wow.carlauncher.view.activity.set.setItem.SetEnum;

/**
 * Created by 10124 on 2018/3/29.
 */

public enum ConsoleProtoclEnum implements SetEnum {
    SYSTEM("系统实现", 0), NWD("NWD", 1);

    private String name;
    private Integer id;

    ConsoleProtoclEnum(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static ConsoleProtoclEnum getById(Integer id) {
        switch (id) {
            case 0:
                return SYSTEM;
            case 1:
                return NWD;
        }
        return SYSTEM;
    }
}
