package com.ck.test.beans;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProjectInfoDto {
	private int prjCode;
	private String prjName;
	private int prjDeposit;
	private int prjWorkingExpenses;
	private String prjStart;
	private String prjEnd;
	private String prjMothercompany;
	private int prjCompletion;
	
}
