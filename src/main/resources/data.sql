INSERT INTO member (name, email, phoneNumber, password, memberType, createdAt, createdBy, modifiedAt, modifiedBy)
VALUES ('John Doe', 'john@example.com', '01012345678', 'password', 'STUDENT', now(), 'UD', now(), 'UD');

INSERT INTO lecture (lectureName, maxStudents, price, instructorId, createdAt, createdBy, modifiedAt, modifiedBy)
VALUES ('Spring Boot', 30, 100000, 1, now(), 'UD', now(), 'UD');