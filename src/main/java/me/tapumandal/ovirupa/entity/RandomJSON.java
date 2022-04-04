package me.tapumandal.ovirupa.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by TapuMandal on 11/10/2021.
 * For any query ask online.tapu@gmail.com
 */

@Data
@Entity
@Table(name = "random_json")
public class RandomJSON {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int id;

    @Column(name = "image", length = 10000)
    private String json;


    @Column(name = "created_at", updatable=false)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date updatedAt;
}
