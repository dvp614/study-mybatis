package org.zerock.myapp.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.zerock.myapp.domain.EmpVO;

public interface EmpMapper {
	@Select("SELECT * FROM emp")
	public abstract List<EmpVO> selectAllEmployees();
	
	@Delete("DELETE FROM emp WHERE empno = #{empno2} AND ename = #{ename2}")
//	@Delete("DELETE FROM emp WHERE empno = #{empno} AND ename = #{ename}")
	
//	public abstract int deleteEmployee(int empno, String ename); 
//	public abstract int deleteEmployee(String ename, int empno); 
	
	public abstract int deleteEmployee(
				@Param("ename2") String ename, 
				@Param("empno2") int empno
			);
	
} // end interface
