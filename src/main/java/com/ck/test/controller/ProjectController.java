package com.ck.test.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ck.test.beans.MemberDto;
import com.ck.test.beans.ProjectInfoDto;
import com.ck.test.beans.ProjectWbsDto;
import com.ck.test.service.ProjectService;
import com.ck.test.util.Analysis;

@Controller
public class ProjectController {

	@Autowired
	private ProjectService projectService;
	
	/*전체 프로젝트 조회 */
	@RequestMapping("/project")
	public String selectProjectList(Model model) {
		List<ProjectInfoDto> prjInfos = projectService.selectProjectList();
		model.addAttribute("prjInfos", prjInfos);
		return "project/project"; 
	}
	
	/*완료된 프로젝트 조회 */
	@RequestMapping("/projectEnd")
	public String selectEndProjectList(Model model) {
		List<ProjectInfoDto> prjInfos = projectService.selectEndProjectList();
		model.addAttribute("prjInfos", prjInfos);
		return "project/project"; 
	}
	
	/*프로젝트 상세페이지로 이동 및 프로젝트 WBS 불러오기*/
	@RequestMapping("/projectDetail")
	public String selectProjectView(Model model, int prjCode) {
		
		ProjectInfoDto prjInfo = projectService.selectProject(prjCode);
		List<MemberDto> memInfos = projectService.selectProjectAttendMemberList(prjCode);
		List<ProjectWbsDto> prjWbs = projectService.selectProjectWbsList(prjCode);
		System.out.println("prjInfo내용 : "+prjInfo);
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
	public String updateProject(Model model, ProjectInfoDto prjDto) {
//		int code = Integer.parseInt(prjCode.trim());
		
		System.out.println("code 값 : " + prjDto);
		System.out.println(prjDto.getPrjStart()+"이건 start  "+prjDto.getPrjEnd()+"이건 end");
		projectService.updateProject(prjDto);
		return "redirect:/project";
	}
	
	/*프로젝트 생성 */
	@RequestMapping(value="/insertProject", method=RequestMethod.POST)
	public String insertProject(ProjectInfoDto prjDto) {
		projectService.insertProject(prjDto);
		return "redirect:/project";
	}
	
	/*프로젝트 참여인원 선택 페이지로 이동 */
	@RequestMapping("/selectProjectAttendMemberList")
	public String selectProjectAttendMembersView(Model model, int prjCode) {
		
		List<MemberDto> memOfficeInfo = projectService.selectProjectMemberList();
		ProjectInfoDto prjDto = projectService.selectProject(prjCode);
		model.addAttribute("members", memOfficeInfo);
		model.addAttribute("prjCode", prjDto);
		return "project/projectAttendMembers";
	}
	
	/*프로젝트 참여인원 추가 */
	@RequestMapping(value="/insertProjectAttendMembers", method=RequestMethod.POST)
	@ResponseBody
	public String insertProjectAttendMembers(@RequestParam(value="checkBoxArr[]") List<String> checkBoxArr, int prjCode) {
		System.out.println("프로젝트 참여인원 추가 컨트롤러 진입");
		for(String checkBox : checkBoxArr) {
			projectService.insertProjectAttendMembers(checkBox, prjCode);
		}
		
		System.out.println("프로젝트 참여인원 추가 컨트롤러 작업 완료");
		return "project/project";
	}
	
	
	//Project WBS List 불러오는 ajax
	@RequestMapping(value="selectProjectWbsListAjax", method=RequestMethod.POST)
	@ResponseBody
	public List<ProjectWbsDto> selectProjectWbsListAjax(int prjCode){
		
		System.out.println("selectProjectWbsListAjax 실행 및 결과 값" + projectService.selectProjectWbsList(prjCode));
		System.out.println("매개변수 prjCode : " + prjCode);
		
		return projectService.selectProjectWbsList(prjCode);
	}
	
	//Project WBS List 저장하는 ajax
	@RequestMapping(value="insertProjectWbsListAjaxCon", method=RequestMethod.POST)
	@ResponseBody
	public boolean insertProjectWbsListAjaxCon(ProjectInfoDto prjDto, HttpServletRequest req) {
		System.out.println("컨트롤러 진입했습니다.");
		
		System.out.println("insertProjectWbsListAjax : " + prjDto);
		boolean isc = false;
		
		
		String[] prjTotalDaysSum = req.getParameterValues("inPrjTotalDays");
		System.out.println("prjTotalDaysSum 출력 결과 : " + prjTotalDaysSum);
		System.out.println("req.getParameterValues(\"inPrjTotalDays\") 출력 결과 : " + req.getParameterValues("inPrjTotalDays"));
		
		if(prjTotalDaysSum != null) {
			String[] prjTotalDays = new Analysis(prjDto, prjTotalDaysSum).getTermsFormat();
			List<ProjectWbsDto> lists = new ArrayList<ProjectWbsDto>();
			for(int i = 0; i < prjTotalDays.length; i++) {
				System.out.println("값을 list에 넣는 중 " + i + "번째");
				ProjectWbsDto prjWbsDto = new ProjectWbsDto(prjDto.getPrjCode(),
						Integer.parseInt(req.getParameterValues("inPrjGroup")[i]),
						Integer.parseInt(req.getParameterValues("inPrjStep")[i]),
						Integer.parseInt(req.getParameterValues("inPrjDepth")[i]),
						Integer.parseInt(req.getParameterValues("workCompletion")[i]),
						req.getParameterValues("inPrjWorkName")[i],
						req.getParameterValues("planStart")[i],
						req.getParameterValues("planEnd")[i],
						req.getParameterValues("realStart")[i],
						req.getParameterValues("realEnd")[i],
						req.getParameterValues("inPrjManager")[i],
						req.getParameterValues("inPrjOutput")[i],
						prjTotalDays[i]);
				lists.add(prjWbsDto);
				System.out.println("lists에 들어간 값 : "+lists);
			}
			System.out.println("prjDto에서 가져온 코드 값" + prjDto.getPrjCode());
			isc = projectService.deleteProjectWbsList(prjDto.getPrjCode());
			
			isc = projectService.insertProjectWbsList(lists);
			
			System.out.println("출력 결과입니다."+lists);
		}else {
			isc = projectService.deleteProjectWbsList(prjDto.getPrjCode());
		}
		
		return isc;
	}
	
	
	
	
//	
//	@RequestMapping("selectAttendProjectMembersAjax")
//	@ResponseBody
//	public List<MemberDto> selectAttendProjectMembersAjax(int prjCode){
//		
//		List<ProjectInfoDto> prjCodes = projectService.selectPrjCode();
//		List<MemberDto> memDto = null;
//		for(int i=0; i <= prjCodes.size(); i++) {
//			memDto = projectService.selectProjectAttendMemberList(prjCode);
//		}
//		
//		return memDto;
//	}
//	
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
