package me.tapumandal.ovirupa.domain.home_management;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "home_management")
@Component
public class HomeManagement {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int id;


    @Column(name="titleNotice")
    private String titleNotice;

    @Column(name="sliderDisplay")
    private boolean sliderDisplay = true;

    @Column(name="mainTagLine")
    private String mainTagLine;

    @Column(name="categoriesTitle")
    private String categoriesTitle;

    @Column(name="categoriesDisplay")
    private boolean categoriesDisplay = true;

    @Column(name="productSection")
    private String productSection;


    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date updatedAt;



    public HomeManagement(){}

    public HomeManagement(HomeManagementDto homeManagementDto) {
        this.id = homeManagementDto.getId();

        this.titleNotice = homeManagementDto.getTitleNotice();
        this.sliderDisplay = homeManagementDto.isSliderDisplay();
        this.mainTagLine = homeManagementDto.getMainTagLine();
        this.categoriesTitle = homeManagementDto.getCategoriesTitle();
        this.categoriesDisplay = homeManagementDto.isCategoriesDisplay();
        this.productSection = homeManagementDto.getProductSection();

    }
}
