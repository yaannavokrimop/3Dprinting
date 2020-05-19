package com.netcracker.educ.printing.service;

import com.netcracker.educ.printing.exception.NotFoundException;
import com.netcracker.educ.printing.model.bean.OrderStatus;
import com.netcracker.educ.printing.model.bean.ResponseStatus;
import com.netcracker.educ.printing.model.entity.Material;
import com.netcracker.educ.printing.model.entity.Order;
import com.netcracker.educ.printing.model.entity.Response;
import com.netcracker.educ.printing.model.entity.User;
import com.netcracker.educ.printing.model.repository.MaterialRepo;
import com.netcracker.educ.printing.model.repository.OrderRepo;
import com.netcracker.educ.printing.model.repository.ResponseRepo;
import com.netcracker.educ.printing.model.repository.UserRepo;
import com.netcracker.educ.printing.model.representationModel.OrderRepresent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class OrderService {
    private final MaterialRepo materialRepo;
    private final UserRepo userRepo;
    private final OrderRepo orderRepo;
    private final ResponseRepo responseRepo;
    private final MaterialService materialService;
    private final ResponseService responseService;
    private final FileService fileService;

    public Order create(OrderRepresent represent, UUID userId) throws RuntimeException {
        represent.setId(UUID.randomUUID());
        OrderStatus status = OrderStatus.IN_SEARCH;
        if (represent.getFile() != null && !represent.getFile().isEmpty()) status = OrderStatus.DRAFT;
        Order order;
        if (represent.getMaterials() != null) {
            order = new Order(represent, status, new Date(), userRepo.findById(userId).orElseThrow(NotFoundException::new), materialsFromList(represent.getMaterials()));
        } else {
            order = new Order(represent, status, new Date(), userRepo.findById(userId).orElseThrow(NotFoundException::new));
        }
        Order dbOrder = orderRepo.save(order);
        log.info("User {} created order {}", userId, order.getId());
        return dbOrder;
    }

    public String addFile(MultipartFile file, UUID orderId) throws IOException {
        Order order = orderRepo.findById(orderId).orElseThrow(NotFoundException::new);
        String fileName = fileService.uploadFile(file);
        if (!fileName.equals("")) {
            order.setFile(fileName);
            orderRepo.save(order);
            log.info("File {} added to order {}", fileName, orderId);
            return fileName;
        } else return "";

    }

    public String addFileAndChangeStatus(MultipartFile file, UUID orderId) throws IOException {
        Order order = orderRepo.findById(orderId).orElseThrow(NotFoundException::new);
        String fileName = fileService.uploadFile(file);
        if (!fileName.equals("")) {
            order.setFile(fileName);
            order.setStatus(OrderStatus.IN_SEARCH);
            orderRepo.save(order);
            log.info("File {} added to order {}",fileName, orderId);
            return fileName;
        } else return "";
    }

    public Page<Order> getPageOfOrders(Map<String, String> pageParams, UUID principalId) {
        int currentPage = Integer.parseInt(pageParams.get("page")) - 1;
        int perPage = Integer.parseInt(pageParams.get("perPage"));
        String name = pageParams.get("orderName");
        Pageable page = PageRequest.of(currentPage, perPage, Sort.by("date").descending());
        if (name == null || name.isEmpty() || name.equals("null")) return orderRepo.findAllByUserId(principalId, page);
        else return orderRepo.findAllByNameContainingAndUserId(name, principalId, page);
    }

    public Order createDraft(OrderRepresent inputOrder, UUID userId) {
        User user = userRepo.findById(userId).orElseThrow(NotFoundException::new);
        inputOrder.setId(UUID.randomUUID());
        Order order;
        if (inputOrder.getMaterials() != null) {
            order = new Order(inputOrder, OrderStatus.DRAFT, new Date(), user, materialsFromList(inputOrder.getMaterials()));
        } else {
            order = new Order(inputOrder, OrderStatus.DRAFT, new Date(), user);
        }
//        order.setName(inputOrder.getName());
        Order dbOrder = orderRepo.save(order);
        log.info("User {} created orderDraft {}", userId, order.getId());
        return dbOrder;
    }

    public UUID deleteOrder(UUID orderId) {
        fileService.deleteFileByOrderId(orderId);
        orderRepo.deleteById(orderId);
        log.info("Order {}  was deleted", orderId);
        return orderId;
    }

    public Set<Material> materialsFromList(List<String> materialTitleList) {
        Set<Material> materials = new HashSet<>();
        for (String materialName : materialTitleList) {
            materials.add(materialRepo.findByMatTitle(materialName));
        }
        log.debug("Get materials from materialTitleList");
        return materials;
    }

    public OrderRepresent orderToOrderRepresent(Order order) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        String date = format.format(order.getDate());
        OrderRepresent orderRepresent = new OrderRepresent(order.getSum(),order.getHeight(), order.getWidth(), order.getLength(), order.getName());
        orderRepresent.setId(order.getId());
        orderRepresent.setDescription(order.getDescription());
        orderRepresent.setMaterials(materialService.MaterialSetToMatTitleList(order.getMaterials()));
        orderRepresent.setFile(order.getFile());
        orderRepresent.setResponsesCount(responseRepo.countDistinctByOrderId(order.getId()));
        orderRepresent.setStatus(statusToString(order.getStatus()));
        orderRepresent.setCustomerId(order.getUser().getId());
        orderRepresent.setDate(date);
        return orderRepresent;
    }

    public List<OrderRepresent> ordersToOrderRepresents(List<Order> orders) {
        List<OrderRepresent> orderRepresents = new ArrayList<>();
        for (Order order : orders) {
            orderRepresents.add(this.orderToOrderRepresent(order));
        }
        return orderRepresents;
    }

    public List<Order> getOrdersForChat(UUID chatId) {
        List<Response> chatResponses = responseService.getResponsesForChat(chatId);

        List<Order> chatOrders = new ArrayList<>();

        for (Response response : chatResponses) {
            if (response.getStatus() == ResponseStatus.AGREED) {
                chatOrders.add(response.getOrder());
            }
        }
        log.info("Get orders for chat: {}",chatId);
        return chatOrders;
    }

    public Order payOrder(UUID id) {
        Order order = orderRepo.findById(id).orElseThrow(NotFoundException::new);

        if (order.getStatus() == OrderStatus.NO_PAY) {
            order.setStatus(OrderStatus.PAYED);

            orderRepo.save(order);
            log.info("Order payed= {}", order.getId());
        }

        return order;
    }

    public Order doneOrder(UUID id) {
        Order order = orderRepo.findById(id).orElseThrow(NotFoundException::new);

        if (order.getStatus() == OrderStatus.PAYED) {
            order.setStatus(OrderStatus.DONE);

            orderRepo.save(order);
            log.info("Order done= {}", order.getId());
        }

        return order;
    }

    public Order receivedOrder(UUID id) {
        Order order = orderRepo.findById(id).orElseThrow(NotFoundException::new);

        if (order.getStatus() == OrderStatus.DONE) {
            order.setStatus(OrderStatus.RECEIVED);

            orderRepo.save(order);
            log.info("Order received= {}", order.getId());
        }

        return order;
    }

    public Order notDraftOrder(UUID id) {
        Order order = orderRepo.findById(id).orElseThrow(NotFoundException::new);

        if (order.getStatus() == OrderStatus.DRAFT) {
            order.setStatus(OrderStatus.IN_SEARCH);

            orderRepo.save(order);
            log.info("Order not a Draft anymore= {}", order.getId());
        }
        return order;
    }

    public OrderRepresent getOrderById(UUID id) {
        Optional<Order> orderData = orderRepo.findById(id);
        return orderToOrderRepresent(orderData.orElseThrow(NotFoundException::new));
    }

    public Order updateOrder(OrderRepresent inputOrderRepresent, UUID orderId) {
        Order dbOrder = orderRepo.findById(orderId).orElseThrow(NullPointerException::new);
        Order inputOrder = new Order(inputOrderRepresent, dbOrder.getDate(), dbOrder.getUser(), dbOrder.getStatus(), materialsFromList(inputOrderRepresent.getMaterials()));
        BeanUtils.copyProperties(inputOrder, dbOrder, "user");
        Order saveOrder = orderRepo.save(dbOrder);
        log.info("Updated order {}", saveOrder.getId());
        return saveOrder;
    }

    public String statusToString(OrderStatus status) {
        switch (status) {
            case DRAFT :
                return "Черновик";
            case IN_SEARCH:
                return "В поиске исполнителя";
            case NO_PAY :
                return "Не оплачено";
            case PAYED :
                return "Оплачено";
            case DONE :
                return "Сделано";
            case RECEIVED :
                return "Заказ получен";
        }
        return "Статус не найден";
    }
}
