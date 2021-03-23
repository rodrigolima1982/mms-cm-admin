package com.mms.controller;

import com.mms.model.FormDataWithFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class FileUploadController {
    @RequestMapping(value = "/api/mms-sender/uploadFileModelAttribute", method = RequestMethod.POST)
    public String submit(@ModelAttribute FormDataWithFile formDataWithFile, ModelMap modelMap) {

        modelMap.addAttribute("formDataWithFile", formDataWithFile);
        return "fileUploadView";
    }
}
