package org.zerock.myapp.mybatis;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;

import lombok.Cleanup;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SqlSessionFactoryBuilderTests {
	
	// SqlSessionFactory 객체를 MyBatis 설정파일대로 생성해주는
	// 건설사(SqlSessionFactoryBuilder) 객체를 얻는 방법을 배우고
	// 얻어낸 건설사를 통해서, 현재 시점의 우리의 목표인 공장을 세우자
	
	private SqlSessionFactoryBuilder builder;
	private InputStream configIs;
	private SqlSessionFactory sqlSessionFactory;
	
	@BeforeAll
	void beforeAll() throws IOException { // 1회성 전처리 : 건설사를 만들어서, 필드에 저장하자
		log.trace("beforeAll() invoked.");
		
		// Step1. 재사용할 Builder 객체를 생성하여, 필드에 저장
		this.builder = new SqlSessionFactoryBuilder();
		log.info("\t+ this.builder : {}", this.builder);
		
		assertNotNull(this.builder);
		
		// Step2. 공장의 설계도에 해당되는, MyBatis의 설정파일에 대한 입력스트림 얻음
		// 2-1. 설정파일의 경로 지정
		String configPath = "mybatis-config.xml";
		InputStream is = Resources.getResourceAsStream(configPath);
		log.info("\tStep2. is : {}", is);
		Objects.requireNonNull(is);
		this.configIs = is;
	} // beforeAll
	
	@AfterAll
	void afterAll() {
		log.trace("afterAll() invoked.");
		
		try { this.configIs.close(); } catch (IOException _ignored) {;;}
		
	} // afterAll
	
//	@Disabled			// 이 단위테스트는 비활성화시켜라!!! -> 수행하지 마라!!
	@Order(1)			// 이 단위테스트의 실행순서 지정 -> 시나리오 순서대로 테스트 수행
	
	@Test				// 어제 사용한 것과 동일 -> 이 메소드가 단위테스트이다!!
//	@RepeatedTest(1)	// 지정된 숫자만큼 동일한 단위테스트를 반복수행하라!!!
						// 바로 위의 @Test 어노테이션과는 동시에 사용불가!!!
	
	// JUnit View 같은, 개발도구에 표시될 단위테스트에 대한 설명/이름을 설정
	@DisplayName("1. testCreateSqlSessionFactory")
	
	@Timeout(value=1L, unit=TimeUnit.SECONDS)	// 테스트의 만료시간 설정 
	void testCreateSqlSessionFactory() {
		log.trace("testCreateSqlSessionFactory() invoked.");
		
		// Step1. 필드에 저장된 Builder 객체와 설정파일에 대한 입력스트림을 기반으로
		//        MyBatis 사용시 항상 요구되는 SqlSessionFactory 생성
		SqlSessionFactory sqlSessionFactory = this.builder.build(this.configIs);
		log.info("\tStep1. sqlSessionFactory : {}", sqlSessionFactory);
		assert sqlSessionFactory != null;
		
		this.sqlSessionFactory = sqlSessionFactory;
	} // testCreateSqlSessionFactory
	
//	@Disabled			// 이 단위테스트는 비활성화시켜라!!! -> 수행하지 마라!!
	@Order(2)			// 이 단위테스트의 실행순서 지정 -> 시나리오 순서대로 테스트 수행
	
	@Test				// 어제 사용한 것과 동일 -> 이 메소드가 단위테스트이다!!
//	@RepeatedTest(1)	// 지정된 숫자만큼 동일한 단위테스트를 반복수행하라!!!
						// 바로 위의 @Test 어노테이션과는 동시에 사용불가!!!
	
	// JUnit View 같은, 개발도구에 표시될 단위테스트에 대한 설명/이름을 설정
	@DisplayName("2. testSqlSession")
	
	@Timeout(value=1000L, unit=TimeUnit.SECONDS)	// 테스트의 만료시간 설정 
	void testSqlSession() {
		log.trace("testSqlSession() invoked.");
		
		
		for(int i =0; i<=100; i++) {
			// Step1. 공장(Facotory)으로 부터, MyBatis의 가장 중요한 핵심객체인
			//	      SqlSession 객체를 획득
			@Cleanup SqlSession sqlSession = this.sqlSessionFactory.openSession();
			log.info("\tStep1. sqlSession[{}] : {}", i, sqlSession);
			
			// Step2. 추상적 연결(데이터베이스에 대한)을 의미하는 sqlSesion 객체의
			//		  Low-level에는 실제, 데이터소스에서 얻어낸 물리적인 JDBC Connection
			//		  이 있어야 합니다. 그래서 실제 물리적인 연결을 얻어내어 검증 수행
			Objects.requireNonNull(sqlSession);
			Connection conn = sqlSession.getConnection();
			log.info("\tStep2. conn: {}", conn);
			
			assertNotNull(conn);
		}// for
		
	} // testSqlSession
} // end class
