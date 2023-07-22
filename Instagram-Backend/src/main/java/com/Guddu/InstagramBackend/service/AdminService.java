package com.Guddu.InstagramBackend.service;

import com.Guddu.InstagramBackend.repository.IAdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    IAdminRepo adminRepo;

}
