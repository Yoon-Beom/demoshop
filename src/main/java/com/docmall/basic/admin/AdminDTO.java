package com.docmall.basic.admin;

import java.util.Date;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ToString
public class AdminDTO {
	private String ad_userid;
	private String ad_passwd;
	private Date login_date;
}
