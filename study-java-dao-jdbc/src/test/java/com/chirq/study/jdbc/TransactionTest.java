package com.chirq.study.jdbc;

import java.math.BigDecimal;
import java.util.Date;

import com.chirq.study.jdbc.simplejdbc.entity.User;
import com.chirq.study.jdbc.transaction.entity.Payment;
import com.chirq.study.jdbc.transaction.proxy.TransactionProxyFactory;
import com.chirq.study.jdbc.transaction.service.UserBusinessService;
import com.chirq.study.jdbc.transaction.service.impl.UserBusinessServiceImpl;

import junit.framework.TestCase;

public class TransactionTest extends TestCase {

    UserBusinessService userBusinessService = TransactionProxyFactory.getService(UserBusinessService.class, new UserBusinessServiceImpl());

    
    public void testSaveUserAndPaymentNotProxy() {
        User user = new User();
        user.setAddress("事务测试");
        user.setAge(1);
        user.setName("事务");

        Payment payment = new Payment();
        payment.setPayMoney(new BigDecimal("100.1"));
        payment.setPayMsg("付款");
        payment.setPayStatus(1333333);
        payment.setPayTime(new Date());
        payment.setUserId(1);
        new UserBusinessServiceImpl().saveUserAndPayment(user, payment);

    }

    public void testSaveUserAndPayment() {
        System.out.println(userBusinessService.getClass().getName());
        User user = new User();
        user.setAddress("事务测试");
        user.setAge(1);
        user.setName("事务");

        Payment payment = new Payment();
        payment.setPayMoney(new BigDecimal("100.1"));
        payment.setPayMsg("付款");
        payment.setPayStatus(1333333);
        payment.setPayTime(new Date());
        payment.setUserId(1);
        userBusinessService.saveUserAndPayment(user, payment);
    }
}
