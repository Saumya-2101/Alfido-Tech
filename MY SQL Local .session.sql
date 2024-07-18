CREATE TABLE Student (
  id VARCHAR(50) PRIMARY KEY,
  name VARCHAR(100)
);
CREATE TABLE Course (
  id INT AUTO_INCREMENT PRIMARY KEY,
  course_name VARCHAR(100),
  student_id VARCHAR(50),
  grade DOUBLE,
  FOREIGN KEY (student_id) REFERENCES Students(id)
);
CREATE TABLE Attendances (
  id INT AUTO_INCREMENT PRIMARY KEY,
  student_id VARCHAR(50),
  attendance_date DATE,
  FOREIGN KEY (student_id) REFERENCES Students(id)
);