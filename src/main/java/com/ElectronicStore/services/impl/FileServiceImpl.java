package com.ElectronicStore.services.impl;

import com.ElectronicStore.exceptions.ResourceNotFoundException;
import com.ElectronicStore.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService
{
    @Override
    public String uploadFile(MultipartFile file, String path) throws ResourceNotFoundException, IOException {
        String originalFileName=file.getOriginalFilename();
        String fileName= UUID.randomUUID().toString();
        String extension=originalFileName.substring(originalFileName.lastIndexOf("."));

        String fileNameWithExtension=fileName+extension;
        String fullPathWithFileName=path+fileNameWithExtension;

        if(extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg")||extension.equalsIgnoreCase("jpeg"))
        {
            //upload File

            File folder=new File(path);

            if(!folder.exists())
            {
                folder.mkdirs();
            }

            Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
            return fileNameWithExtension;
        }
        else
        {
            throw new ResourceNotFoundException("File extension not found");
        }

    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException
    {
        String fullPath=path+name;
        InputStream inputStream=new FileInputStream(fullPath);
        return inputStream;

    }
}
