package com.teterin.mergesort.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class OutputDataWriter {

    private static final Logger log = LoggerFactory.getLogger(OutputDataWriter.class);
    private PrintWriter writer;

    public void openFile(String filename) throws IOException {
        try {
            writer = new PrintWriter(filename, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("Ошибка. Невозможно создать файл для записи.", e);
            throw e;
        }
    }

    public <T> void writeLineToFile(T line) {
        if (writer != null) {
            writer.println(line);
        } else {
            log.error("Не удалось записать строку {}. Файл для записи не открыт.", line);
        }
    }

    public void closeWriter() {
        if (writer != null) {
            writer.close();
        } else {
            log.error("Невозможно закрыть файл. Файл для записи не открыт.");
        }
    }
}
