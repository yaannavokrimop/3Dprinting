package com.netcracker.educ.printing.model.representationModel;

import com.netcracker.educ.printing.model.bean.Pageable;
import com.netcracker.educ.printing.model.bean.Role;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class UserRepresent implements Pageable {
    private UUID id;
    private String name;
    private String surname;
    private Role role;
    private String phone;
    private String email;
    private String information;

    private List<AddressRepresent> addresses;

    public UserRepresent(UUID id, String name, String surname, Role role, String phone, String email, String information, List<AddressRepresent> addresses) {
    this.id=id;
    this.name=name;
    this.surname=surname;
    this.role=role;
    this.phone=phone;
    this.email=email;
    this.information=information;
    this.addresses=addresses;
    }



}


