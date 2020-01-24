package com.fashionista.api.constants;

public class RouteConstants {
    public static final String ADMIN_ROOT = "/api/admin";
    public static final String ADMIN_CREATE_TAG = "/create-tag";
    public static final String ADMIN_CREATE_PRODUCT = "/create-product";

    public static final String AUTH_ROOT = "/api/auth";
    public static final String AUTH_LOGIN = "/login";
    public static final String AUTH_REGISTER = "/register";

    public static final String PRODUCT_ROOT = "/api/products";
    public static final String PRODUCTS_GET = "";
    public static final String PRODUCTS_SEARCH = "/search/{name}";
    public static final String PRODUCT_GET = "/product/{id}";
    public static final String PRODUCT_IMAGE_GET = "/image/{filename}";
    public static final String PRODUCT_ADD_REVIEW = "/add-review/{id}";
    public static final String PRODUCT_GET_REVIEWS = "/reviews/{id}";

    public static final String CART_ROOT = "/api/cart";
    public static final String CART_GET = "";
    public static final String CART_ADD_PRODUCT = "/add-product";
    public static final String CART_DELETE_CART = "/delete-cart/{id}";

    public static final String PURCHASES_ROOT = "/api/purchases";
    public static final String PURCHASE_CART = "/purchase-cart";
    public static final String PURCHASES_GET = "";
    public static final String PURCHASE_GET = "/purchase/{id}";

    public static final String TAG_ROOT = "/api/tags";
    public static final String TAG_SEARCH = "/search-tag/{name}";
}
