package com.orrin.sca.component.redis.config;

public enum Prefixes {


    RESOURCE_AND_AUTHORITIES_SET("RESOURCE:AUTHORIT:RESOURCE_AND_AUTHORITIES_SET","");


    private String value;

    private String name;

    Prefixes(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
