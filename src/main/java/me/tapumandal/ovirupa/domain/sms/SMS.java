package me.tapumandal.ovirupa.domain.sms;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "highlight")
@Data
public class SMS implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @Column(name="title")
    String title;

    @Column(name="followLink")
    String followLink;

    @Column(name="imageUrl")
    String imageUrl;

    public SMS() {
    }


    public SMS(SMSDto SMSDto) {
        this.id = SMSDto.getId();
        this.title = SMSDto.getTitle();
        this.followLink = SMSDto.getFollowLink();
        this.imageUrl = SMSDto.getImageUrl();
    }

}
