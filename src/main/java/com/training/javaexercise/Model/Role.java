package com.training.javaexercise.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    private Long roleId;

    @NotNull
    private String roleCode;

    @NotNull
    private String roleLabel;

    // OPTIONAL
//    @ManyToMany
//    @JsonIgnore
//    private Set<User> roleUser;

}
