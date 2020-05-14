package com.netcracker.educ.printing.controller;

import com.netcracker.educ.printing.exception.NotFoundException;
import com.netcracker.educ.printing.model.bean.PaginationBean;
import com.netcracker.educ.printing.model.entity.Order;
import com.netcracker.educ.printing.model.repository.OrderRepo;
import com.netcracker.educ.printing.model.representationModel.OrderRepresent;
import com.netcracker.educ.printing.security.UserDetailsImpl;
import com.netcracker.educ.printing.service.FileService;
import com.netcracker.educ.printing.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Slf4j
@RestController
@RequestMapping("/api/order")
@AllArgsConstructor
public class OrderController {

    private final OrderRepo orderRepo;
    private final OrderService orderService;
    private final FileService fileService;

    @GetMapping("/user")
    public ResponseEntity<PaginationBean> getOrdersByUserId(@RequestParam Map<String, String> pageParams, @AuthenticationPrincipal UserDetailsImpl principal) {
        log.debug("Get orders for user {}",principal.getId());
        Page<Order> ordersPage = orderService.getPageOfOrders(pageParams, principal.getId());
        List<OrderRepresent> orders = orderService.ordersToOrderRepresents(ordersPage.getContent());
        return ResponseEntity.ok(new PaginationBean(ordersPage.getTotalPages(), orders));
    }

    @GetMapping("{id}")
    public OrderRepresent getOrderById(@PathVariable("id") UUID id) {
        log.debug("Get order by id= {}", id);
        return orderService.getOrderById(id);
    }

    @GetMapping("/file/{fileName}")
    public void returnFile(@PathVariable("fileName") String fileName, HttpServletResponse response) {
        fileService.returnFile(fileName, response);
    }

    @GetMapping("forchat/{chatId}")
    public List<Order> getOrdersForChat(@PathVariable(name = "chatId") UUID chatId) {
        log.debug("Get Order by chat id= {}", chatId);
        return orderService.getOrdersForChat(chatId);
    }

    @PostMapping
    public Order createOrder(@RequestBody OrderRepresent inputOrder, @AuthenticationPrincipal UserDetailsImpl details) {
        log.debug("User {} create order {}",details.getId(),inputOrder.getName());
        return orderService.create(inputOrder, details.getId());
    }

    @PostMapping("/file")
    public ResponseEntity<String> addFileToOrder(@RequestParam("file")MultipartFile file, @RequestParam("orderId") UUID orderId) {
        String result;
        try {
            result = orderService.addFileToOrder(file, orderId);
        }catch (IOException ex) {
            return ResponseEntity.badRequest().body("Не удалось загрузить файл. Попробуйте ещё раз.");
        } catch (NotFoundException ex) {
            return ResponseEntity.badRequest().body("Не удалось прикрепить файл. Заказ не найден.");
        }
        if (!result.equals("")) return ResponseEntity.ok("Файл успешно загружен.");
        else return ResponseEntity.badRequest().body("Фвйл пустой. Выберите другой файл.");
    }

    @PostMapping("/draft")
    public Order createDraft(@RequestBody OrderRepresent inputOrder, @AuthenticationPrincipal UserDetailsImpl details) {
        log.debug("User {} create orderDraft {}",details.getId(),inputOrder.getName());
        return orderService.createDraft(inputOrder, details.getId());
    }

    @PostMapping("/draft/file")
    public ResponseEntity<String> addFileToDraft(@RequestParam("file")MultipartFile file, @RequestParam("orderId") UUID orderId) {
        boolean result;
        try {
            result = orderService.addFileToDraft(file, orderId);
        }catch (IOException ex) {
            return ResponseEntity.badRequest().body("Не удалось загрузить файл. Попробуйте ещё раз.");
        } catch (NotFoundException ex) {
            return ResponseEntity.badRequest().body("Не удалось прикрепить файл. Черновик не найден.");
        }
        if (result) return ResponseEntity.ok("Файл успешно загружен.");
        else return ResponseEntity.badRequest().body("Фвйл пустой. Выберите другой файл.");
    }

    @PutMapping("{id}")
    public Order updateOrder(
            @RequestBody OrderRepresent inputOrder,
            @PathVariable("id") UUID orderId) {
        log.debug("Update order {}", orderId);
        return orderService.updateOrder(inputOrder,orderId);
    }

    @PutMapping("/file/{orderId}")
    public ResponseEntity<String> updateFile (@PathVariable UUID orderId, @RequestBody MultipartFile file) {
        fileService.deleteFileByOrderId(orderId);
        String result;
        try {
            result = orderService.addFileToOrder(file, orderId);
        }catch (IOException ex) {
            return ResponseEntity.badRequest().body("Не удалось изменить файл. Попробуйте ещё раз.");
        } catch (NotFoundException ex) {
            return ResponseEntity.badRequest().body("Не удалось изменить файл. Заказ не найден.");
        }
        if (!result.equals("")) return ResponseEntity.ok(result);
        else return ResponseEntity.badRequest().body("Фвйл пустой. Выберите другой файл.");
    }

    @DeleteMapping("{id}")
    public UUID deleteOrder(@PathVariable("id") UUID id) {
        log.debug("Delete order {}",id);
        return orderService.deleteOrder(id);
    }

    @DeleteMapping("/file/{orderId}")
    public void deleteFile(@PathVariable UUID orderId) {
        log.debug("Delete file from order {}", orderId);
        fileService.deleteFileByOrderId(orderId);
    }

    @PatchMapping("/pay/{id}")
    public Order payOrder(@PathVariable("id") UUID id) {
        log.debug("Pay order {}",id);
        return orderService.payOrder(id);
    }

    @PatchMapping("/done/{id}")
    public Order doneOrder(@PathVariable("id") UUID id) {
        log.debug("Done order {}",id);
        return orderService.doneOrder(id);
    }

    @PatchMapping("/receive/{id}")
    public Order receivedOrder(@PathVariable("id") UUID id) {
        log.debug("Receive order {}",id);
        return orderService.receivedOrder(id);
    }

    @PatchMapping("/notDraft/{id}")
    public Order notDraft(@PathVariable("id") UUID id) {
        log.debug("Not Draft order {}",id);
        return orderService.notDraftOrder(id);
    }
}
