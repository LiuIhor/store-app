package com.example.finalprojectstoreapp.services;

import com.example.finalprojectstoreapp.dtos.order.OrderDto;
import com.example.finalprojectstoreapp.dtos.order.OrderListDto;
import com.example.finalprojectstoreapp.models.User;

import java.util.List;

public interface OrderService {

    /**
     * Method to get order`s items
     *
     * @param user Order owner
     * @return list of Item
     */
    OrderListDto listOrderItems(User user);

    /**
     * Method to finish all user`s order
     *
     * @param user Order owner
     * @return List of changed orders
     */
    List<OrderDto> orderAllFinish(User user);

    /**
     * Method to cancel all user`s order
     *
     * @param user Order owner
     * @return List of changed orders
     */
    List<OrderDto> orderAllCancel(User user);

    /**
     * Method to cancel user`s order
     *
     * @param user    Order owner
     * @param orderId Id of the order to be canceled
     * @return Order with status cancel
     */
    OrderDto orderCancel(User user, Long orderId);

    /**
     * Method to finish user`s order
     *
     * @param user    Order owner
     * @param orderId Id of the order to be finished
     * @return Order with status finish
     */
    OrderDto orderFinish(User user, Long orderId);

    /**
     * Method to finish user`s order. Method for user with role Manager or Admin
     *
     * @param orderId Id of the order to be finished
     * @return Order with status finish
     */
    OrderDto orderFinish(Long orderId);

    /**
     * Method to cancel user`s order. Method for user with role Manager or Admin
     *
     * @param orderId Id of the order to be canceled
     * @return Order with status cancel
     */
    OrderDto orderCancel(Long orderId);
}