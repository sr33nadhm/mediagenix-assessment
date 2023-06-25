package com.mediagenix.assessment.entity;

import java.util.Date;
import java.util.SortedSet;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SortNatural;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "collections_tbl")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CollectionEntity implements Comparable<CollectionEntity> {

	@Id
	@GeneratedValue
	@Column(name = "collection_id")
	private long collectionId;

	@Column(name = "name", unique = true)
	@EqualsAndHashCode.Include
	private String name;

	@Column(name = "books")
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@JsonBackReference
	@SortNatural
	private SortedSet<BookEntity> books;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date")
	private Date createDate;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modify_date")
	private Date modifyDate;
	
	public void addBook(BookEntity book) {
        this.books.add(book);
        book.getCollections().add(this);
    }
  
    public void removeBook(BookEntity book) {
        this.books.remove(book);
        book.getCollections().remove(this);
    }

	@Override
	public int compareTo(CollectionEntity o) {
		return name.compareTo(o.getName());
	}
}
