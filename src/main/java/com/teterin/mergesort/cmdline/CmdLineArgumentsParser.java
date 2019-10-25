package com.teterin.mergesort.cmdline;

import com.teterin.mergesort.application.exeptions.ApplicationException;
import com.teterin.mergesort.application.settings.ApplicationSettings;
import com.teterin.mergesort.application.settings.DataType;
import com.teterin.mergesort.application.settings.SortMode;

import java.util.*;

public class CmdLineArgumentsParser {

    private CmdLineArgumentsParser() {
    }

    public static ApplicationSettings parse(String[] args) throws ApplicationException {

        if (args.length < CmdLineOptionsProperties.ARGS_MINIMUM) {
            throw new ApplicationException("Недостаточно параметров.");
        }

        Set<String> cmdLineOptions = getOptions(args);

        SortMode sortMode = getSortMode(cmdLineOptions);
        DataType dataType = getDataType(cmdLineOptions);

        int optionsAmount = cmdLineOptions.size();

        String outputFileName = args[optionsAmount];

        List<String> inputFileNames = new ArrayList<>();
        Collections.addAll(inputFileNames, Arrays.copyOfRange(args, optionsAmount + 1, args.length));

        return new ApplicationSettings(sortMode, dataType, outputFileName, inputFileNames);
    }

    private static Set<String> getOptions(String[] args) {
        Set<String> validParams = CmdLineOptionsProperties.getOptions();
        Set<String> cmdLineOptions = new HashSet<>();

        for (String option : args) {
            if (validParams.contains(option)) {
                cmdLineOptions.add(option);
            } else {
                break;
            }
        }
        return cmdLineOptions;
    }

    private static SortMode getSortMode(Set<String> cmdLineOptions) {
        if (cmdLineOptions.contains(CmdLineOptionsProperties.DESCENDING_MODE)) {
            return SortMode.DESCENDING;
        } else {
            return SortMode.ASCENDING;
        }
    }

    private static DataType getDataType(Set<String> cmdLineOptionsList) throws ApplicationException {
        DataType dataType = null;
        if (cmdLineOptionsList.contains(CmdLineOptionsProperties.INTEGER_DATA_TYPE)) {
            dataType = DataType.INTEGER;
        } else if (cmdLineOptionsList.contains(CmdLineOptionsProperties.STRING_DATA_TYPE)) {
            dataType = DataType.STRING;
        }
        if (dataType == null) {
            throw new ApplicationException("Не задан параметр 'режим сортировки'.");
        }
        return dataType;
    }
}
