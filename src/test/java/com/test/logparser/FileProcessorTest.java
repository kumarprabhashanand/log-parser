package com.test.logparser;

import com.test.logparser.repository.LogRepository;
import com.test.logparser.service.FileProcessor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
@EnableAutoConfiguration
public class FileProcessorTest {

    @Autowired
    private FileProcessor fileProcessor;

    @Autowired
    private LogRepository logRepository;

    @Configuration
    public static class Config {
        @Bean
        public FileProcessor getFileProcessor() {
            return new FileProcessor();
        }
    }

    @Test
    void sendInvalidFile_getRunTimeException() {
        assertThrows(RuntimeException.class, () -> fileProcessor.processInputFileLogs(null));
        String[] inputFileNameArgs = new String[1];
        inputFileNameArgs[0] = "abcd.txt";
        assertThrows(RuntimeException.class, () -> fileProcessor.processInputFileLogs(inputFileNameArgs));
    }

    @Test
    void sendFileWithTwoCompleteEvents_getTwoRowsInDatabase() {
        String[] inputFileNameArgs = new String[1];
        inputFileNameArgs[0] = "src/test/resources/logfile.txt";
        fileProcessor.processInputFileLogs(inputFileNameArgs);
        assertEquals(2, logRepository.count());
    }


    @Test
    void sendFileWithThreeIncompleteEvents_getTwoRowsInDatabase() {
        String[] inputFileNameArgs = new String[1];
        inputFileNameArgs[0] = "src/test/resources/logfile1.txt";
        fileProcessor.processInputFileLogs(inputFileNameArgs);
        assertEquals(2, logRepository.count());
    }


}
