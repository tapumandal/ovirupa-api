package me.tapumandal.ovirupa.domain.home_management;


import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.List;

@Data
@Component
public class HomeManagementDto implements Serializable {

    protected int id;

    private String titleNotice;

    private boolean sliderDisplay = true;

    private String mainTagLine;

    private String categoriesTitle;

    private boolean categoriesDisplay = true;

    private String productSection;
}
