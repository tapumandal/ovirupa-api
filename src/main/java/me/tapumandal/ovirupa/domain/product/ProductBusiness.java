package me.tapumandal.ovirupa.domain.product;

import me.tapumandal.ovirupa.domain.image.Image;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
public class ProductBusiness {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int id;

    @Column(name = "name")
    private String name;

    @Column(name = "image")
    private String image;

    @Column(name = "imageName")
    private String imageName;

    @Column(name = "company")
    private String company;

    @Column(name = "categories", length = 300)
    private String categories;

    @Column(name = "preSelectedCategories", length = 300)
    private String[] preSelectedCategories;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "sellingPricePerUnit")
    private int sellingPricePerUnit;

    @Column(name = "discountPrice")
    private int discountPrice;

    @Column(name = "discountTitle")
    private String discountTitle;

    @Column(name = "unit")
    private float unit;

    @Column(name = "unitTitle")
    private String unitTitle;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "maximumOrderQuantity")
    private int maximumOrderQuantity = 20;

    @Column(name = "sortPriority")
    private int sortPriority;

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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private List<Image> productImages = new ArrayList<Image>();



    @Column(name = "color")
    private String color;

    @Column(name = "variable_product_name")
    private String variableProductName;

    @Column(name = "variable_product_price")
    private String variableProductPrice;


    @Column(name = "meta_tag_title")
    private String metaTagTitle;


    @Column(name = "meta_tag_description")
    private String metaTagDescription;

    @Column(name = "title")
    private String title;

    @Column(name = "blog")
    private String blog;


    public ProductBusiness() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image == null ? "" : image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageName() {
        return imageName == null ? "" : imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getCompany() {
        return company == null ? "" : company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCategories() {
        return categories == null ? "" : categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String[] getPreSelectedCategories() {
        return preSelectedCategories;
    }

    public void setPreSelectedCategories(String[] preSelectedCategories) {
        this.preSelectedCategories = preSelectedCategories;
    }

    public String getDescription() {
        return description == null ? "" : description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSellingPricePerUnit() {
        return sellingPricePerUnit;
    }

    public void setSellingPricePerUnit(int sellingPricePerUnit) {
        this.sellingPricePerUnit = sellingPricePerUnit;
    }

    public int getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(int discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getDiscountTitle() {
        return discountTitle == null ? "" : discountTitle;
    }

    public void setDiscountTitle(String discountTitle) {
        this.discountTitle = discountTitle;
    }

    public float getUnit() {
        return unit;
    }

    public void setUnit(float unit) {
        this.unit = unit;
    }

    public String getUnitTitle() {
        return unitTitle == null ? "" : unitTitle;
    }

    public void setUnitTitle(String unitTitle) {
        this.unitTitle = unitTitle;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getMaximumOrderQuantity() {
        return maximumOrderQuantity;
    }

    public void setMaximumOrderQuantity(int maximumOrderQuantity) {
        this.maximumOrderQuantity = maximumOrderQuantity;
    }

    public int getSortPriority() {
        return sortPriority;
    }

    public void setSortPriority(int sortPriority) {
        this.sortPriority = sortPriority;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Image> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<Image> productImages) {
        this.productImages = productImages;
    }

    public String getColor() {
        return color == null ? "" : color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getVariableProductName() {
        return variableProductName == null ? "" : variableProductName;
    }

    public void setVariableProductName(String variableProductName) {
        this.variableProductName = variableProductName;
    }

    public String getVariableProductPrice() {
        return variableProductPrice == null ? "" : variableProductPrice;
    }

    public void setVariableProductPrice(String variableProductPrice) {
        this.variableProductPrice = variableProductPrice;
    }

    public String getMetaTagTitle() {
        return metaTagTitle;
    }

    public void setMetaTagTitle(String metaTagTitle) {
        this.metaTagTitle = metaTagTitle;
    }

    public String getMetaTagDescription() {
        return metaTagDescription;
    }

    public void setMetaTagDescription(String metaTagDescription) {
        this.metaTagDescription = metaTagDescription;
    }
}

