package com.ElectronicStore.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
public class Category
{
    @Id
    @Column(name = "id")
    private String categoryId;
    @Column(name = "catgegory_title",length = 60)
    private String title;
    @Column(name = "category_desc",length = 50)
    private String description;
    private String coverImage;
}
