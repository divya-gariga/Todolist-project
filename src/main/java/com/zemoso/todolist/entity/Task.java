package com.zemoso.todolist.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="tasks")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @NotBlank(message="is required")
    @Column(name="title")
    private String title;

    @NotBlank(message="is required")
    @Column(name="description")
    private String description;

    @Column(name="is_complete")
    private boolean isComplete;

    @Column(name="username")
    private String username;
}
