package com.hospitalmanagementsystem.hospitalmanagementsystem.Security;

import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Type.PermissionType;
import com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Type.RoleType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Type.PermissionType.*;
import static com.hospitalmanagementsystem.hospitalmanagementsystem.Entity.Type.RoleType.*;

@Service
public class RolePermissionMapper {

    public static Map<RoleType, Set<PermissionType>> map = Map.of(
            PATIENT,Set.of(APPOINTMENT_READ,APPOINTMENT_WRITE,PATIENT_READ),
            DOCTOR,Set.of(APPOINTMENT_READ,APPOINTMENT_DELETE,APPOINTMENT_WRITE,PATIENT_READ),
            ADMIN,Set.of(APPOINTMENT_READ,APPOINTMENT_DELETE,APPOINTMENT_WRITE,PATIENT_READ,PATIENT_WRITE,USER_MANAGE,REPORT_VIEW)
    );

    public static Set<SimpleGrantedAuthority> getAuthorities(RoleType role){
        return map.get(role).stream().map(
                authority-> new SimpleGrantedAuthority(authority.getPermission())
        ).collect(Collectors.toSet());
    }
}
