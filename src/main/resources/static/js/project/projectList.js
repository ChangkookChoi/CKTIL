// 프로젝트 생성 폼
function insertProjectForm() {
	$("#insertProjectForm").modal();
}

// 프로젝트 생성
// swal은 sweetalert.js에서 파생. alert 대신 쓰는 것. 경고창 url을 없애주고 ui가 깔끔
function insertProject() {
	var prjName = $('#prjName').val();
	var prjStart = $('#prjStart').val();
	var prjEnd = $('#prjEnd').val();
	var prjDeposit = $('#prjDeposit').val();
	var prjWorkingExpenses = $('#prjWorkingExpenses').val();
	var prjMothercompany = $('#prjMothercompany').val();
	var start = new Date(prjStart);
	var end = new Date(prjEnd);
	var memId = $('#memId').val();
	if(prjName == ''){
		swal('프로젝트명', '프로젝트명을 입력해주세요');
		return false;
	}else if(prjName.length > 33){
		swal('프로젝트명', '길이 제한을 벗어났습니다');
		return false;
	}else if(prjDeposit == ''){
		swal('계약금', '계약금을 입력해주세요');
		return false;
	}else if(prjWorkingExpenses == ''){
		swal('사업비', '사업비을 입력해주세요');
		return false;
	}else if(prjStart == ''){
		swal('시작일', '시작일을 입력해주세요');
		return false;
	}else if(prjEnd == ''){
		swal('종료일', '종료일을 입력해주세요');
		return false;
	}else if(end.getTime()-start.getTime() < 0){
		swal('프로젝트 기간', '시작일이 종료일보다 늦습니다');
		return false;
	}else if((((end.getTime()-start.getTime())/(1000*60*60*24)))+1 > 4000){
		swal('프로젝트 기간', '4000일 초과입니다.');
		return false;
	}else if(prjMothercompany == ''){
		swal('마더업체', '마더업체를 입력해주세요');
	}
	$('#frmInsertProjcet').submit();
}

/*프로젝트 정보 수정 모달 팝업*/
function projectUpdateModal() {
	$('#projectUpdateModal').modal();
}

/*프로젝트 참여인원 확인 리스트 모달 팝업 */
function projectAttendMemberModal() {
	$("#projectAttendMemberModal").modal();
}

function updateProject() {
	var prjName = $('#prjName').val();
	var prjStart = $('#prjStart').val();
	var prjEnd = $('#prjEnd').val();
	var prjDeposit = $('#prjDeposit').val();
	var prjWorkingExpenses = $('#prjWorkingExpenses').val();
	var prjMothercompany = $('#prjMothercompany').val();
	var start = new Date(prjStart);
	var end = new Date(prjEnd);
	var memId = $('#memId').val();
	//var prjCode = $('#prjCode').val();
	if(prjName == ''){
		swal('프로젝트명', '프로젝트명을 입력해주세요');
		return false;
	}else if(prjName.length > 33){
		swal('프로젝트명', '길이 제한을 벗어났습니다');
		return false;
	}else if(prjDeposit == ''){
		swal('계약금', '계약금을 입력해주세요');
		return false;
	}else if(prjWorkingExpenses == ''){
		swal('사업비', '사업비을 입력해주세요');
		return false;
	}else if(prjStart == ''){
		swal('시작일', '시작일을 입력해주세요');
		return false;
	}else if(prjEnd == ''){
		swal('종료일', '종료일을 입력해주세요');
		return false;
	}else if(end.getTime()-start.getTime() < 0){
		swal('프로젝트 기간', '시작일이 종료일보다 늦습니다');
		return false;
	}else if((((end.getTime()-start.getTime())/(1000*60*60*24)))+1 > 4000){
		swal('프로젝트 기간', '4000일 초과입니다.');
		return false;
	}else if(prjMothercompany == ''){
		swal('마더업체', '마더업체를 입력해주세요');
	}
	$('#frmUpdateProjcet').submit();
}


//참여인원 선택 생성 폼
//function selectProjectMember() {
//	$("#memberForm").modal();
//}

//function selectProjectMemberListAjax() {
//
//	$.ajax({
//		type: "post",
//		url: "/selectProjectMemberListAjax",
//		data: {},
//		dataType: 'text',
//		
//		error:function(request,status,error){
//		    alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);}
//		
//		});
//	
//}
