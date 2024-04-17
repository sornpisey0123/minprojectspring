CREATE TABLE Users (
    user_id UUID PRIMARY KEY DEFAULT GEN_RANDOM_UUID(),
    email VARCHAR(255) NOT NULL ,
    password VARCHAR(255) NOT NULL,
    profile_image VARCHAR(255) NOT NULL
);

CREATE TABLE Categories (
    category_id UUID PRIMARY KEY DEFAULT GEN_RANDOM_UUID(),
    name VARCHAR(35) NOT NULL,
    description VARCHAR(255) NOT NULL,
    user_id UUID,
    CONSTRAINT categories_users_fk FOREIGN KEY (user_id) REFERENCES Users(user_id) ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE Otps (
    otp_id UUID PRIMARY KEY DEFAULT GEN_RANDOM_UUID(),
    otp_code VARCHAR(6),
    issued_id TIMESTAMP,
    expiration TIMESTAMP,
    verify BOOLEAN,
    user_id UUID,
    CONSTRAINT otps_users_fk FOREIGN KEY (user_id) REFERENCES Users(user_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Expenses (
    expense_id UUID PRIMARY KEY DEFAULT GEN_RANDOM_UUID(),
    amount DOUBLE PRECISION,
    description VARCHAR(255),
    date DATE,
    user_id UUID,
    category_id UUID,
    CONSTRAINT expense_user_fk FOREIGN KEY (user_id) REFERENCES Users(user_id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT expense_category_fk FOREIGN KEY (category_id) REFERENCES Categories(category_id) ON UPDATE CASCADE ON DELETE CASCADE
);