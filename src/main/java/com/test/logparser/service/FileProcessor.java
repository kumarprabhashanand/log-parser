package com.test.logparser.service;

import com.google.gson.Gson;
import com.test.logparser.model.dto.LogEntityDto;
import com.test.logparser.model.entity.LogEntity;
import com.test.logparser.repository.LogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

@Service
public class FileProcessor {

    @Autowired
    private LogRepository logRepository;

    private final Logger log = LoggerFactory.getLogger(getClass());

    private HashMap<String, LogEntityDto> logMap = new HashMap<>();

    public void processInputFileLogs(String[] inputArgs) {
        validateInputArgs(inputArgs);
        String fileName = inputArgs[0];
        parseFileData(fileName);
    }

    private void validateInputArgs(String[] inputArgs) {
        if(inputArgs==null || inputArgs.length<1) {
            log.error("Invalid Input Argument, Number of Argument should be atleast 1");
            throw new RuntimeException("Invalid Input Argument, Number of Argument should be atleast 1");
        }
    }

    private void parseFileData(String fileName) {
        FileInputStream fileInputStream = null;
        Scanner scanner = null;
        try {
            fileInputStream = new FileInputStream(fileName);
            scanner = new Scanner(fileInputStream, "UTF-8");
            while (scanner.hasNextLine()) {
                String logLine = scanner.nextLine();
                LogEntityDto logEntityDto = getLogEntityDtoObject(logLine);
                saveLogsByEvent(logEntityDto);
            }
            logIncompleteEvents();
        } catch (FileNotFoundException e) {
            log.error("Invalid file {}",fileName);
            e.printStackTrace();
            String errorMessage = "Invalid file : " + fileName +", Please pass correct file.";
            throw new RuntimeException(errorMessage);
        }
    }

    private LogEntityDto getLogEntityDtoObject(String logLine) {
        Gson gson = new Gson();
        LogEntityDto LogEntityDto = gson.fromJson(logLine, LogEntityDto.class);
        return LogEntityDto;
    }

    private void saveLogsByEvent(LogEntityDto logEntityDto) {
        if(logMap.containsKey(logEntityDto.getId())) {
            LogEntityDto existingLogDto = logMap.remove(logEntityDto.getId());
            int duration = (int) Math.abs(existingLogDto.getTimestamp()-logEntityDto.getTimestamp());
            LogEntity logEntity = createLogEntityObject(logEntityDto,duration);
            logRepository.save(logEntity);
        } else {
            logMap.put(logEntityDto.getId(),logEntityDto);
        }
    }

    private LogEntity createLogEntityObject(LogEntityDto logEntityDto, int duration) {
        boolean alert = duration>4;
        if (alert) {
            log.info("Event id {} took more than 4ms to complete",logEntityDto.getId());
        }
        LogEntity logEntity = new LogEntity(logEntityDto.getId(), duration, logEntityDto.getType(), logEntityDto.getHost(), alert);
        return logEntity;
    }

    private void logIncompleteEvents() {
        if (logMap.size()>0) {
            logMap.keySet().forEach(k -> log.info("Data for Event ID {} is incomplete", k));
        }
        log.info("Processing finished!");
    }

}
