package org.zerock.myapp.domain;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

public interface EmpMapper {
	@Select("SELECT * FROM emp")
	public abstract List<EmpVO> selectAllEmployees();
	
	@Delete("DELETE FROM emp WHERE empno = #{empno}")
	public abstract int deleteEmployee(int empno);
} // end interface
