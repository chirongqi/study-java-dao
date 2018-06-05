package com.chirq.study.jdbc.transaction.service.impl;

import com.chirq.study.jdbc.simplejdbc.entity.User;
import com.chirq.study.jdbc.transaction.dao.PaymentDao;
import com.chirq.study.jdbc.transaction.dao.UserTransationsDao;
import com.chirq.study.jdbc.transaction.entity.Payment;
import com.chirq.study.jdbc.transaction.service.UserBusinessService;

public class UserBusinessServiceImpl implements UserBusinessService {
    PaymentDao paymentDao = new PaymentDao();

    UserTransationsDao userTransationsDao = new UserTransationsDao();

    @Override
    public void saveUserAndPayment(User user, Payment payment) {
        userTransationsDao.saveUser(user);
        paymentDao.savePayment(payment);
    }

}
