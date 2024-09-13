package org.zerock.myapp.mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.zerock.myapp.domain.EmpVO;
import org.zerock.myapp.persistence.ComplexEmpMapper;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ComplexEmpMapperTests {

//	@Disabled
	@Order(1)
	@Test
//	@RepeatedTest(1)
	@DisplayName("1. testRangeEmployees")
	@Timeout(value=3L, unit=TimeUnit.SECONDS)
	void testRangeEmployees() throws IOException {
		log.trace("testRangeEmployees() invoked.");
		
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
		SqlSessionFactory factory = builder.build(is);
		SqlSession sqlSession = factory.openSession();
		
		ComplexEmpMapper mapper = 
				sqlSession.<ComplexEmpMapper>getMapper(ComplexEmpMapper.class);
		
		int startEmpno = 7500;
		int endEmpno = 7600;
		
		List<EmpVO> list = mapper.rangeEmployees(startEmpno, endEmpno);
		list.forEach(log::info);
	} // testRangeEmployees
	
//	@Disabled
	@Order(2)
	@Test
//	@RepeatedTest(1)
	@DisplayName("2. testgetCurrentDate")
	@Timeout(value=3L, unit=TimeUnit.SECONDS)
	void testgetCurrentDate() throws IOException {
		log.trace("testgetCurrentDate() invoked.");
		
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
		SqlSessionFactory factory = builder.build(is);
		SqlSession sqlSession = factory.openSession();
		
		ComplexEmpMapper mapper = 
				sqlSession.<ComplexEmpMapper>getMapper(ComplexEmpMapper.class);
		
		Date date = mapper.getCurrentDate();
		
		log.info("\t+ date : {} ", date);
	} // testgetCurrentDate
	
} // end class
