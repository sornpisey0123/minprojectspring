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
    CONSTRAINT categories_users_fk FOREIGN KEY (user_id) REFERENCES Users(user_id)
);