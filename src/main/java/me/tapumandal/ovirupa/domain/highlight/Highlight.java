package me.tapumandal.ovirupa.domain.highlight;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "highlight")
@Data
public class Highlight implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @Column(name="title")
    String title;

    @Column(name="followLink")
    String followLink;

    @Column(name="imageUrl")
    String imageUrl;

    public Highlight() {
    }


    public Highlight(HighlightDto highlightDto) {
        this.id = highlightDto.getId();
        this.title = highlightDto.getTitle();
        this.followLink = highlightDto.getFollowLink();
        this.imageUrl = highlightDto.getImageUrl();
    }

}
