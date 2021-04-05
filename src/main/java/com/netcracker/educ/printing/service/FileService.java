package com.netcracker.educ.printing.service;

import com.netcracker.educ.printing.exception.NotFoundException;
import com.netcracker.educ.printing.model.bean.OrderStatus;
import com.netcracker.educ.printing.model.entity.Order;
import com.netcracker.educ.printing.model.repository.OrderRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.*;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class FileService {
    private final OrderRepo orderRepo;

//    @Value("${upload.path}")
    private final String uploadPath = new File("src/main/resources/file").getAbsolutePath();

    public Order uploadFile(MultipartFile file, Order order) throws IOException {
        if (file != null && !file.isEmpty()) {
           // File uploadFolder = new File(uploadPath);
           // if (!uploadFolder.exists()) uploadFolder.mkdir();
            String uuidFile = UUID.randomUUID().toString();
            String fileName = uuidFile + "_" + file.getOriginalFilename();
            byte[] bytesArray = file.getBytes();
            order.setFile(fileName);
            order.setSchema(bytesArray);
            log.info("File {} added to order {}",fileName, order.getId());
          //  file.transferTo(new File(uploadPath + File.separator + fileName));
//            log.info("File {} was uploaded", fileName);
            return order;
        }
        return null;
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

    @Transactional
    public void returnFile(String fileName, HttpServletResponse response)  {
        Order order = orderRepo.findByFile(fileName).orElseThrow(NotFoundException::new);
       // Path filePath = Paths.get(uploadPath, File.separator, fileName);
       // if(Files.exists(filePath)) {
            response.setContentType(URLConnection.guessContentTypeFromName(fileName));
            response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
            try {
                OutputStream output = response.getOutputStream();
                output.write(order.getSchema());
//                Files.copy(filePath, response.getOutputStream());
                response.getOutputStream().flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
       // }
    }
}
