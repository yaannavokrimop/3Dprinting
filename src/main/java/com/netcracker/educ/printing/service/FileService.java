package com.netcracker.educ.printing.service;

import com.netcracker.educ.printing.exception.NotFoundException;
import com.netcracker.educ.printing.model.entity.Order;
import com.netcracker.educ.printing.model.repository.OrderRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileService {
    private final OrderRepo orderRepo;

//    @Value("${upload.path}")
    private String uploadPath = new File("src/main/resources/file").getAbsolutePath();

    public String uploadFile(MultipartFile file) throws IOException {
        System.out.println(uploadPath);
        if (file != null && !file.isEmpty()) {
            File uploadFolder = new File(uploadPath);
            if (!uploadFolder.exists()) uploadFolder.mkdir();
            String uuidFile = UUID.randomUUID().toString();
            String fileName = uuidFile + "_" + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + File.separator + fileName));
            log.info("File {} was uploaded", fileName);
            return fileName;
        }
        return "";
    }

    public boolean deleteFileByOrderId(UUID orderId) {
        Order order = orderRepo.findById(orderId).orElseThrow(NotFoundException::new);
        String fileName = order.getFile();
        File file = new File(uploadPath + File.separator + fileName);
        if (file.delete()) {
            order.setFile(null);
            orderRepo.save(order);
            log.info("File {} was deleted from order {}", fileName, orderId);
            return true;
        } else return false;
    }

    public void returnFile(String fileName, HttpServletResponse response) {
        Path filePath = Paths.get(uploadPath, File.separator, fileName);
        if(Files.exists(filePath)) {
            response.setContentType(URLConnection.guessContentTypeFromName(fileName));
            response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
            try {
                Files.copy(filePath, response.getOutputStream());
                response.getOutputStream().flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
