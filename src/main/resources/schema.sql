-- Member 테이블 생성
CREATE TABLE member (
    memberId BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phoneNumber VARCHAR(15) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    memberType VARCHAR(20) NOT NULL,
    createdAt DATETIME(6) NOT NULL,
    createdBy VARCHAR(100) NOT NULL,
    modifiedAt DATETIME(6),
    modifiedBy VARCHAR(100)
);
-- createdAt 및 modifiedAt에 대한 인덱스 생성
CREATE INDEX idx_user_createdAt_02 ON member(createdAt);
CREATE INDEX idx_user_modifiedAt_03 ON member(modifiedAt);

-- Lecture 테이블 생성
CREATE TABLE lecture (
    lectureId BIGINT AUTO_INCREMENT PRIMARY KEY,
    lectureName VARCHAR(100) NOT NULL,
    maxStudents INT NOT NULL,
    currentStudentCnt INT DEFAULT 0,
    price INT NOT NULL,
    instructorId BIGINT,
    createdAt DATETIME(6) NOT NULL,
    createdBy VARCHAR(100) NOT NULL,
    modifiedAt DATETIME(6),
    modifiedBy VARCHAR(100),
    CONSTRAINT fk_instructor_member FOREIGN KEY (instructorId) REFERENCES member(memberId) ON DELETE SET NULL
);

-- createdAt 및 modifiedAt에 대한 인덱스 생성
CREATE INDEX idx_lecture_name_01 ON lecture(lectureName);
CREATE INDEX idx_lecture_createdAt_02 ON lecture(createdAt);
CREATE INDEX idx_lecture_modifiedAt_03 ON lecture(modifiedAt);

-- Enrollment 테이블 생성
CREATE TABLE enrollment (
    enrollmentId BIGINT AUTO_INCREMENT PRIMARY KEY,
    memberId BIGINT NOT NULL,
    lectureId BIGINT NOT NULL,
    createdAt DATETIME(6) NOT NULL,
    createdBy VARCHAR(100) NOT NULL,
    modifiedAt DATETIME(6),
    modifiedBy VARCHAR(100),
    CONSTRAINT fk_member_enrollment FOREIGN KEY (memberId) REFERENCES member(memberId) ON DELETE CASCADE,
    CONSTRAINT fk_lecture_enrollment FOREIGN KEY (lectureId) REFERENCES lecture(lectureId) ON DELETE CASCADE
);
-- createdAt 및 modifiedAt에 대한 인덱스 생성
CREATE INDEX idx_enrollment_createdAt ON enrollment(createdAt);
CREATE INDEX idx_enrollment_modifiedAt ON enrollment(modifiedAt);