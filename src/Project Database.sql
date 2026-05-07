create database SMS;
use SMS;
create table reg_users(
fullname varchar(50),
username varchar(50),
email varchar(100),
confirm_pass varchar(100),
role varchar(20)
);

create table NewStudent(
id INT AUTO_INCREMENT PRIMARY KEY,
stu_id VARCHAR(50) unique key,
firstname varchar(50),
lastname varchar(50),
email varchar(100),
phone varchar(100),
age varchar(100),
gender varchar(20),
address varchar(300)
);

SET SQL_SAFE_UPDATES = 0;

-- Create new trigger using LAST_INSERT_ID()
DELIMITER $$
CREATE TRIGGER before_insert_student
BEFORE INSERT ON NewStudent
FOR EACH ROW
BEGIN
    -- Get the id that will be assigned
    SET @next_id = (SELECT AUTO_INCREMENT 
                    FROM information_schema.TABLES 
                    WHERE TABLE_SCHEMA = DATABASE() 
                    AND TABLE_NAME = 'NewStudent');
    
    -- If this is the first row in a batch, use current AUTO_INCREMENT
    -- Otherwise, increment for each subsequent row
    IF @last_id IS NULL OR @last_id != @next_id THEN
        SET @row_counter = 0;
        SET @last_id = @next_id;
    END IF;
    
    SET @row_counter = IFNULL(@row_counter, 0) + 1;
    SET NEW.stu_id = CONCAT('#STU', LPAD(@next_id + @row_counter - 1, 3, '0'));
END $$
DELIMITER ;

CREATE TABLE NewCourse (
    id INT AUTO_INCREMENT PRIMARY KEY,
    course_name VARCHAR(200),
    course_code VARCHAR(50),
    duration VARCHAR(50),
    course_fee int,
    course_description varchar(500)
) AUTO_INCREMENT = 101;



create table Enrollment(
    stu_id VARCHAR(50) NOT NULL,
    stu_name VARCHAR(150) NOT NULL,
    course_id INT NOT NULL,
    course_name VARCHAR(200) NOT NULL,
    enrollment_date DATE NOT NULL DEFAULT (CURDATE()),
    duration varchar(50),
    enrollment_status VARCHAR(20) DEFAULT 'Active',
    fee_paid INT not null,
    PRIMARY KEY (stu_id, course_id),
    FOREIGN KEY (stu_id) REFERENCES NewStudent(stu_id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES NewCourse(id) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS Attendance (
    id INT PRIMARY KEY AUTO_INCREMENT,
    studentId VARCHAR(50),
    studentName VARCHAR(100),
    courseName VARCHAR(100),
    attendance VARCHAR(10),
    attendanceDate DATE,
    FOREIGN KEY (studentId) REFERENCES NewStudent(stu_id)
);

