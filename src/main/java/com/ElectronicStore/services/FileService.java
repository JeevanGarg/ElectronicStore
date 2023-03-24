package com.ElectronicStore.services;


import com.ElectronicStore.exceptions.ResourceNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileService
{
    String uploadFile(MultipartFile file,String path) throws ResourceNotFoundException, IOException;

    InputStream getResource(String path,String name) throws FileNotFoundException;
}
