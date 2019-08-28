package com.ck.test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ck.test.beans.MemberOfficeInfo;
import com.ck.test.beans.ProjectInfoDto;
import com.ck.test.beans.ProjectWbsDto;
import com.ck.test.persistence.ProjectDao;

@Service("ProjectService")
public class ProejctServiceImpl implements ProjectService {

	@Autowired
	private ProjectDao dao;
	
	//전체 프로젝트 조회 (진행중인 프로젝트)
	@Override
	public List<ProjectInfoDto> selectProjectList(int prjCompletion) {
		return dao.selectProjectList(prjCompletion);
	}
	
	//완료된 프로젝트 조회
	@Override
	public List<ProjectInfoDto> selectEndProjectList(int prjCompletion) {
		return dao.selectEndProjectList(prjCompletion);
	}

	//참여중인 프로젝트 조회
	@Override
	public List<ProjectInfoDto> selectAttendProjectList(int prjCompletion, String memId) {
		return dao.selectAttendProjectList(prjCompletion, memId);
	}

	//프로젝트 상세페이지 조회
	@Override
	public ProjectInfoDto selectProject(int prjCode) {
		return dao.selectProject(prjCode);
	}
	
	/*프로젝트 정보 수정*/
	@Override
	public void updateProject(ProjectInfoDto prjDto) {

		dao.updateProject(prjDto);
	}

	/*프로젝트 정보 생성 */
	@Override
	public void insertProject(ProjectInfoDto prjInfoDto) {

		dao.insertProject(prjInfoDto);
	}
	
	/*프로젝트 참여인원 기본 리스트 출력*/
	@Override
	public List<MemberOfficeInfo> selectProjectMemberList() {

		return dao.selectProejctMemberList();
	}
	
	/*프로젝트 참여인원 추가(생성)*/
	@Override
	public void insertProjectAttendMembers(String memId, int prjCode) {
		
		dao.insertProjectAttendMembers(memId, prjCode);
	}

	/*특정 프로젝트 참여인원 리스트 출력 */
	@Override
	public List<MemberOfficeInfo> selectProjectAttendMemberList(int prjCode) {
		
		return dao.selectProjectAttendMemberList(prjCode);
	}
	
	/*프로젝트 WBS 정보 불러오기*/
	@Override
	public List<ProjectWbsDto> selectProjectWbsList(int prjCode) {
		
		return dao.selectProjectWbsList(prjCode);
	}
	
	/*프로젝트 WBS 삭제 */
	@Override
	public boolean deleteProjectWbsList(int prjCode) {
		return dao.deleteProjectWbsList(prjCode) >= 0 ? true : false;
	}
	
	/*프로젝트 WBS 생성 */
	@Override
	public boolean insertProjectWbsList(List<ProjectWbsDto> prjWbsDto) {
		
		boolean chk = false;
		
		for(ProjectWbsDto prjWbsDtos : prjWbsDto) {
		chk = dao.insertProjectWbsList(prjWbsDtos) == 1 ? true : false;
	}
		return chk;
	}
}
