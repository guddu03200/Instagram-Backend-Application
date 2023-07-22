package com.Guddu.InstagramBackend.repository;

import com.Guddu.InstagramBackend.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAdminRepo extends JpaRepository<Admin, Long> {
}
