package com.teterin.mergesort.io;

import com.teterin.mergesort.application.settings.DataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class InputDataReader {

    private static final Logger log = LoggerFactory.getLogger(InputDataReader.class);
    private BufferedReader bufferedReader;
    private DataType dataType;

    public InputDataReader(DataType dataType) {
        this.dataType = dataType;
    }

    public boolean open(String filename) {
        FileReader reader;
        try {
            reader = new FileReader(filename, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.warn("Ошибка открытия файла {}.", filename, e);
            return false;
        }

        bufferedReader = new BufferedReader(reader);
        return true;
    }

    public <T> T getNextLine() {
        T retObject = null;

        while (retObject == null) {
            String line = readLine();

            if (line != null) {
                retObject = getRetObject(line);
            } else {
                break;
            }
        }

        return retObject;
    }

    private String readLine() {
        String line = null;
        try {
            line = bufferedReader.readLine();
        } catch (IOException e) {
            log.warn("Не удается считать строку.", e);
        }
        return line;
    }

    @SuppressWarnings("unchecked")
    private <T> T getRetObject(String line) {
        T retObject = null;
        if (dataType == DataType.INTEGER) {
            Integer parsedLine = isInt(line);
            if (parsedLine != null) {
                retObject = (T) parsedLine;
            }
        } else if (!line.isEmpty() && !line.contains(" ")) {
            retObject = (T) line;
        }
        return retObject;
    }

    private Integer isInt(String line) {
        Integer parsedLine = null;
        try {
            parsedLine = Integer.parseInt(line);
        } catch (NumberFormatException e) {
            log.warn("Строка '{}' содержит нечисловое значение.", line, e);
        }
        return parsedLine;
    }

    public void closeReader() {
        try {
            bufferedReader.close();
        } catch (IOException e) {
            log.warn("Не удалось закрыть поток ввода.", e);
        }
    }
}
