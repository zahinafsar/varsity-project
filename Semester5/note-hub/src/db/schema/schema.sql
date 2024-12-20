-- db database
CREATE DATABASE IF NOT EXISTS note_hub_db;

-- user table
CREATE TABLE IF NOT EXISTS user (
    id INT NOT NULL AUTO_INCREMENT,
    student_id VARCHAR(255) NOT NULL,
    avatar VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY (student_id)
);

-- note table
CREATE TABLE IF NOT EXISTS note (
    id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255) NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_note_user_id FOREIGN KEY (user_id) REFERENCES user (id)
);

-- image table
CREATE TABLE IF NOT EXISTS image (
    id INT NOT NULL AUTO_INCREMENT,
    note_id INT NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_image_note_id FOREIGN KEY (note_id) REFERENCES note (id)
);

-- saved_note table
CREATE TABLE IF NOT EXISTS saved_note (
    id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    note_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_saved_note_user_id FOREIGN KEY (user_id) REFERENCES user (id),
    CONSTRAINT fk_saved_note_note_id FOREIGN KEY (note_id) REFERENCES note (id)
);

-- comment table
CREATE TABLE IF NOT EXISTS comment (
    id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    note_id INT NOT NULL,
    content VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_comment_user_id FOREIGN KEY (user_id) REFERENCES user (id),
    CONSTRAINT fk_comment_note_id FOREIGN KEY (note_id) REFERENCES note (id)
);


-- like table
CREATE TABLE IF NOT EXISTS liked_note (
    id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    note_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_like_user_id FOREIGN KEY (user_id) REFERENCES user (id),
    CONSTRAINT fk_like_note_id FOREIGN KEY (note_id) REFERENCES note (id)
);