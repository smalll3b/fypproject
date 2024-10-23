package com.example.fypproject;

public class Product {
    public String _name;
    public String _brand;
    public float  _price;
    public float  _weight;
    public int    _quantity;
    public String _description;
    public String _imageUri;

    public Product(String name, String brand, float price, float weight, int quantity, String description, String imageUri){
        this._name = name;
        this._brand = brand;
        this._price = price;
        this._weight = weight;
        this._quantity = quantity;
        this._description = description;
        this._imageUri = imageUri;
    }
}
