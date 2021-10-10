# log-parser

To Run this project, simply execute below commands:
```
./gradlew clean build
```
```
java -jar build/libs/log-parser-0.0.1-SNAPSHOT.jar "filename with path"
```

Example:
```
java -jar build/libs/log-parser-0.0.1-SNAPSHOT.jar /Users/prabhashkumar/Desktop/logfile.txt
```

Generate Test Report:
```
./gradlew test jacocoTestReport
```
Test report will be generated at location : _build/reports/jacoco/test/html/index.html_


Next Steps:
- Add some more tests that I skipped like checking logs for incomplete events
- Think of adding some async logic for processing file data and writing it in database
- Think of adding batch kind of thing to send more than one record at a time in database
