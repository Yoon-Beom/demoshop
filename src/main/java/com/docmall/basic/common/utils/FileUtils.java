package com.docmall.basic.common.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;

/**
 * 파일 업로드, 다운로드, 삭제 등 파일 관련 유틸리티 기능을 제공하는 클래스입니다.
 *
 * - @Component로 빈으로 등록하여 어디서든 주입받아 사용할 수 있습니다.
 * - 이미지 파일 업로드 시 썸네일 자동 생성, 날짜별 폴더 구조 지원
 * - 외부 경로 파일의 안전한 다운로드, 삭제 지원
 * - Thumbnailator 라이브러리 사용: https://github.com/coobird/thumbnailator
 *
 * @author main
 * @since 2025.05.01
 */
@Component // 스프링 부트가 시작되면 Bean으로 등록됨
public class FileUtils {

    /**
     * 오늘 날짜로 폴더명을 생성합니다. (예: 2024/11/18)
     *
     * @return 날짜 폴더명 (yyyy/MM/dd 형식)
     */
    public String getDateFolder() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String str = sdf.format(date);
        // 운영체제에 상관없이 '/'로 경로 구분
        return str.replace("-", "/");
    }

    /**
     * 파일이 이미지 타입인지 확인합니다.
     *
     * @param saveFile 검사할 파일 객체
     * @return 이미지 파일이면 true, 아니면 false
     */
    public boolean checkImageType(File saveFile) {
        boolean isImageType = false;
        try {
            // MIME Type 예: image/jpeg, image/png 등
            String contentType = Files.probeContentType(saveFile.toPath());
            isImageType = contentType.startsWith("image");
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return isImageType;
    }

    /**
     * 파일 업로드 및 썸네일 이미지 생성
     *
     * @param uploadFolder 업로드 루트 경로 (예: C:/Dev/upload/pds)
     * @param dateFolder 날짜 폴더명 (예: 2024/11/18)
     * @param uploadFile 업로드할 파일(MultipartFile)
     * @return 실제 저장된 파일명 (UUID_원본파일명)
     */
    public String uploadFile(String uploadFolder, String dateFolder, MultipartFile uploadFile) {
        String realUploadFileName = "";

        // 업로드할 폴더 생성 (없으면 생성)
        File file = new File(uploadFolder, dateFolder);
        if(!file.exists()) {
            file.mkdirs();
        }

        // 업로드 파일명에 UUID를 붙여서 유일하게 저장
        String clientFileName = uploadFile.getOriginalFilename();
        UUID uuid = UUID.randomUUID();
        realUploadFileName = uuid.toString() + "_" + clientFileName;

        try {
            File saveFile = new File(file, realUploadFileName);
            uploadFile.transferTo(saveFile); // 실제 파일 저장

            // 이미지 파일이면 썸네일 생성
            if(checkImageType(saveFile)) {
                File thumbnailFile = new File(file, "s_" + realUploadFileName);
                BufferedImage bo_img = ImageIO.read(saveFile);
                double ratio = 3;
                int width = (int) (bo_img.getWidth() / ratio);
                int height = (int) (bo_img.getHeight() / ratio);

                // 썸네일 생성(Thumbnailator 라이브러리 사용)
                Thumbnails.of(saveFile)
                          .size(width, height)
                          .toFile(thumbnailFile);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return realUploadFileName;
    }

    /**
     * 외부 폴더에 저장된 파일을 바이트 배열로 읽어서 클라이언트에 전송합니다.
     * (보안상 직접 접근 대신 ResponseEntity<byte[]>로 파일 제공)
     *
     * @param uploadPath 업로드 루트 경로
     * @param fileName 파일 경로(날짜폴더/파일명)
     * @return 파일 데이터와 Content-Type 헤더를 포함한 ResponseEntity
     * @throws Exception 파일이 없거나 읽기 실패 시
     */
    public ResponseEntity<byte[]> getFile(String uploadPath, String fileName) throws Exception {
        ResponseEntity<byte[]> entity = null;
        File file = new File(uploadPath, fileName);

        if(!file.exists()) {
            return entity;
        }

        // 파일의 MIME 타입을 헤더에 추가
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", Files.probeContentType(file.toPath()));

        entity = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);
        return entity;
    }

    /**
     * 업로드된 파일(및 썸네일) 삭제
     *
     * @param uploadPath 업로드 루트 경로
     * @param dateFolderName 날짜 폴더명
     * @param fileName 파일명 (썸네일은 's_' 접두사)
     * @param type 파일 타입 (image면 원본/썸네일 모두 삭제)
     */
    public void delete(String uploadPath, String dateFolderName, String fileName, String type) {
        // 파일 경로 생성(운영체제별 구분자 처리)
        File file1 = new File((uploadPath + "\\" + dateFolderName + "\\" + fileName).replace('\\', File.separatorChar));
        if(file1.exists()) file1.delete();

        // 이미지 파일이면 원본 이미지도 삭제
        if(type.equals("image")) {
            File file2 = new File((uploadPath + "\\" + dateFolderName + "\\" + fileName.substring(2)).replace('\\', File.separatorChar));
            if(file2.exists()) file2.delete();
        }
    }
}
