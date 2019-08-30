// 100/cnt 소수점 0.00자리까지 반올림
function makeRound(cnt) {
	var remain = 100 / cnt + '';
	var point = remain.split('.');
	if(point.length == 1) {
		return Number(remain);
	}else if(point[1].length <= 2) {
		return Number(remain);
	}else {
		var roundFormat = Math.round(point[1].substr(0, 2) + '.' + point[1].substr(2, point[1].length+1));
		return Number(point[0] + '.' + roundFormat);
	}
}
// num을 소수점 0.00자리까지 반올림
function makeBaseRound(num) {
	var point = (num+'').split('.');
	if(point.length == 1) {
		return Number(num);
	}else if(point[1].length <= 2) {
		return Number(num);
	}else {
		var roundFormat = Math.round(point[1].substr(0, 2) + '.' + point[1].substr(2, point[1].length+1));
		return Number(point[0] + '.' + roundFormat);
	}
}