package me.tapumandal.ovirupa.domain.category;

import me.tapumandal.ovirupa.service.Service;

import java.util.List;

public interface CategoryService extends Service<Category, Category> {

    public List<Category> getAll();
}
