package com.hendisantika.springbootuploaddownloadfile.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-upload-download-file
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 9/29/23
 * Time: 18:07
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class FileController {

    // Save the uploaded file to this folder
    private static final String UPLOADED_FOLDER = "/home/hendisantika/Desktop/files/";

    @GetMapping("/")
    public String index(Model model) {
        List<String> list = new ArrayList<String>();
        File files = new File(UPLOADED_FOLDER);
        String[] fileList = files.list();
        Collections.addAll(list, fileList);
        model.addAttribute("list", list);
        return "index";
    }
}
