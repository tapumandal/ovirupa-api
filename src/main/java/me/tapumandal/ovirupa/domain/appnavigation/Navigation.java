package me.tapumandal.ovirupa.domain.appnavigation;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "navigation")
@Data
public class Navigation implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int navigationId;

    @Column(name="navigationTitle")
    String navigationTitle;

    @Column(name="navigationImageUrl")
    String navigationImageUrl;

    @Column(name="navigationChildren")
    private String navigationChildren;


    @Column(name = "meta_tag_title")
    private String metaTagTitle;


    @Column(name = "meta_tag_description")
    private String metaTagDescription;

    public Navigation() {
    }

    public Navigation(NavigationDto navigationDto) {
        this.navigationId = navigationDto.getNavigationId();
        this.navigationTitle = navigationDto.getNavigationTitle();
        this.navigationImageUrl = navigationDto.getNavigationImageUrl();
        this.navigationChildren = navigationDto.getNavigationChildren();

        this.setMetaTagTitle(navigationDto.getMetaTagTitle());
        this.setMetaTagDescription(navigationDto.getMetaTagDescription());
    }
}
