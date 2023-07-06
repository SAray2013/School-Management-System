package com.tday.school_management_system.config.security;

import java.util.Optional;

public interface ApplicationUserService {
    Optional<AuthUser> loadUserByUsername(String username);
}