package me.tapumandal.ovirupa.domain.slider;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "slider")
@Data
public class Slider implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @Column(name="title")
    String title;

    @Column(name="followLink")
    String followLink;

    @Column(name="imageUrl")
    String imageUrl;

    public Slider() {
    }


    public Slider(SliderDto sliderDto) {
        this.id = sliderDto.getId();
        this.title = sliderDto.getTitle();
        this.followLink = sliderDto.getFollowLink();
        this.imageUrl = sliderDto.getImageUrl();
    }

}
