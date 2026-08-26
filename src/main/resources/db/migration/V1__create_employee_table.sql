CREATE TABLE employee (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(50) NOT NULL,
                          department VARCHAR(30) NOT NULL,
                          salary DOUBLE,
                          image_url VARCHAR(255)
);