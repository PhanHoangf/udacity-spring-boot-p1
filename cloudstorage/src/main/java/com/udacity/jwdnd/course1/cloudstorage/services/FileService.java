package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {
    private FileMapper fileMapper;
    private UserMapper userMapper;

    public FileService(FileMapper fileMapper, UserMapper userMapper) {
        this.fileMapper = fileMapper;
        this.userMapper = userMapper;
    }

    public List<File> getFiles(Integer userid) {
        return fileMapper.getFilesByUserid(userid);
    }

    public Integer insertFile(File file) {
        return fileMapper.insertFile(file);
    }

    public boolean isFileExists(String filename) {
        return fileMapper.getFileByFilename(filename) != null;
    }

    public Integer deleteFile(Integer fileid) {
        return fileMapper.deleteFile(fileid);
    }

    public ResponseEntity<Resource> downloadFile(String filename) {
        File file = fileMapper.getFileByFilename(filename);
        // Get the file data as a byte array
        byte[] fileData = file.getFiledata();

        // Create a ByteArrayResource from the file data
        ByteArrayResource resource = new ByteArrayResource(fileData);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContenttype()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .body(resource);
    }
}
