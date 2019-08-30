package com.ck.test.beans;


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
	private String prjPlanStart;
	private String prjPlanEnd;
	private String prjRealEnd;
	private int prjWorkCompletion;
	private String prjTotalDays;
	
	public ProjectWbsDto() {
		
	}
	
	public ProjectWbsDto(int prjCode, int prjGroup, int prjStep, int prjDepth, int prjWorkCompletion, String prjWorkName, 
						String prjPlanStart, String prjPlanEnd, String prjRealEnd, String prjManager, String prjOutput, String prjTotalDays) {
		this.prjCode = prjCode;
		this.prjGroup = prjGroup;
		this.prjStep = prjStep;
		this.prjDepth = prjDepth;
		this.prjWorkCompletion = prjWorkCompletion;
		this.prjWorkName = prjWorkName;
		this.prjPlanStart = prjPlanStart;
		this.prjPlanEnd = prjPlanEnd;
		this.prjRealEnd = prjRealEnd;
		this.prjManager = prjManager;
		this.prjOutput = prjOutput;
		this.prjTotalDays = prjTotalDays;
	}
}
