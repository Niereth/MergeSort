package com.teterin.mergesort.application.settings;

import com.teterin.mergesort.application.exeptions.ApplicationException;

import java.util.List;

public class ApplicationSettings {

    private SortMode sortMode;
    private DataType dataType;
    private String outputFileName;
    private List<String> inputFileNames;

    public ApplicationSettings(SortMode sortMode, DataType dataType, String outputFileName, List<String> inputFileNames) throws ApplicationException {
        if (sortMode == null || dataType == null || outputFileName == null || inputFileNames == null) {
            throw new ApplicationException("Парамерты командной строки заданы некорректно.");
        }

        this.sortMode = sortMode;
        this.dataType = dataType;
        this.outputFileName = outputFileName;
        this.inputFileNames = inputFileNames;
    }

    public SortMode getSortMode() {
        return sortMode;
    }

    public DataType getDataType() {
        return dataType;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public List<String> getInputFileNames() {
        return inputFileNames;
    }
}
