package com.mediagenix.assessment.entity;

import java.util.Date;
import java.util.SortedSet;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SortNatural;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "books_tbl")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BookEntity implements Comparable<BookEntity> {

	@Id
	@GeneratedValue
	@Column(name = "book_id")
	@EqualsAndHashCode.Include
	private long bookId;

	@Column(name = "isbn")
	@Pattern(regexp = "ISBN(-1(?:(0)|3))?:?\\x20(\\s)*[0-9]+[- ][0-9]+[- ][0-9]+[- ][0-9]*[- ]*[xX0-9]")
	private String ISBN;

	@Column(name = "title")
	private String title;

	@Column(name = "author")
	private String author;

	@Column(name = "collections")
	@ManyToMany(mappedBy = "books", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JsonManagedReference
	@SortNatural
	private SortedSet<CollectionEntity> collections;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date")
	private Date createDate;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modify_date")
	private Date modifyDate;

	public void removeAttachedCollections(CollectionEntity collection) {
		this.collections.remove(collection);
		collection.getBooks().remove(this);
	}

	@Override
	public int compareTo(BookEntity o) {
		return title.compareTo(o.getTitle());
	}

}
