package com.ntrpk.schoolregistration.configuration;

import com.ntrpk.schoolregistration.model.Model;
import org.springframework.beans.factory.aspectj.ConfigurableObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

@Configuration
public class EntityListener implements ConfigurableObject {

    @PrePersist
    public void touchForCreate(Object target) {
        touchForUpdate(target);
        if (target instanceof Model) {
            Model model = (Model) target;
            model.setCreatedBy(model.getUpdatedBy());
            model.setCreatedAt(model.getUpdatedAt());
        }
    }

    @PreUpdate
    public void touchForUpdate(Object target) {
        if (target instanceof Model) {
            Model model = (Model) target;
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof UserDetails) {
                model.setUpdatedBy(((UserDetails) principal).getUsername());
            } else {
                model.setUpdatedBy("System");
            }
            model.setUpdatedAt(new Date());
        }
    }
}
