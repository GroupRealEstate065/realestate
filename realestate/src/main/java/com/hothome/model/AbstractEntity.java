package com.hothome.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.datetime.DateFormatter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.hothome.constant.Roles;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class AbstractEntity implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false)
	protected Long id;
	
	@JsonFormat(shape = Shape.STRING)
	@Column(name = "created_at", nullable = false)
	@DateTimeFormat
	@CreationTimestamp
	protected LocalDateTime createdAt;
	
	@JsonFormat(shape = Shape.STRING)
	@Column(name = "modified_at", nullable = false)
	@DateTimeFormat
	@UpdateTimestamp
	protected LocalDateTime modifiedAt;
	
	@Column(nullable = true, name = "authorities")
	protected String[] authorities;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private Roles role;

	@Column(nullable = true)
	protected boolean isActive;
	
	@Column(nullable = true)
	protected boolean isNotLocked;
	
	@Column(unique = true, nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
}
