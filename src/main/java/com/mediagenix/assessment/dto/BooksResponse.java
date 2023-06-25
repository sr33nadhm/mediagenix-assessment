package com.mediagenix.assessment.dto;

import java.util.Date;
import java.util.SortedSet;

import com.mediagenix.assessment.entity.CollectionEntity;

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
public class BooksResponse implements Comparable<BooksResponse> {
	private long bookId;

	private String ISBN;

	private String title;

	private String author;
	
	private SortedSet<CollectionEntity> collections;

	private Date createDate;

	private Date modifyDate;

	@Override
	public int compareTo(BooksResponse o) {
		return title.compareTo(o.getTitle());
	}
}
