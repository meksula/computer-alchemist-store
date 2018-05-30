package com.computeralchemist.store.repository.history;

import com.computeralchemist.store.domain.store.order.Order;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author
 * Karol Meksuła
 * 30-05-2018
 * */

@Repository
public class HistoryRepositoryImpl implements HistoryRepository {
    private JdbcOperations jdbcOperations;
    private static final String TABLE_NAME = "order_history";
    private final String SAVE_ORDER = "INSERT INTO " + TABLE_NAME + " (order_id, customer_id, customer_username, store_id, store_name, realize_date) " +
            "values(?,?,?,?,?,?)";
    private final String FIND_BY_ORDER_ID = "SELECT * FROM " + TABLE_NAME + " WHERE order_id=?";
    private final String FIND_BY_STORE_NAME = "SELECT * FROM " + TABLE_NAME + " WHERE store_name=?";
    private final String DELETE_ALL = "DELETE FROM " + TABLE_NAME;

    public HistoryRepositoryImpl(JdbcOperations jdbcTemplate) {
        this.jdbcOperations = jdbcTemplate;
    }

    private void createTable() {
        String createTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
                + "  (order_id          INTEGER,"
                + "   customer_id       INTEGER,"
                + "   customer_username VARCHAR(25),"
                + "   store_id          INTEGER,"
                + "   store_name        VARCHAR(30),"
                + "   realize_date      VARCHAR(50))";

        jdbcOperations.execute(createTable);
    }

    @Override
    public Order saveToHistory(Order order) {
        createTable();

        jdbcOperations.update(SAVE_ORDER,
                order.getOrderId(),
                order.getCustomersId(),
                order.getCustomersUsername(),
                order.getStoreId(),
                order.getStoreName(),
                String.valueOf(LocalDate.now()));
        return order;
    }

    @Override
    public Order findOrder(long orderId) {
        Order order = new Order();

        jdbcOperations.queryForObject(FIND_BY_ORDER_ID,
                ((rs, rowNum) -> {
                    order.setOrderId((long) rs.findColumn("order_id"));
                    order.setCustomersId((long) rs.findColumn("customer_id"));
                    order.setCustomersUsername(rs.getString("customer_username"));
                    order.setStoreId((long) rs.findColumn("store_id"));
                    order.setStoreName(rs.getString("store_name"));
                    order.setRealizeDate(rs.getString("realize_date"));
                    return order;
                }), orderId);

        return order;
    }

    @Override
    public void clearHistory() {
        jdbcOperations.execute(DELETE_ALL);
    }

    @Override
    public List<Order> findAllByStorename(String storeName) {
        List<Order> ordersList = new ArrayList<>();

        jdbcOperations.queryForObject(FIND_BY_STORE_NAME,
                ((rs, rowNum) -> {
                    do {
                        Order order = new Order();
                        order.setOrderId((long) rs.findColumn("order_id"));
                        order.setCustomersId((long) rs.findColumn("customer_id"));
                        order.setCustomersUsername(rs.getString("customer_username"));
                        order.setStoreId((long) rs.findColumn("store_id"));
                        order.setStoreName(rs.getString("store_name"));
                        order.setRealizeDate(rs.getString("realize_date"));
                        ordersList.add(order);
                    } while (rs.next());
                    return ordersList;
                }), storeName);
        return ordersList;
    }
}
