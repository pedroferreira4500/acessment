package com.readinessit.livraria.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Book.
 */
@Entity
@Table(name = "book")
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "price", nullable = false)
    private Float price;

    @NotNull
    @Column(name = "stock", nullable = false)
    private Integer stock;

    @OneToMany(mappedBy = "book")
    private Set<Basket> baskets = new HashSet<>();
    @OneToMany(mappedBy = "book")
    private Set<Sales> sales = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("books")
    private Author writer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Book name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public Book price(Float price) {
        this.price = price;
        return this;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public Book stock(Integer stock) {
        this.stock = stock;
        return this;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Set<Basket> getBaskets() {
        return baskets;
    }

    public Book baskets(Set<Basket> baskets) {
        this.baskets = baskets;
        return this;
    }

    public Book addBasket(Basket basket) {
        this.baskets.add(basket);
        basket.setBook(this);
        return this;
    }

    public Book removeBasket(Basket basket) {
        this.baskets.remove(basket);
        basket.setBook(null);
        return this;
    }

    public void setBaskets(Set<Basket> baskets) {
        this.baskets = baskets;
    }

    public Set<Sales> getSales() {
        return sales;
    }

    public Book sales(Set<Sales> sales) {
        this.sales = sales;
        return this;
    }

    public Book addSales(Sales sales) {
        this.sales.add(sales);
        sales.setBook(this);
        return this;
    }

    public Book removeSales(Sales sales) {
        this.sales.remove(sales);
        sales.setBook(null);
        return this;
    }

    public void setSales(Set<Sales> sales) {
        this.sales = sales;
    }

    public Author getWriter() {
        return writer;
    }

    public Book writer(Author author) {
        this.writer = author;
        return this;
    }

    public void setWriter(Author author) {
        this.writer = author;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Book book = (Book) o;
        if (book.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), book.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Book{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", price=" + getPrice() +
            ", stock=" + getStock() +
            "}";
    }
}
