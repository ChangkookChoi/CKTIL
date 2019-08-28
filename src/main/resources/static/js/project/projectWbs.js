var baseData = {} // ProjectInfoDto
var subData = {} // List<ProjectWbsDto>


/*최초 ProjectWbs 화면 그리기 */
$(document).ready(function(){
	
	// 기본데이터 저장 객체
	baseData = {
			start : $('#prjStart').val().split("-"),
			end : $('#prjEnd').val().split("-"),
			schdule : function() { //term 구하기. 날짜와 날짜로 일수 차이를 구하는 공식. [1]에서 -1 해주는 이유는 date객체의 월은 실제 월보다 1이 작기 때문
				var start = new Date(this.start[0], this.start[1]-1, this.start[2]);
				var end = new Date(this.end[0], this.end[1]-1, this.end[2]);
				return ((end.getTime() - start.getTime()) / (1000*60*60*24)) + 1;
			}
	}
	
	screenWriteThead();
	
	var prjCodes = $('#prjCode').val();

	$.ajax({
		url: '/selectProjectWbsListAjax',
		type: 'post',
		async: false,
		data: {
			prjCode : prjCodes
		},
		success: function(lists) {
			if(lists.length != 0){
				baseData.chk = true;
				subData.cnt = lists.length;
				subData.prjWorkName = new Array();
				subData.prjGroup = new Array();
				subData.prjStep = new Array();
				subData.prjDepth = new Array();
				subData.prjManager = new Array();
				subData.prjOutput = new Array();
				subData.prjPlanStart = new Array();
				subData.prjPlanEnd = new Array();
				subData.prjRealEnd = new Array();
				subData.prjWorkCompletion = new Array();
				subData.prjTotalDays = new Array();
				$.each(lists, function(i, projectWbsDto){
					subData.prjWorkName[i] = projectWbsDto.prjWorkName;
					subData.prjGroup[i] = projectWbsDto.prjGroup;
					subData.prjStep[i] = projectWbsDto.prjStep;
					subData.prjDepth[i] = projectWbsDto.prjDepth;
					subData.prjManager[i] = projectWbsDto.prjManager;
					subData.prjOutput[i] = projectWbsDto.prjOutput;
					subData.prjPlanStart[i] = projectWbsDto.prjPlanStart;
					subData.prjPlanEnd[i] = projectWbsDto.prjPlanEnd;
					subData.prjRealEnd[i] = projectWbsDto.prjRealEnd;
					subData.prjWorkCompletion[i] = projectWbsDto.prjWorkCompletion;
					subData.prjTotalDays[i] = projectWbsDto.prjTotalDays;
					});
				}else{
					baseData.chk = false;
					swal("오류", "WBS가 생성되지 않았거나, WBS를 불러오던 중 오류가 발생했습니다.");
				}
			}
	});
	
	if(baseData.chk) {
		for(var i=0; i<subData.cnt; i++){
			$('#tbody').append(screenWriteTbody(i, subData.prjGroup[i], subData.prjStep[i], subData.prjDepth[i],
						subData.prjWorkName[i], subData.prjManager[i], subData.prjOutput[i], 
						subData.prjPlanStart[i], subData.prjPlanEnd[i], subData.prjRealEnd[i],
						subData.prjWorkCompletion[i], subData.prjTotalDays[i]));
		}
	}
});
	

/*테이블 tbody 화면에 그리는 함수 (projectWbs 출력) */
function screenWriteTbody(num, group, step, depth, workName, manager, output, planStart, planEnd, realEnd, workCompletion, totalDays) {
	var tag = new StringBuffer();
	
	tag.append("<tr id='"+num+"'>");
	tag.append("<td><input type='checkbox' name='chkVal' value='"+num+"'/></td>")
	tag.append("<td>");
	tag.append('<div class="dropdown">');
	tag.append('<button class="btn btn-info" id="dLabel" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">');
	tag.append(numFormat(Number(num)+1));
	tag.append('</button>');
	tag.append('<ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">');
	tag.append('<li><a onclick="createList('+num+')">목록추가</a></li>');
	tag.append('<li><a onclick="createChildList('+num+')">하위추가</a></li>');
	tag.append('<li><a onclick="createParentList('+num+')">상위추가</a></li>');
	tag.append('</ul></div>');
	tag.append("<input type='hidden' name='inPrjGroup' value='"+group+"'>");
	tag.append("<input type='hidden' name='inPrjStep' value='"+step+"'>");
	tag.append("<input type='hidden' name='inPrjDepth' value='"+depth+"'>");
	tag.append("<input type='hidden' name='workCompletion' value='"+workCompletion+"'>");
	tag.append("</td>");
	tag.append("<td style='overflow: hidden;'><input style='margin-left: "+(25*depth)+"px;' type='text' name='inPrjWorkName' value='"+workName+"'></td>");
	tag.append("<td><input type='date' name='planStart' value='"+planStart+"'></td>");
	tag.append("<td><input type='date' name='planEnd' value='"+planEnd+"'></td>");
	tag.append("<td><input type='date' name='realEnd' value='"+realEnd+"'></td>");
	tag.append("<td><input type='text' name='inPrjManager' value='"+manager+"'></td>");
	tag.append("<td><input type='text' name='inPrjOutput' value='"+output+"'></td>")
	tag.append("<td class='progressList'>0%</td>");
	tag.append("</tr>");
	
	return tag.toString();
}


//숫자 5 -> 05 변경
function numFormat(num) {
	var str = ''+num;
	if(num<10 && str.indexOf('0') == -1) {
		str = '0'+num;
	}
	return str;
}

// 맨 처음 목록 생성
function createTopList(){
	if(baseData.chk){
		$('#tbody tr').attr('id', function(){
			return Number($(this).attr('id'))+1;
		}).find('input[name=chkVal]').attr('value', function(){
			return Number($(this).val()) + 1;
		}).end().find('input[name=inPrjGroup]').attr('value', function(){
			return Number($(this).val()) + 1;
		}).end().find('button').text(function(){
			return numFormat(Number($(this).text()) + 1);
		}).end().find('a').attr('onclick', function(){
			if($(this).attr('onclick').indexOf('createChildList') != -1){
				return 'createChildList('+$(this).parents("tr").attr("id")+')';
			}else if($(this).attr('onclick').indexOf('createParentList') != -1){
				return 'createParentList('+$(this).parents("tr").attr("id")+')';
			}else {
				return 'createList('+$(this).parents("tr").attr("id")+')';
			}
		});
	}
	$('#tbody').prepend(screenWriteTbody(0, 0, 0, 0, '', '', '', '', '', '', 0, ''));
	subData.cnt = $('#tbody tr').length;
	baseData.chk = true;
}

// 상위 추가
function createParentList(num){
	var myPrjGroup = $('#tbody tr:eq('+num+')').find('input[name=inPrjGroup]').val();
	var myPrjStep = $('#tbody tr:eq('+num+')').find('input[name=inPrjStep]').val();
	var myPrjDepth = $('#tbody tr:eq('+num+')').find('input[name=inPrjDepth]').val();
	
	var sumStep = 1;
	$('#tbody tr:gt('+num+')').attr('id', function(){
		return Number($(this).attr('id')) + 1;
	}).find('input[name=chkVal]').attr('value', function(){
		return Number($(this).val()) + 1;
	}).end().find('input[name=inPrjGroup]').attr('value', function(){
		if(myPrjDepth == '0'){
			return Number($(this).val()) + 1;
		}else if(myPrjDepth == '1'){
			if(myPrjGroup == $(this).val()){
				$(this).parent('td').find('input[name=inPrjStep]').attr('value', function(){
					return sumStep++;
				});
			}
			return Number($(this).val()) + 1;
		}else{
			if(myPrjGroup == $(this).val){
				$(this).parent('td').find('input[name=inPrjStep]').attr('value', function(){
					return Number($(this).parent('td').find('input[name=inPrjStep]').val()) + 1;
				});
			}
			return $(this).val();
		}
	}).end().find('button').text(function(){
		return numFormat(Number($(this).text()) + 1);
	}).end().find('a').attr('onclick', function(){
		if($(this).attr('onclick').indexOf('createChildList') != -1){
			return 'createChildList('+$(this).parents("tr").attr("id")+')';
		}else if($(this).attr('onclick').indexOf('createParentList') != -1){
			return 'createParentList('+$(this).parents("tr").attr("id")+')';
		}else {
			return 'createList('+$(this).parents("tr").attr("id")+')';
		}
	});
	
	if(myPrjDepth == '0'){
		$('#tbody tr:eq('+num+')').after(screenWriteTbody(num+1, Number(myPrjGroup)+1, 0, 0, '', '', '', '', '', '', 0, ''));
	}else if(myPrjDepth == '1'){
		$('#tbody tr:eq('+num+')').after(screenWriteTbody(num+1, Number(myPrjGroup)+1, 0, 0, '', '', '', '', '', '', 0, ''));
	}else{
		$('#tbody tr:eq('+num+')').after(screenWriteTbody(num+1, myPrjGroup, Number(myPrjStep)+1, Number(myPrjDepth)-1, '', '', '', '', '', '', 0, ''));
	}
	subData.cnt = $('#tbody tr').length;
}

// 목록 추가
function createList(num) {
	var myPrjGroup = $('#tbody tr:eq('+num+')').find('input[name=inPrjGroup]').val();
	var myPrjStep = $('#tbody tr:eq('+num+')').find('input[name=inPrjStep]').val();
	var myPrjDepth = $('#tbody tr:eq('+num+')').find('input[name=inPrjDepth]').val();
	$('#tbody tr:gt('+num+')').attr('id',function(){
		return Number($(this).attr('id')) + 1;
	}).find('input[name=chkVal]').attr('value', function(){
		return Number($(this).val()) + 1;
	}).end().find('input[name=inPrjGroup]').attr('value', function(){
		if($(this).val() == myPrjGroup) {
			if(myPrjStep == '0') {
				return Number($(this).val()) + 1;
			}else {
				$(this).parent('td').find('input[name=inPrjStep]').attr('value', function(){
					return Number($(this).parent('td').find('input[name=inPrjStep]').val()) + 1;
				});
				return $(this).val();
			}
		}else if(myPrjStep != '0') {
			return $(this).val();
		}else {
			return Number($(this).val()) + 1;
		}
	}).end().find('button').text(function(){
		return numFormat(Number($(this).text()) + 1);
	}).end().find('a').attr('onclick', function(){
		if($(this).attr('onclick').indexOf('createChildList') != -1) {
			return 'createChildList('+$(this).parents("tr").attr("id")+')';
		}else if($(this).attr('onclick').indexOf('createParentList') != -1) {
			return 'createParentList('+$(this).parents("tr").attr("id")+')';
		}else {
			return 'createList('+$(this).parents("tr").attr("id")+')';
		}
	});
	if(myPrjStep == '0') {
		$('#tbody tr:eq('+num+')').after(screenWriteTbody(num+1, Number(myPrjGroup)+1, 0, 0, '', '', '', '', '', '', 0, ''));
	}else {
		$('#tbody tr:eq('+num+')').after(screenWriteTbody(num+1, myPrjGroup, Number(myPrjStep)+1, myPrjDepth, '', '', '', '', '', '', 0, ''));
	}
	subData.cnt = $('#tbody tr').length;
}

//하위추가
function createChildList(num) {
	var myPrjGroup = $('#tbody tr:eq('+num+')').find('input[name=inPrjGroup]').val();
	var myPrjStep = $('#tbody tr:eq('+num+')').find('input[name=inPrjStep]').val();
	var myPrjDepth = $('#tbody tr:eq('+num+')').find('input[name=inPrjDepth]').val();
	$('#tbody tr:gt('+num+')').attr('id',function(){
		return Number($(this).attr('id'))+1;
	}).find('input[name=chkVal]').attr('value', function(){
		return Number($(this).val()) + 1;
	}).end().find('input[name=inPrjStep]').attr('value', function(){
		if($(this).parent('td').find('input[name=inPrjGroup]').val() == myPrjGroup) {
			return Number($(this).val()) + 1;
		}else {
			return $(this).val();
		}
	}).end().find('button').text(function(){
		return numFormat(Number($(this).text()) + 1);
	}).end().find('a').attr('onclick', function(){
		if($(this).attr('onclick').indexOf('createChildList') != -1) {
			return 'createChildList('+$(this).parents("tr").attr("id")+')';
		}else if($(this).attr('onclick').indexOf('createParentList') != -1) {
			return 'createParentList('+$(this).parents("tr").attr("id")+')';
		}else {
			return 'createList('+$(this).parents("tr").attr("id")+')';
		}
	});
	$('#tbody tr:eq('+num+')').after(screenWriteTbody(num+1, myPrjGroup, Number(myPrjStep)+1, Number(myPrjDepth)+1, '', '', '', '', '', '', 0, ''));

	subData.cnt = $('#tbody tr').length;
}

//전체체크
function checkAll(bool) {
	var chkVal = document.getElementsByName("chkVal");
	for (var i = 0; i < chkVal.length; i++) {
		chkVal[i].checked = bool;
	}
}
// 삭제처리
function checkListRemove() {
	var isc = 0;
	while(true) {
		var chkVal = document.getElementsByName("chkVal");
		for (var i=0,cnt=0; i<chkVal.length; i++) {
			if(chkVal[i].checked) {
				cnt++;
			}
		}
		if(cnt==0) {
			break;
		}
		isc++;
		for (var i=0; i<chkVal.length; i++) {
			if(chkVal[i].checked) {
				var myPrjGroup = $('#tbody tr:eq('+chkVal[i].value+')').find('input[name=inPrjGroup]').val();
				var myPrjStep = $('#tbody tr:eq('+chkVal[i].value+')').find('input[name=inPrjStep]').val();
				$('#tbody tr:gt('+chkVal[i].value+')').attr('id',function(){
					return Number($(this).attr('id')) - 1;
				}).find('input[name=chkVal]').attr('value', function(){
					return Number($(this).val()) - 1;
				}).end().find('input[name=inPrjGroup]').attr('value',function(){
					if(myPrjGroup == $(this).val() && myPrjStep != '0') {
						$(this).parent('td').find('input[name=inPrjStep]').attr('value', function(){
							return Number($(this).parent('td').find('input[name=inPrjStep]').val()) - 1;
						});
						return $(this).val();
					}else if(myPrjStep != '0') {
						return $(this).val();
					}else {
						return Number($(this).val()) - 1;
					}
				}).end().find('button').text(function(){
					return numFormat(Number($(this).text()) - 1);
				}).end().find('a').attr('onclick', function(){
					if($(this).attr('onclick').indexOf('createChildList') != -1) {
						return 'createChildList('+$(this).parents("tr").attr("id")+')';
					}else if($(this).attr('onclick').indexOf('createParentList') != -1) {
						return 'createParentList('+$(this).parents("tr").attr("id")+')';
					}else {
						return 'createList('+$(this).parents("tr").attr("id")+')';
					}
				});
				$('#tbody tr:eq('+chkVal[i].value+')').remove();
				break;
			}
		}
	}
	if(isc==0) {
		swal("삭제", '선택해주세요');
	}
	if($('#tbody tr').length == 0) {
		baseData.chk = false;
	}
	subData.cnt = $('#tbody tr').length;
}

//배열로 반환한다.
function byNameArray(data) {
	var dataArray = new Array();
	for(var i=0; i<data.length; i++) {
		dataArray[i] = data[i].value;
	}
	return dataArray;
}

// 프로젝트 WBS 저장
function insertProjectWbsList(){
	
	var prjWorkNames = byNameArray(document.getElementsByName("inPrjWorkName"));
	var chk = true;
	for(var i=0; i<prjWorkNames.length; i++){
		if(prjWorkNames[i].trim() == '' || prjWorkNames[i].trim().length == 0){
			chk = false;
			break;
		}
	}
	if(chk){
		insertProjectWbsListAjax();
	}else{
		swal('저장 오류', '업무구분 작업명을 모두 입력해주세요.')
	}
}

var insertProjectWbsListAjax = function(){
	
	$.ajax({
		url: 'insertProjectWbsListAjax',
		type: 'post',
		async: false,
		data: {
			
		},
		success: function(msg){
			if(msg){
				swal('저장', '저장에 성공했습니다.');
			}else{
				swal('저장', '저장에 실패했습니다.');
			}
		}
	})
	
}


//매개변수로 받은 월부터 마지막 12까지
function LastMonths(year, month) {
	var tag = new StringBuffer();
	for(var i=Number(month); i<=12; i++) {
		tag.append(tagMonthOne(calendar.makeOne(year, i)));
	}
	return tag.toString();
}
// 매개변수로 받은 월이하 1이상
function startMonths(year, month) {
	var tag = new StringBuffer();
	for(var i=1; i<=Number(month); i++){
		tag.append(tagMonthOne(calendar.makeOne(year, i)));
	}
	return tag.toString();
}

//달력의 해당 날짜의 요일을 구하기위해 현재위치 반환
function monthDayIndex(month, day) {
	for(var i=0; i<month.length; i++) {
		if(month[i]==day) {
			return i;
		}
	}
}
// 시작월부터 종료월의 colspan값을 구해서 td 반환
function yearMonth(year, startMonth, lastMonth) {
	var tag = new StringBuffer();
	if(calendar.iscLeafCheck(year)) {
		for(var i=Number(startMonth)-1; i<Number(lastMonth); i++) {
			tag.append(tdTagFormatMonth(calendar.LEAF[i], year, i+1));
		}
	}else {
		for(var i=Number(startMonth)-1; i<Number(lastMonth); i++) {
			tag.append(tdTagFormatMonth(calendar.PLAIN[i], year, i+1));
		}
	}
	return tag.toString();
}

//td 월 한칸 포맷
function tdTagFormatMonth(colspan, year, month) {
	return "<td class='removeThead' colspan='"+colspan+"'>"+year+"-"+numFormat(month)+"</td>";
}
// td 주차 한칸 포맷
function tdTagFormatWeek(colspan, week) {
	return "<td class='removeThead' colspan='"+colspan+"'>"+week+"주차</td>";
}
// td 날짜 한칸 포맷
function tdTagFormatDay(day) {
	return "<td class='removeThead'><div style='width: 60px;'>"+numFormat(day)+"</div></td>";
}

//테이블 thead 화면에 그리는 함수
function screenWriteThead() {
	// 월처리
	var tag = new StringBuffer();
	if(baseData.start[0] != baseData.end[0]) {
		if(calendar.iscLeafCheck(baseData.start[0])) {
			var startMonthCnt = calendar.LEAF[baseData.start[1]-1] - baseData.start[2] + 1;
			tag.append(tdTagFormatMonth(startMonthCnt, baseData.start[0], baseData.start[1]));
		}else {
			var startMonthCnt = calendar.PLAIN[baseData.start[1]-1] - baseData.start[2] + 1;
			tag.append(tdTagFormatMonth(startMonthCnt, baseData.start[0], baseData.start[1]));
		}
		tag.append(yearMonth(baseData.start[0], Number(baseData.start[1])+1, 12));
		if(Number(baseData.end[0])-Number(baseData.start[0]) > 1) {
			for(var i=Number(baseData.start[0])+1; i<Number(baseData.end[0]); i++) {
				tag.append(yearMonth(i, 1, 12));
			}
		}
		tag.append(yearMonth(baseData.end[0], 1, Number(baseData.end[1])-1));
		if(calendar.iscLeafCheck(baseData.end[0])) {
			tag.append(tdTagFormatMonth(baseData.end[2], baseData.end[0], baseData.end[1]));
		}else {
			tag.append(tdTagFormatMonth(baseData.end[2], baseData.end[0], baseData.end[1]));
		}
	}else if(baseData.start[1] == baseData.end[1]) {
		tag.append(tdTagFormatMonth(baseData.schdule(), baseData.start[0], baseData.start[1]));
	}else {
		if(calendar.iscLeafCheck(baseData.start[0])) {
			var startMonthCnt = calendar.LEAF[baseData.start[1]-1] - baseData.start[2] + 1;
			tag.append(tdTagFormatMonth(startMonthCnt, baseData.start[0], baseData.start[1]));
			tag.append(yearMonth(baseData.start[0], Number(baseData.start[1])+1, Number(baseData.end[1])-1));
			tag.append(tdTagFormatMonth(baseData.end[2], baseData.end[0], baseData.end[1]));
		}else {
			var startMonthCnt = calendar.PLAIN[baseData.start[1]-1] - baseData.start[2] + 1;
			tag.append(tdTagFormatMonth(startMonthCnt, baseData.start[0], baseData.start[1]));
			tag.append(yearMonth(baseData.start[0], Number(baseData.start[1])+1, Number(baseData.end[1])-1));
			tag.append(tdTagFormatMonth(baseData.end[2], baseData.end[0], baseData.end[1]));
		}
	}
	$("#thead tr:eq(0)").append(tag.toString());
	// 주차처리
	tag = new StringBuffer();
	var startDayIndex = monthDayIndex(calendar.make(baseData.start[0], baseData.start[1]), baseData.start[2]);
	var lastDayIndex = monthDayIndex(calendar.make(baseData.end[0], baseData.end[1]), baseData.end[2]);
	var startDayOfWeek = 7 - startDayIndex % 7;
	var lastDayOfWeek = lastDayIndex % 7;
	var remain = (baseData.schdule() - startDayOfWeek) / 7;
	tag.append(tdTagFormatWeek(startDayOfWeek, 1));
	for(var i=2; i<remain+2; i++) {
		tag.append(tdTagFormatWeek(7, i));
	}
	$("#thead tr:eq(1)").append(tag.toString());
	// 날짜처리
	var tag = new StringBuffer();
	if(baseData.start[0] != baseData.end[0]) {
		for(var i=Number(baseData.start[2]); i<=calendar.lastDay(baseData.start[0], baseData.start[1]); i++) {
			tag.append(tdTagFormatDay(i));
		}
		if(Number(baseData.end[0])-Number(baseData.start[0]) > 1) {
			tag.append(LastMonths(baseData.start[0], Number(baseData.start[1])+1));
			for(var i=Number(baseData.start[0])+1; i<Number(baseData.end[0]); i++) {
				tag.append(startMonths(i, 12));
			}
			tag.append(startMonths(baseData.end[0], Number(baseData.end[1])-1));
		}else {
			tag.append(LastMonths(baseData.start[0], Number(baseData.start[1])+1));
			tag.append(startMonths(baseData.end[0], Number(baseData.end[1])-1));
		}
		for(var i=1; i<=Number(baseData.end[2]); i++) {
			tag.append(tdTagFormatDay(i));
		}
	}else if(baseData.start[1] == baseData.end[1]) {
		for(var i=Number(baseData.start[2]); i<=Number(baseData.end[2]); i++){
			tag.append(tdTagFormatDay(i));
		}
	}else {
		var lastDay = calendar.lastDay(baseData.start[0], baseData.start[1]);
		for(var i=Number(baseData.start[2]); i<=lastDay; i++) {
			tag.append(tdTagFormatDay(i));
		}
		for(var i=Number(baseData.start[1])+1; i<Number(baseData.end[1]); i++) {
			tag.append(tagMonthOne(calendar.makeOne(baseData.start[0], i)));
		}
		for(var i=1; i<=Number(baseData.end[2]); i++){
			tag.append(tdTagFormatDay(i));
		}
	}
	$("#thead tr:eq(2)").append(tag.toString());
	// 총, 일간 진척률 처리
	tag = new StringBuffer();
	for(var i=0; i<baseData.schdule(); i++) {
		tag.append("<td class='removeThead progressDaySum'></td>");
	}
	$("#thead tr:eq(3)").append(tag.toString());
	tag = new StringBuffer();
	for(var i=0; i<baseData.schdule(); i++) {
		tag.append("<td class='removeThead progressDay'></td>");
	}
	$("#thead tr:eq(4)").append(tag.toString());
	tag = new StringBuffer();
	for(var i=0; i<baseData.schdule(); i++) {
		tag.append("<td class='removeThead'></td>");
	}
	$("#thead tr:eq(5)").append(tag.toString());
	
}