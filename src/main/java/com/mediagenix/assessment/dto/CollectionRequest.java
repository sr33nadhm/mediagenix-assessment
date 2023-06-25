package com.mediagenix.assessment.dto;

import java.util.List;

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
public class CollectionRequest {
	private Long collectionId;
	private String name;
	private List<BooksRequest> books;
}
