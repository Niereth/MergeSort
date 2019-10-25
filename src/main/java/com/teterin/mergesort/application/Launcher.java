package com.teterin.mergesort.application;

import com.teterin.mergesort.application.exeptions.ApplicationException;
import com.teterin.mergesort.application.settings.ApplicationSettings;
import com.teterin.mergesort.application.settings.DataType;
import com.teterin.mergesort.cmdline.CmdLineArgumentsParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Launcher {

    private static final Logger log = LoggerFactory.getLogger(Launcher.class);

    public static void main(String[] args) {

        try {
            ApplicationSettings appSettings = CmdLineArgumentsParser.parse(args);

            SortWorker sortWorker;

            if (appSettings.getDataType() == DataType.INTEGER) {
                sortWorker = new SortWorker<Integer>(appSettings);
            } else {
                sortWorker = new SortWorker<String>(appSettings);
            }

            sortWorker.mergeInputFiles();
        } catch (IOException e) {
            log.error("Программа завершена с ошибкой ввода/вывода.");
        } catch (ApplicationException e) {
            log.error("Программа завершена с ошибкой.", e);
        }
    }
}
