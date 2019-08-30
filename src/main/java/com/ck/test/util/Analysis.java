package com.ck.test.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ck.test.beans.ProjectInfoDto;
import com.ck.test.beans.ProjectWbsDto;

public class Analysis {

	private String[] prjTotalDays;
	private int cnt;
	private long days;
	private int sumCnt;
	
	public Analysis(ProjectInfoDto prjDto, String[] prjTotalDays) {
		this.prjTotalDays = prjTotalDays;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = formatter.parse(prjDto.getPrjStart());
			Date lastDate = formatter.parse(prjDto.getPrjEnd());
			days = ((lastDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24)) + 1;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cnt = (int) (prjTotalDays.length / days);
	}
	
	public String[] getTermsFormat() {
		String[] prjTotalDays = new String[cnt];
		for(int i=0; i<cnt; i++) {
			prjTotalDays[i] = termFormat();
		}
		return  prjTotalDays;
	}
	private String termFormat() {
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<days; i++,sumCnt++) {
			sb.append(prjTotalDays[sumCnt]);
		}
		return sb.toString();
	}
	
}
