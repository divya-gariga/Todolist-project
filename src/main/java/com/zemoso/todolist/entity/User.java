package com.zemoso.todolist.entity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
public class User {
    @Id
    @Column(name="username")
    private String username;
    @Column(name="password")
    private String password;
    @Column(name="enabled")
    private int enabled;

    @OneToMany(fetch =FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="username")
    private List<Task> todos;
}
