package ru.pfr.timeTracking.config.configDB;

import lombok.Data;

@Data
public class DatabaseProperty {
    private String url;
    private String username;
    private String password;
    private String classDriver;
}
