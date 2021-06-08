package com.example.library.entity.request;

import lombok.Data;

 /*
    Project     : Library Service
    Class       : UserDTO.java
    Author      : Johan Eka Santosa
    Created On  : Mon, 07-06-2021

    Version History:
        v1.0.0 => Initial Work (Mon, 07-06-2021 by Johan Eka Santosa)
 */

@Data
public class UserDTO {
    private String name;
    private String email;
    private String password;
}
