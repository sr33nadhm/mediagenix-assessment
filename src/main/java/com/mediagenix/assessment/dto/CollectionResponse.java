package com.mediagenix.assessment.dto;

import java.util.Date;
import java.util.SortedSet;

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
public class CollectionResponse implements Comparable<CollectionResponse>{
	private long collectionId;

	private String name;

	private SortedSet<BooksResponse> books;

	private Date createDate;

	private Date modifyDate;

	@Override
	public int compareTo(CollectionResponse o) {
		return name.compareTo(o.getName());
	}
}
