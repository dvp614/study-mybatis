package org.zerock.myapp.domain;

import java.time.LocalDate;

import lombok.Value;

@Value
public class EmpVO {
	private String empno;
	private String ename;
	private String job;
	private Integer mgr;
	
	// JAVA8에서 새로개발된 날짜관련 타입은 아래 3개가 핵심입니다.
	// (1) LocalDate(날짜만 저장)
	// (2) LocalTime(시간만 저장)
	// (3) LocalDateTime(날짜 + 시간 저장)
	private LocalDate hireDate;
	private Double sal;
	private Double comm;
	private Integer deptno;
} // end class
