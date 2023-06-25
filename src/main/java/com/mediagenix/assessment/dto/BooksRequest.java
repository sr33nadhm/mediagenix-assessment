package com.mediagenix.assessment.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BooksRequest {
	private long bookId;
	private String ISBN;
	private String title;
	private String author;
}
