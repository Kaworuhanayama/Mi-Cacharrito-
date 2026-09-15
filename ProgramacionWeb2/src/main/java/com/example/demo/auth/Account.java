package com.example.demo.auth;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 30)
    private String identification;

    @Column(unique = true, length = 50)
    private String username;

    @Column(nullable = false, length = 120)
    private String fullName;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Role role;

    private String licenseIssueDate;
    private String licenseCategory;
    private String licenseExpirationDate;
    private String email;
    private String phone;

    protected Account() {
    }

    public Account(String identification, String username, String fullName, String password, Role role,
                   String licenseIssueDate, String licenseCategory, String licenseExpirationDate,
                   String email, String phone) {
        this.identification = identification;
        this.username = username;
        this.fullName = fullName;
        this.password = password;
        this.role = role;
        this.licenseIssueDate = licenseIssueDate;
        this.licenseCategory = licenseCategory;
        this.licenseExpirationDate = licenseExpirationDate;
        this.email = email;
        this.phone = phone;
    }

    public Long getId() { return id; }
    public String getIdentification() { return identification; }
    public String getUsername() { return username; }
    public String getFullName() { return fullName; }
    public String getPassword() { return password; }
    public Role getRole() { return role; }
    public String getLicenseIssueDate() { return licenseIssueDate; }
    public String getLicenseCategory() { return licenseCategory; }
    public String getLicenseExpirationDate() { return licenseExpirationDate; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
}