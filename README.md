
init database and table

```sql
DROP DATABASE IF EXISTS test_db;
CREATE DATABASE `test_db`; /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */
DROP TABLE IF EXISTS t_order;
CREATE TABLE t_order (
    order_id INT NOT NULL AUTO_INCREMENT, 
    user_id INT NOT NULL,
    address_id BIGINT NOT NULL,
    status VARCHAR(45) NULL, 
    PRIMARY KEY (order_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
```