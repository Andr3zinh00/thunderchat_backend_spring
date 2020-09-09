package com.pw.thunderchat.model;

import org.joda.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author André
 * Pensando em como implementar essa classe...
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseEntity {

	@CreatedDate
	private LocalDateTime createdAt;
	@LastModifiedDate
	private LocalDateTime updatedAt;
}
