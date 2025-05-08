package com.docmall.basic.admin;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdminService {
	private final AdminMapper adminMapper;
	
	public AdminDTO getAdminById(String ad_userid) throws Exception {
		return adminMapper.getAdminById(ad_userid);
	}
}
