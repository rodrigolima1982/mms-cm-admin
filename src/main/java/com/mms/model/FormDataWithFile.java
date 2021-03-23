package com.mms.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FormDataWithFile {

    private String name;
    private String email;
    private List<FileWithLabel> files;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<FileWithLabel> getFiles() {
        return files;
    }

    public void setFiles(List<FileWithLabel> files) {
        this.files = files;
    }
}