package org.zerock.myapp.mybatis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.zerock.myapp.domain.EmpVO;
import org.zerock.myapp.persistence.EmpMapper;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmpMapperTests {
	
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
	
	
//	@Disabled
	@Order(1)
	@Test
//	@RepeatedTest(1)
	@DisplayName("1. testSelectAllEmployees")
	@Timeout(value=3L, unit=TimeUnit.SECONDS)
	void testSelectAllEmployees() {
		log.trace("testSelectAllEmployees() invoked.");
		// Step1. 필드에 저장된 sqlSessionFactory로부터 sqlSession 얻어냄
//	      SqlSession sqlSession = this.sqlSessionFactory.openSession();
	      
	      // Step2. Mapper inyterface구현객체를 획득
	      EmpMapper mapper = sqlSession.<EmpMapper>getMapper(EmpMapper.class);
	      log.info("\t+ mapper: {}, type: {}", mapper, mapper.getClass().getName());
	      
	      // Step3. Mapper Interface의 메소드를 실행
	      Objects.requireNonNull(mapper);
	      
	      List<EmpVO> list = mapper.selectAllEmployees();
	      assertNotNull(list);
	      
	      // Step4. Step3에서 받은 리턴값 출력
	      list.forEach(log::info);
	} // testSelectAllEmployees
	
//	@Disabled
	@Order(2)
	@Test
//	@RepeatedTest(1)
	@DisplayName("2. testDeleteEmployee")
	@Timeout(value=3L, unit=TimeUnit.SECONDS)
	void testDeleteEmployee() {
		log.trace("testDeleteEmployee() invoked.");
		
		// Step1. 필드에 저장된 SqlSessionFactory로부터, SqlSession 얻어냄
//		SqlSession sqlSession = this.sqlSessionFactory.openSession();
		
		// Step2. Mapper Interface의 구현객체(MapperProxy)를 획득
		EmpMapper mapper = sqlSession.<EmpMapper>getMapper(EmpMapper.class);
		log.info("\tStep2. mapper : {}, type : {}", mapper, mapper.getClass().getName());
		
		// Step3. Mapper Interface의 메소드를 실행
		// 3-1 추상메소드에 붙인 SQL문장에 바인드 변수가 하나도 없을 때
//		int deletedRows = mapper.deleteEmployee();
//		log.info("\tStep3. deletedRows : {}", deletedRows);
		
		// 3-2  추상메소드에 붙힌 SQL문장에 바인드변수가 하나만 있을 때
		int empnoToDelete = 9000;
		String enameToDelete = "Yoon";
		int deletedRows = mapper.deleteEmployee(enameToDelete, empnoToDelete);
		
		// Step4. Step3에서 받은 리턴값 출력
		assertEquals(1, deletedRows);
		
		List<EmpVO> list = mapper.selectAllEmployees();
	    assertNotNull(list);
	      
	    // Step4. Step3에서 받은 리턴값 출력
	    list.forEach(log::info);
	    
	} // testDeleteEmployee
	
//	@Disabled
	@Order(3)
	@Test
//	@RepeatedTest(1)
	@DisplayName("3. testDeleteWithBindVariable")
	@Timeout(value=3L, unit=TimeUnit.SECONDS)
	void testDeleteWithBindVariable() {
		log.trace("testDeleteWithBindVariable() invoked.");
		
		// Mapper XML 방식으로 SQL문장 수행 및 트랜잭션 관리
		String namespace = "EmpMapper";
		String sqlId = "2";
		String mappedStatement = namespace + '.' + sqlId;
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("empno", 9000);
		map.put("ename", "Yoon");
		
		int deletedRows = sqlSession.delete(mappedStatement, map);
		assertEquals(1, deletedRows);
		
		
		String sql = "EmpMapper.1";
		
		List<EmpVO> emp = this.sqlSession.<EmpVO>selectList(sql);
		log.info("\t+ Impl. Type : {}", emp.getClass().getName());
		
		Objects.requireNonNull(emp);
		emp.forEach(log::info);
	} // testDeleteWithBindVariable
} // end class
