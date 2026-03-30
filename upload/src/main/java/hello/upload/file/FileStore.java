package hello.upload.file;


import hello.upload.domain.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String fileName) {
        return fileDir + fileName;
    }

    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFile> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if(!multipartFile.isEmpty()) {
                UploadFile uploadFile = storeFile(multipartFile);
                storeFileResult.add(uploadFile); //멀티 파트 파일이 비어있지 않으면 루프를 돌고 해당 파일을 storeFileResult에 넣음.
            }
        }
        return storeFileResult;

    } // 여러개 업로드



    public UploadFile storeFile(MultipartFile multipartFile) throws IOException { // multipartFile을 받고 UploadFile로 바꿔주는 로직.
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename(); //사용자가 업로드한 파일 이름
        //image.png

        String storeFileName = createStoreFileName(originalFilename); //서버에 저장하는 파일명 => qwe-qwe-123-qwe.png
        multipartFile.transferTo(new File(getFullPath(storeFileName))); // storeFileName과 fileDir이 합쳐짐
        return new UploadFile(originalFilename, storeFileName);

    }

    private String createStoreFileName(String originalFilename) {
        //서버에 저장하는 파일명 => qwe-qwe-123-qwe.png
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFilename);
        return  uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1); // 이렇게 하면 png(확장자)를 뽑을 수 있음.
    }
}
