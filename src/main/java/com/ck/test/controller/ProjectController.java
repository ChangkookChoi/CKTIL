package com.ck.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ck.test.beans.MemberOfficeInfo;
import com.ck.test.beans.ProjectInfoDto;
import com.ck.test.beans.ProjectWbsDto;
import com.ck.test.service.ProjectService;

@Controller
public class ProjectController {

	@Autowired
	private ProjectService projectService;
	
	@RequestMapping("/")
	public String index() {
		
		return "project/index";
	}
	
	/*전체 프로젝트 조회 */
	@RequestMapping("/project")
	public String selectProjectList(Model model) {
		int prj_completion = 0;
		List<ProjectInfoDto> prjInfos = projectService.selectProjectList(prj_completion);
		model.addAttribute("prjInfos", prjInfos);
		return "project/project"; 
	}
	
	/*완료된 프로젝트 조회 */
	@RequestMapping("/projectEnd")
	public String selectEndProjectList(Model model) {
		int prjCompletion = 1;
		List<ProjectInfoDto> prjInfos = projectService.selectEndProjectList(prjCompletion);
		model.addAttribute("prjInfos", prjInfos);
		return "project/project"; 
	}
	
	/*프로젝트 상세페이지로 이동 및 프로젝트 WBS 불러오기*/
	@RequestMapping("/projectDetail")
	public String selectProjectView(Model model, int prjCode) {
		
		ProjectInfoDto prjInfo = projectService.selectProject(prjCode);
		List<MemberOfficeInfo> memInfos = projectService.selectProjectAttendMemberList(prjCode);
		List<ProjectWbsDto> prjWbs = projectService.selectProjectWbsList(prjCode);
		model.addAttribute("prjInfo", prjInfo);
		model.addAttribute("memInfos", memInfos);
		model.addAttribute("prjWbs", prjWbs);
		return "project/projectDetail";
	}
	
	/*프로젝트 수정 페이지로 이동 */
	@RequestMapping("/updateProjectView")
	public String updateProjectView(Model model, String prjCode) {
		int code = Integer.parseInt(prjCode.trim());
		
		ProjectInfoDto prjVO = projectService.selectProject(code);
		model.addAttribute("board", prjVO);
		return "project/projectUpdate";
	}
	
	/*프로젝트 수정 */
	@RequestMapping("/updateProject")
	public String updateProject(Model model, String prjCode) {
		
		int code = Integer.parseInt(prjCode.trim());
		
		ProjectInfoDto prjVO = projectService.updateProject(code);
		
		model.addAttribute("board", prjVO);
		return "project/project";
	}
	
	/*프로젝트 생성 */
	@RequestMapping(value="/insertProject", method=RequestMethod.POST)
	public String insertProject(ProjectInfoDto prjVO) {
		
		projectService.insertProject(prjVO);
		
		return "redirect:/project/project";
	}
	
	/*프로젝트 참여인원 선택 페이지로 이동 */
	@RequestMapping("/selectProjectAttendMemberList")
	public String selectProjectAttendMembersView(Model model, int prjCode) {
		
		List<MemberOfficeInfo> memOfficeInfo = projectService.selectProjectMemberList();
		ProjectInfoDto prjDto = projectService.selectProject(prjCode);
		model.addAttribute("members", memOfficeInfo);
		model.addAttribute("prjCode", prjDto);
		return "project/projectAttendMembers";
	}
	
	/*프로젝트 참여인원 추가 */
	@RequestMapping(value="/insertProjectAttendMembers", method=RequestMethod.POST)
	@ResponseBody
	public String insertProjectAttendMembers(@RequestParam(value="checkBoxArr[]") List<String> checkBoxArr, int prjCode) {
		for(String checkBox : checkBoxArr) {
			projectService.insertProjectAttendMembers(checkBox, prjCode);
		}
		
		return "project/project";
	}
	
	@RequestMapping(value="selectProjectWbsListAjax", method=RequestMethod.POST)
	@ResponseBody
	public List<ProjectWbsDto> selectProjectWbsListAjax(int prjCode){
			
		System.out.println("11111"+prjCode);
		System.out.println(projectService.selectProjectWbsList(prjCode));
		return projectService.selectProjectWbsList(prjCode);
	}
	
	
//	@RequestMapping("/selectProjectMemberListAjax")
//	@ResponseBody
//	public List<ProjectVO> selectProjectMemberListAjax() {
//		
//		
//		return projectService.selectProejctMemberList();
//	}
//	
//	@RequestMapping("/selectProjectMemberList")
//	public String selectProjectMemberList(Model model) {
//		
//		List<ProjectVO> prjLists = projectService.selectProejctMemberList();
//		model.addAttribute("memList", prjLists);
//		return "/project";
//	}
	
}
