package loan.ppcat.godsky.controller.controller;

import loan.ppcat.godsky.controller.model.LogInfo;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

@Controller
@RequestMapping("/log")
public class LogController {
    private Logger logger = Logger.getLogger(LogController.class);

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public LogInfo getLogInfo() {
        File file = new File("/opt/logs/controller-mvc-controller.log");
        InputStreamReader inputStreamReader = null;
        FileInputStream fileInputStream = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            fileInputStream = new FileInputStream(file);
            inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = "";
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                }
            }
        }
        LogInfo logInfo = new LogInfo();
        logInfo.setName("controller-mvc-controller.log");
        logInfo.setData(stringBuilder.toString());
        return logInfo;
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public ResponseEntity<byte[]> download(String name) throws IOException {
        logger.info("I want to print log ; ok -------------");
        File file = new File(name);
        HttpHeaders headers = new HttpHeaders();
        String fileName = new String(name.getBytes("UTF-8"), "iso-8859-1");//为了解决中文名称乱码问题
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
                headers, HttpStatus.CREATED);
    }
}
