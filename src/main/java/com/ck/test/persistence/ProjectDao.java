package com.ck.test.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.ck.test.beans.MemberOfficeInfo;
import com.ck.test.beans.ProjectInfoDto;
import com.ck.test.beans.ProjectWbsDto;

@Mapper
public interface ProjectDao {
	
	//전체 프로젝트 조회(진행중인 프로젝트)
	@Select("SELECT * FROM PROJECT_INFO "
			+ "WHERE PRJ_COMPLETION = 0 ORDER BY PRJ_CODE DESC")
	public List<ProjectInfoDto> selectProjectList(int prjCompletion);
	
	//완료된 프로젝트 조회
	@Select("SELECT * FROM PROJECT_INFO WHERE PRJ_COMPLETION = 1 ORDER BY PRJ_CODE DESC")
	public List<ProjectInfoDto> selectEndProjectList(int prjCompletion);
	
	//참여중인 프로젝트 조회
	@Select("SELECT * FROM PROJECT_INFO WHERE PRJ_COMPLETION = 0, PRJ_CODE = (SELECT PRJ_CODE FROM PROJECT_MEMBERS WHERE MEM_ID = #{memId}) ORDER BY PRJ_CODE DESC")
	public List<ProjectInfoDto> selectAttendProjectList(int prjCompletion, String memId);
	
	//프로젝트 상세페이지 조회
	@Select("SELECT * FROM PROJECT_INFO WHERE PRJ_CODE = #{prjCode}")
	public ProjectInfoDto selectProject(int prjCode);
	
	/*프로젝트 정보 수정 */
	@Update("UPDATE PROJECT_INFO SET PRJ_NAME=#{prjName}, PRJ_DEPOSIT=#{prjDeposit}, PRJ_WORKING_EXPENSES=#{prjWorkingExpenses}, PRJ_START=#{prjStart}, PRJ_END=#{prjEnd}, PRJ_MOTHERCOMPANY=#{prjMothercompany} WHERE PRJ_CODE=#{prjCode}")
	public ProjectInfoDto updateProject(int prj_code);
	
	/*프로젝트 정보 생성 */
	@Insert("INSERT INTO PROJECT_INFO (PRJ_NAME, PRJ_DEPOSIT, PRJ_WORKING_EXPENSES, PRJ_START, PRJ_END, PRJ_MOTHERCOMPANY, MEM_ID) VALUES (#{prjName}, #{prjDeposit}, #{prjWorkingExpenses}, '#{prjStart}', '#{prjEnd}', #{prjMothercompany}, #{mem_id})")
	public void insertProject(ProjectInfoDto prjVO);
	
	/*프로젝트 참여인원 기본 리스트 출력*/
	@Select("SELECT * FROM MEMBER_OFFICEINFO")
	public List<MemberOfficeInfo> selectProejctMemberList();
	
	/*프로젝트 참여인원 추가(생성)*/
	@Insert("INSERT INTO PROJECT_MEMBERS (PRJ_CODE, MEM_ID) VALUES (#{prjCode}, #{memId})")
	public void insertProjectAttendMembers(String memId, int prjCode);
	
	/*특정 프로젝트 참여인원 리스트 출력 */
	@Select("SELECT * FROM MEMBER_OFFICEINFO WHERE MEM_ID = ANY(SELECT MEM_ID FROM PROJECT_MEMBERS WHERE PRJ_CODE = #{prjCode})")
	public List<MemberOfficeInfo> selectProjectAttendMemberList(int prjCode);

	/*프로젝트 WBS 정보 불러오기*/
	@Select("SELECT PRJ_CODE, PRJ_WORK_NAME, PRJ_GROUP, PRJ_STEP, PRJ_DEPTH, PRJ_MANAGER, PRJ_OUTPUT, PRJ_PLAN_START,"
			+ "PRJ_PLAN_END, PRJ_REAL_END, PRJ_WORK_COMPLETION, PRJ_TOTAL_DAYS FROM PROJECT_WBS "
			+ "WHERE PRJ_CODE = #{prjCode} ORDER BY PRJ_GROUP, PRJ_STEP ASC")
	public List<ProjectWbsDto> selectProjectWbsList(int prjCode);
	
	
}
