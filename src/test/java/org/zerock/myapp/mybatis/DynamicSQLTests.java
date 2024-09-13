package org.zerock.myapp.mybatis;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.zerock.myapp.domain.EmpVO;

import lombok.Cleanup;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DynamicSQLTests {
	
	private SqlSessionFactory sqlSessionFactory;
	
	@BeforeAll
	void beforAll() throws IOException {
		log.trace("beforeAll() invoked.");
		
		SqlSessionFactoryBuilder builder= 
				new SqlSessionFactoryBuilder();
		
		InputStream configIs = Resources.getResourceAsStream("mybatis-config.xml");
		
		this.sqlSessionFactory = builder.build(configIs);
		
		assertNotNull(this.sqlSessionFactory);
	} // beforeAll
	
//	@Disabled
	@Order(2)
	@Test
//	@RepeatedTest(1)
	@DisplayName("1. testWhereAndIfTag")
	@Timeout(value=1L, unit=TimeUnit.MINUTES)
	void testWhereAndIfTag() {
		log.trace("testWhereAndIfTag() invoked.");
		
		String namespace = "EmpMapper";
		String sqlId = "3";
		String mappedStatement = namespace + '.' + sqlId;
		
		@Cleanup SqlSession sqlSession = this.sqlSessionFactory.openSession();
		
		List<EmpVO> list = sqlSession.<EmpVO>selectList(mappedStatement, "A");
		
		Objects.requireNonNull(list);
		list.forEach(log::info);
	} // testWhereAndIfTag
	
//	@Disabled
	@Order(3)
	@Test
//	@RepeatedTest(1)
	@DisplayName("1. testWhereAndIfTag2")
	@Timeout(value=1L, unit=TimeUnit.MINUTES)
	void testWhereAndIfTag2() {
		log.trace("testWhereAndIfTag() invoked.");
		
		String namespace = "EmpMapper";
		String sqlId = "4";
		String mappedStatement = namespace + '.' + sqlId;
		
		@Cleanup SqlSession sqlSession = this.sqlSessionFactory.openSession();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("startEmpno", 7500);
		paramMap.put("endEmpno", 7900);
		paramMap.put("ename", "A");
		
		List<EmpVO> list = sqlSession.<EmpVO>selectList(mappedStatement, paramMap);
		
		Objects.requireNonNull(list);
		list.forEach(log::info);
	} // testWhereAndIfTag
} // end class
