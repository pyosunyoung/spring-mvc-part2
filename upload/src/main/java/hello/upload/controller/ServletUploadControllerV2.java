package hello.upload.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

@Slf4j
@Controller
@RequestMapping("/servlet/v2")
public class ServletUploadControllerV2 {

    @Value("${file.dir}") // application 파일 경로 가져옴
    private String fileDir;

    @GetMapping("/upload")
    public String newFile() {
        return "upload-form";
    }

    @PostMapping("/upload")
    public String saveFile(HttpServletRequest request) throws ServletException, IOException {
        log.info("request: {}", request);

        String itemName = request.getParameter("itemName");
        log.info("itemName: {}", itemName);

        Collection<Part> parts = request.getParts(); // getParts각각 멀티 폼에서 넘겨진 값들을 각각 받을 수 있음.
        log.info("parts: {}", parts);

        for (Part part : parts) {
            log.info("part: {}", part);
            log.info("name: {}", part.getName());
            Collection<String> headerNames = part.getHeaderNames();
            for (String headerName : headerNames) { // 또 각각의 파츠들의 헤더네임이 있음., http헤더 안에 또 다른 파트의 헤더가 있음
                log.info("headerName: {} : {}", headerName, part.getHeader(headerName));
            }
            //편의 메서드
            //content - disposition; filename 꺼내기, 원래는 filename 꺼내기가 복잡함.
            log.info("submittedFilename: {}", part.getSubmittedFileName()); // 파일이름 꺼내기
            log.info("size: {}", part.getSize()); // part의 body size

            //데이터 읽기
            InputStream inputStream = part.getInputStream();
            String body = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
//            log.info("body: {}", body); 테스트 시 힘듦 양이 많아서

            //파일에 저장하기
            if(StringUtils.hasText(part.getSubmittedFileName())) {
                String fullPath = fileDir + part.getSubmittedFileName(); // 디렉토리 + 파일 이름
                log.info("파일 저장 fullPath: {}", fullPath);
                part.write(fullPath);
            }
        }

        return "upload-form";
    }
}


//: request: org.springframework.web.multipart.support.StandardMultipartHttpServletRequest@5b3c6f71
//       : itemName: Spring
//      : parts: [org.apache.catalina.core.ApplicationPart@4f3de104, org.apache.catalina.core.ApplicationPart@3eb1f30c]
//     : name: itemName
//        : headerName: content-disposition : form-data; name="itemName"
//      : submittedFilename: null
//         : size: 6
//       : body: Spring
//        : part: org.apache.catalina.core.ApplicationPart@3eb1f30c
//  : name: file: headerName: content-disposition : form-data; name="file"; filename="ack 개발자.png"
//      : headerName: content-type : image/png
//         : submittedFilename: ack 개발자.png
//        : size: 132361
//     : body: �PNG