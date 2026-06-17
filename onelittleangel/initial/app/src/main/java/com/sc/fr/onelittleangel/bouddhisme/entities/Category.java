package com.sc.fr.onelittleangel.bouddhisme.entities;

import java.io.Serializable;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class Category implements Serializable {
    private static final long serialVersionUID = 1;
    private String category;
    private List<Category> categoryList;
    private Long idCategory;
    private Category idParent;
    private List<QuoCategory> quoCategoryList;

    public Category() {
    }

    public Category(Long idCategory) {
        this.idCategory = idCategory;
    }

    public Category(String category) {
        this.category = category;
    }

    public Long getIdCategory() {
        return this.idCategory;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Category> getCategoryList() {
        return this.categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public Category getIdParent() {
        return this.idParent;
    }

    public void setIdParent(Category idParent) {
        this.idParent = idParent;
    }

    public List<QuoCategory> getQuoCategoryList() {
        return this.quoCategoryList;
    }

    public void setQuoCategoryList(List<QuoCategory> quoCategoryList) {
        this.quoCategoryList = quoCategoryList;
    }

    public int hashCode() {
        int hash = 0 + (this.idCategory != null ? this.idCategory.hashCode() : 0);
        return hash;
    }

    public boolean equals(Object object) {
        if (!(object instanceof Category)) {
            return false;
        }
        Category other = (Category) object;
        if (this.idCategory != null || other.idCategory == null) {
            return this.idCategory == null || this.idCategory.equals(other.idCategory);
        }
        return false;
    }

    public String toString() {
        return String.format("Category[ %s, %s ]", this.idCategory, this.category);
    }
}
