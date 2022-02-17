package com.example.shardingjdbcdemo.repository;

import com.example.shardingjdbcdemo.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private JdbcTemplate jdbcTemplate;

    private DataSource readWriteDataSource;

    private JdbcTemplate jdbcTemplateWithRawDataSource;

    private String INSERT_SQL = "INSERT INTO t_order (user_id, address_id, status) VALUES (?, ?, ?)";


    @Override
    public long jdbcTemplateTest(Order order) {
        long id = 0L;
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update((conn) -> {
                PreparedStatement ps = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, order.getUserId());
                ps.setLong(2, order.getAddressId());
                ps.setString(3, order.getStatus());
                return ps;
            }, keyHolder);

            id = keyHolder.getKey().longValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public long rawJdbcTest(Order order) {
        long id = 0;
        try (Connection connection = readWriteDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, order.getUserId());
            ps.setLong(2, order.getAddressId());
            ps.setString(3, order.getStatus());

            ps.executeUpdate();
            try (ResultSet resultSet = ps.getGeneratedKeys()) {
                if (resultSet.next()) {
                    id = resultSet.getLong(1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

    @Override
    public long JdbcTemplateWithRawDataSourceTest(Order order) {
        long id = 0L;
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplateWithRawDataSource.update((conn) -> {
                PreparedStatement ps = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, order.getUserId());
                ps.setLong(2, order.getAddressId());
                ps.setString(3, order.getStatus());
                return ps;
            }, keyHolder);

            id = keyHolder.getKey().longValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }


    @Autowired
    public OrderRepositoryImpl(JdbcTemplate jdbcTemplate, DataSource readWriteDataSource, JdbcTemplate jdbcTemplateWithRawDataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.readWriteDataSource = readWriteDataSource;
        this.jdbcTemplateWithRawDataSource = jdbcTemplateWithRawDataSource;
    }
}
