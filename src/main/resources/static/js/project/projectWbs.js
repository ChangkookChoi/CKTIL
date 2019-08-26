	var baseData = {} // ProjectInfoDto
	var subData = {} // List<ProjectWbsDto>


/*최초 ProjectWbs 화면 그리기 */
$(document).ready(function(){


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
					swal("오류", "WBS를 불러오던 중 오류가 발생했습니다.");
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
	tag.append('<li><a onclick="">하위추가</a></li>');
	tag.append('<li><a onclick="createParentList('+num+')">상위추가</a></li>');
	tag.append('</ul></div>');
	tag.append("<input type='hidden' name='inPrjGroup' value='"+group+"'>");
	tag.append("<input type='hidden' name='inPrjStep' value='"+step+"'>");
	tag.append("<input type='hidden' name='inPrjdepth' value='"+depth+"'>");
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