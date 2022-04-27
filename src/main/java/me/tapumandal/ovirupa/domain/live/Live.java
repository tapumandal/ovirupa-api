package me.tapumandal.ovirupa.domain.live;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "live")
@Data
public class Live implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @Column( name = "title")
    private String title;

    @Column( name = "image")
    private String image;

    @Column( name = "description")
    private String description;

    @Column( name = "facebook_link")
    private String facebookLink;

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


    public Live() {
    }


    public Live(LiveDto liveDto) {
        this.id = liveDto.getId();
        this.title = liveDto.getTitle();
        this.description = liveDto.getDescription();
        this.image = liveDto.getImage();
        this.facebookLink = liveDto.getFacebookLink();
    }

}
