package com.example.library.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Table(name = "logins")
@EqualsAndHashCode
@ToString
public class Login {
 /*
    Project     : Library Service
    Class       : Login.java
    Author      : Johan Eka Santosa
    Created On  : Tue, 08-06-2021

    Version History:
        v1.0.0 => Initial Work (Tue, 08-06-2021 by Johan Eka Santosa)
 */

    @Id
    @Column(name = "session_id", updatable = false, nullable = false)
    private String sessionId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "is_valid")
    private Boolean isValid;

    public Login(String userId) {
        this.sessionId = UUID.randomUUID().toString().replaceAll("-", "");
        this.userId = userId;
        this.createdAt = Calendar.getInstance().getTime();
        this.isValid = true;
    }

    public Login() { }

}
