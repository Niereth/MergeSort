package com.teterin.mergesort.cmdline;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

class CmdLineOptionsProperties {

    private CmdLineOptionsProperties() {
    }

    private static final String ASCENDING_MODE
            = new PropertiesLoader().getPropertyValue("ASCENDING_MODE");
    static final String DESCENDING_MODE
            = new PropertiesLoader().getPropertyValue("DESCENDING_MODE");
    static final String INTEGER_DATA_TYPE
            = new PropertiesLoader().getPropertyValue("INTEGER_DATA_TYPE");
    static final String STRING_DATA_TYPE
            = new PropertiesLoader().getPropertyValue("STRING_DATA_TYPE");
    static final int ARGS_MINIMUM = 3;

    static Set<String> getOptions() {
        return new HashSet<>(List.of(ASCENDING_MODE, DESCENDING_MODE, INTEGER_DATA_TYPE, STRING_DATA_TYPE));
    }
}
