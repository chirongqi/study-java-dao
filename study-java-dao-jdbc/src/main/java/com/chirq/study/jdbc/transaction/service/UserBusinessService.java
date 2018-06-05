package com.chirq.study.jdbc.transaction.service;

import com.chirq.study.jdbc.simplejdbc.entity.User;
import com.chirq.study.jdbc.transaction.entity.Payment;

public interface UserBusinessService {

    void saveUserAndPayment(User user, Payment payment);

}
