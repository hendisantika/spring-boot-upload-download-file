package com.hendisantika.springbootuploaddownloadfile.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    @PostMapping("/upload")
    public String singleFileUpload(@RequestParam("file") MultipartFile file, Model model) {
        if (file.isEmpty()) {
            model.addAttribute("warning",
                    "Please select a file to upload");
            return "upload";
        }

        try {

            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER +
                    file.getOriginalFilename());
            Files.write(path, bytes);

            model.addAttribute("message",
                    "You successfully uploaded '"
                            + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            model.addAttribute("error", "Error");
            return "upload";
        }

        List<String> list = new ArrayList<String>();
        File files = new File(UPLOADED_FOLDER);
        String[] fileList = files.list();
        Collections.addAll(list, fileList);
        model.addAttribute("list", list);
        return "index";
    }

    @GetMapping(path = "/download/{name}")
    public ResponseEntity<Resource> download(@PathVariable("name") String name) throws IOException {

        File file = new File(UPLOADED_FOLDER + name);
        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource
                (Files.readAllBytes(path));

        return ResponseEntity.ok().headers(this.headers(name))
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType
                        ("application/octet-stream")).body(resource);
    }

    @PostMapping(path = "/delete")
    public String delete(@RequestParam("name") String name) throws IOException {
        try {
            Files.deleteIfExists(Paths.get(UPLOADED_FOLDER + name));
        } catch (IOException e) {
            return "redirect:/";
        }
        return "redirect:/";
    }
}
