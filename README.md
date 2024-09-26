### 수강신청 프로젝트
* 이름: 유창근
* 이메일: youzang7@gmail.com

### 수강신청 Api 명세
* 서버 구동 후 swagger-ui 를 통해서 테스트 가능합니다.
* swagger-ui: http://localhost:8080/swagger-ui/index.html
* api-docs: http://localhost:8080/api-docs

### H2 Database Browser 콘솔접속
* 서버 구동 이후
* http://localhost:8080/h2-console

### 수강신청 몰릴경우 동시성 이슈 해결
* Redis를 활용하여 처리하는 방법도 있으나 해당 과제에서는 db로 처리했습니다.
* @Modifying
  @Query("UPDATE LectureEntity l SET l.currentStudentCnt = l.currentStudentCnt + 1 WHERE l.lectureId = :lectureId AND l.currentStudentCnt < l.maxStudents")
  int incrementStudentCountIfNotFull(@Param("lectureId") Long lectureId);