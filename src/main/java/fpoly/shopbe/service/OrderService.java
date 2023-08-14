package fpoly.shopbe.service;

import fpoly.shopbe.DTO.OrderDto;
import fpoly.shopbe.domain.*;
import fpoly.shopbe.exception.OrderDetailException;
import fpoly.shopbe.exception.OrderException;
import fpoly.shopbe.repository.OrderDetailRepository;
import fpoly.shopbe.repository.OrderRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    OrderRepository dao;

    @Autowired
    OrderDetailRepository deDao;

    @Transactional(rollbackOn = Exception.class)
    public OrderDto insterOrder(OrderDto dto) {
        if (dto.getOrderDetails().size() < 1) {
            throw new OrderException("Order Detail null");
        }

        Order entity = new Order();
        BeanUtils.copyProperties(dto, entity, "orderDetails");

        Customers cus = new Customers();
        cus.setId(dto.getCusId());

        entity.setCustomers(cus);

        var newEntity = dao.save(entity);
        dto.setId(newEntity.getId());

        var newOdd = dto.getOrderDetails().stream().map((item) ->{
            OrderDetail odd = new OrderDetail();
            BeanUtils.copyProperties(item, odd);
            Order od = new Order();
            od.setId(newEntity.getId());
            odd.setOrder(od);
            Product pr = new Product();
            pr.setId(item.getProductId());
            odd.setProduct(pr);
            var saveOdd = deDao.save(odd);

            return saveOdd;
        }).collect(Collectors.toList());

        newEntity.setOrderDetails(newOdd);

        dao.save(newEntity);

        return dto;
    }

    @Transactional(rollbackOn = Exception.class)
    public void deleteOdd(Long id){
        var found = dao.findById(id).orElseThrow(()-> new OrderException("Order not found"));

        dao.delete(found);
    }
    @Transactional(rollbackOn = Exception.class)
    public void deleteOrderDetail(Long id){
        var found = deDao.findById(id).orElseThrow(()->new OrderDetailException("OrderDetail not found"));
        deDao.delete(found);
    }
    public List findAll(){
        return (List) dao.findAll();
    }

    public List getListByCus(Long id){
        return dao.findByCustomers_Id(id);
    }

    public List getListOrderDetailByOrderId(Long id){
        return deDao.findByOrder_Id(id);
    }



}
