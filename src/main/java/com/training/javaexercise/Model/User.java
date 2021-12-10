package com.training.javaexercise.Model;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "USER_SEQ")
    @SequenceGenerator(name = "USER_SEQ", sequenceName = "USER_SEQUENCE")
    private Long userId;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    @JoinTable(
            name = "UserRole",
            joinColumns = @JoinColumn(
                    name = "user_userId",
                    referencedColumnName = "userId") ,
            inverseJoinColumns = @JoinColumn(
                    name = "role_roleId",
                    referencedColumnName = "roleId") )
    private Set<Role> roles;
}
