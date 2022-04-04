package me.tapumandal.ovirupa.domain.blog;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "blog")
public class Blog {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int id;

    @Column(name = "title")
    private String title;

    @Column(name = "blog")
    private String blog;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "blog_image_identification", nullable = true)
    private String blogImageIdentification;

    @Column(name = "is_active", columnDefinition = "boolean default 1")
    private boolean isActive = true;

    @Column(name = "is_deleted", columnDefinition = "boolean default 0")
    private boolean isDeleted = false;

    @Column(name = "created_at", updatable=false)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date updatedAt;

    public Blog(){}
    public Blog(BlogDto dto) {
        this.id = dto.getId();
        this.title = dto.getTitle();
        this.blog = dto.getBlog();
        this.productName = dto.getProductName();
        this.productId = dto.getProductId();
        this.blogImageIdentification = dto.getBlogImageIdentification();
        this.isActive = dto.isActive();
        this.isDeleted = dto.isDelete();
        this.createdAt = dto.getCreatedAt();
    }
}

