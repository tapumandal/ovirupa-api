package me.tapumandal.ovirupa.domain.image;

import org.hibernate.Session;

import java.util.List;

public interface ImageRepository{

    public Session getSession();

    public int create(Image images);

    public Image getImageByName(String name);

    public boolean delete(String name);

    public List<Image> getImageByProductId(int productId);

    public List<Image> getImageByBlogImageIdentification(String number);

    List<Image> getAllByImageBlogIdentifire(String value);
}
