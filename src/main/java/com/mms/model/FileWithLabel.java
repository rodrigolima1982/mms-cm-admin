package com.mms.model;

import org.springframework.web.multipart.MultipartFile;

public class FileWithLabel {
    private String label;
    private MultipartFile file;


    public FileWithLabel() {
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
