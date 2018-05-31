package com.chirq.study.jdbc;

import java.math.BigDecimal;
import java.util.Date;

import com.chirq.study.jdbc.simplejdbc.entity.User;
import com.chirq.study.jdbc.transaction.entity.Payment;
import com.chirq.study.jdbc.transaction.service.TransactionService;

import junit.framework.TestCase;

public class TransactionTest extends TestCase {
    TransactionService transactionService = new TransactionService();

    public void testSaveUserAndPayment() {
        User user = new User();
        user.setAddress("事务测试");
        user.setAge(11);
        user.setName("事务");

        Payment payment = new Payment();
        payment.setPayMoney(new BigDecimal("100.1"));
        payment.setPayMsg("付款");
        payment.setPayStatus(1);
        payment.setPayTime(new Date());
        payment.setUserId(1);
        transactionService.saveUserAndPayment(user, payment);
    }
}
