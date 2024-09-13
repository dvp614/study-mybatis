package org.zerock.myapp.mybatis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.zerock.myapp.domain.EmpVO;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class XMLMapperTests {
	
	private SqlSessionFactory sqlSessionFactory;
	private SqlSession sqlSession;
	
	@BeforeAll
	void beforeAll() throws IOException {
		log.trace("beforeAll() invoked.");
		
		this.sqlSessionFactory = 
				new SqlSessionFactoryBuilder()
					.build(Resources.getResourceAsStream("mybatis-config.xml"));
		
		Objects.requireNonNull(this.sqlSessionFactory);
		log.info("\t+ this.sqlSessionFactory : {}", sqlSessionFactory);
	} // beforeAll
	
	@BeforeEach
	void beforeEach() { // 각 단위테스트 수행시마다 반복수행되는 전처리
		log.trace("beforeEach() invoked.");
		
		this.sqlSession = this.sqlSessionFactory.openSession();
		log.info("\t+ this.sqlSession : {}", this.sqlSession);
		
		assertNotNull(this.sqlSession);
	} // beforeEach
	
	@Disabled
	@Order(1)
	@Test
//	@RepeatedTest(1)
	@DisplayName("1. BoardMapper.1으로 특정된 SQL문장 수행 및 결과 수신")
	@Timeout(value=1L, unit=TimeUnit.SECONDS)
	void testBoardMapper() {
		log.trace("testBoardMapper() invoked.");
		
		String namespace = "BoardMapper";
		String sqlId = "1";
		String sql = namespace + '.' + sqlId;
		
		String now = this.sqlSession.<String>selectOne(sql);
		log.info("\t+ now : {}", now);
		
		assert now != null;
	} // testBoardMapper
	
//	@Disabled
	@Order(2)
	@Test
//	@RepeatedTest(1)
	@DisplayName("2. scott::emp 테이블 조회하여, 모든 사원정보를 출력")
	@Timeout(value=3L, unit=TimeUnit.SECONDS)
	void testEmpSelect() {
		log.trace("testEmpSelect() invoked.");
		
		String sql = "EmpMapper.1";
		
		List<EmpVO> emp = this.sqlSession.<EmpVO>selectList(sql);
		log.info("\t+ Impl. Type : {}", emp.getClass().getName());
		
		Objects.requireNonNull(emp);
		emp.forEach(log::info);
	} // testEmpSelect
	
//	@Disabled
	@Order(3)
	@Test
//	@RepeatedTest(1)
	@DisplayName("3. scott::emp 테이블 삭제")
	@Timeout(value=3L, unit=TimeUnit.SECONDS)
	void testEmpDelete() {
		log.trace("testEmpDelete() invoked.");
		
		String sql = "EmpMapper.2";
		Integer affectedRows = this.sqlSession.delete(sql);
		log.info("\t+ affectedRows : {}", affectedRows);
		
		assertEquals(1, affectedRows);
		
		String sql2 = "EmpMapper.1";
		
		List<EmpVO> emp = this.sqlSession.<EmpVO>selectList(sql2);
		log.info("\t+ Impl. Type : {}", emp.getClass().getName());
		
		Objects.requireNonNull(emp);
		emp.forEach(log::info);
	} // testEmpDelete
} // end class
