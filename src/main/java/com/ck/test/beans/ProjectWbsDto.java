package com.ck.test.beans;

import java.sql.Date;

import lombok.Data;

@Data
public class ProjectWbsDto {
	
	private int prjWorksNo;
	private int prjCode;
	private String prjWorkName;
	private int prjGroup;
	private int prjStep;
	private int prjDepth;
	private String prjManager;
	private String prjOutput;
	private Date prjPlanStart;
	private Date prjPlanEnd;
	private Date prjRealEnd;
	private int prjWorkCompletion;
	private String prjTotalDays;
}
