CREATE TABLE Users (
    user_id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL ,
    password VARCHAR(255) NOT NULL,
    profile_image VARCHAR(255) NOT NULL
);
CREATE TABLE Categories (
    category_id SERIAL PRIMARY KEY,
    name VARCHAR(35) NOT NULL,
    description VARCHAR(255) NOT NULL,
    user_id INT,
    CONSTRAINT categories_users_fk FOREIGN KEY (user_id) REFERENCES Users(user_id) ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE TABLE Otps (
    otp_id SERIAL PRIMARY KEY,
    otp_code VARCHAR(6),
    issued_at DATE,
    expiration BOOLEAN,
    verify BOOLEAN DEFAULT FALSE,
    user_id INT,
    CONSTRAINT otps_users_fk FOREIGN KEY (user_id) REFERENCES Users(user_id) ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE TABLE Expenses (
    expense_id SERIAL PRIMARY KEY,
    amount DOUBLE PRECISION,
    description VARCHAR(255),
    date DATE,
    user_id INT,
    category_id INT,
    CONSTRAINT expense_user_fk FOREIGN KEY (user_id) REFERENCES Users(user_id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT expense_category_fk FOREIGN KEY (category_id) REFERENCES Categories(category_id) ON UPDATE CASCADE ON DELETE CASCADE
);