package com.teterin.mergesort.application;

import com.teterin.mergesort.application.exeptions.ApplicationException;
import com.teterin.mergesort.application.settings.ApplicationSettings;
import com.teterin.mergesort.application.settings.SortMode;
import com.teterin.mergesort.comparators.AscendingSortComparator;
import com.teterin.mergesort.comparators.DescendingSortComparator;
import com.teterin.mergesort.comparators.ISortComparator;
import com.teterin.mergesort.io.InputDataReader;
import com.teterin.mergesort.io.OutputDataWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class SortWorker<T extends Comparable<T>> {

    private static final Logger log = LoggerFactory.getLogger(SortWorker.class);
    private ApplicationSettings settings;
    private List<InputDataReader> inputFiles;
    private OutputDataWriter outputFile;
    private List<T> linesProjection;
    private ISortComparator comparator;

    SortWorker(ApplicationSettings appSettings) {
        settings = appSettings;
        inputFiles = new ArrayList<>();
        outputFile = new OutputDataWriter();
        linesProjection = new ArrayList<>();
    }

    void mergeInputFiles() throws ApplicationException, IOException {
        createInputFilesList();
        createOutputFile();
        setComparator();
        createLinesProjection();

        while (!inputFiles.isEmpty()) {
            T sortedElement = findSortedElementInProjection();
            outputFile.writeLineToFile(sortedElement);
            int index = linesProjection.indexOf(sortedElement);
            T nextElement = getNextElementToPutInProjection(sortedElement, index);
            putNextElementInProjection(index, nextElement);
        }
        outputFile.closeWriter();
    }

    private void createInputFilesList() throws ApplicationException {
        for (String name : settings.getInputFileNames()) {
            InputDataReader file = new InputDataReader(settings.getDataType());
            if (file.open(name)) {
                inputFiles.add(file);
            }
        }

        if (inputFiles.isEmpty()) {
            throw new ApplicationException("Не удалось открыть ни одного файла.");
        }
    }

    private void createOutputFile() throws IOException {
        outputFile.openFile(settings.getOutputFileName());
    }

    private void setComparator() {
        if (settings.getSortMode() == SortMode.ASCENDING) {
            comparator = new AscendingSortComparator();
        } else {
            comparator = new DescendingSortComparator();
        }
    }

    private void createLinesProjection() throws ApplicationException {
        for (InputDataReader file : inputFiles) {
            T line = file.getNextLine();

            if (line != null) {
                linesProjection.add(line);
            } else {
                inputFiles.remove(file);
            }
        }

        if (inputFiles.isEmpty()) {
            throw new ApplicationException("Во входных файлах нет данных.");
        }
    }

    private T findSortedElementInProjection() {
        T sortedElement = linesProjection.get(0);
        for (T comparedElement : linesProjection) {
            if (comparator.compare(sortedElement, comparedElement) > 0) {
                sortedElement = comparedElement;
            }
        }
        return sortedElement;
    }

    private T getNextElementToPutInProjection(T sortedElement, int index) {
        while (true) {
            T nextElementToPutInProjection = inputFiles.get(index).getNextLine();

            if (nextElementToPutInProjection == null) {
                return null;
            } else if (comparator.compare(sortedElement, nextElementToPutInProjection) > 0) {
                log.warn("Элемент '{}' в файле '{}' не соответствует заданному порядку сортировки '{}'. Элемент пропущен.",
                        nextElementToPutInProjection, settings.getInputFileNames().get(index), settings.getSortMode());
            } else {
                return nextElementToPutInProjection;
            }
        }
    }

    private void putNextElementInProjection(int index, T nextElement) {
        if (nextElement != null) {
            linesProjection.set(index, nextElement);
        } else {
            linesProjection.remove(index);
            inputFiles.get(index).closeReader();
            inputFiles.remove(index);
        }
    }
}
