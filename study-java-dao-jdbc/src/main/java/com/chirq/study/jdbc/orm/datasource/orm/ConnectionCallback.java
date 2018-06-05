package com.chirq.study.jdbc.orm.datasource.orm;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionCallback<T> {
    T doInConnection(Connection con) throws SQLException ;
}
