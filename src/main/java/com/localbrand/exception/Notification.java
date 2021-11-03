package com.localbrand.exception;

public interface Notification {
    String PAGE_INVALID = "Invalid page number";
    interface Size {
        String GET_LIST_SIZE_SUCCESS = "Get list size success";
        String GET_SIZE_BY_ID_SUCCESS = "Get size by id success";
        String GET_SIZE_BY_ID_FALSE = "Size does not exists";
        String GET_SIZE_AND_SORT_SUCESS = "Get size and sort is success";
        String GET_SIZE_AND_SORT_FALSE = "Invalid sort";
        String SAVE_SIZE_SUCCESS = "Save size is success";
        String SAVE_SIZE_FALSE = "Save size is false";
        String DELETE_SIZE_SUCCESS = "Delete size is success";
        String DELETE_SIZE_FALSE = "Delete size is false";
        String SEARCH_SIZE_SUCCESS = "Search size is success";
        String FIND_SIZE_BY_STATUS_SUCCESS = "Find size by status is success";
        interface Validate_Size {
            String VALIDATE_ID = "Invalid id size";
            String VALIDATE_NAME = "Invalid name size";
            String VALIDATE_ID_CATEGORY = "Invalid id category";
            String VALIDATE_STATUS = "Invalid status size";
        }
    }
    interface Color {
        String GET_LIST_COLOR_SUCCESS = "Get list color success";
        String GET_COLOR_BY_ID_SUCCESS = "Get color by id success";
        String GET_COLOR_BY_ID_FALSE = "Color does not exists";
        String GET_COLOR_AND_SORT_SUCESS = "Get color and sort is success";
        String GET_COLOR_AND_SORT_FALSE = "Invalid sort";
        String SAVE_COLOR_SUCCESS = "Save color is success";
        String SAVE_COLOR_FALSE = "Save color is false";
        String DELETE_COLOR_SUCCESS = "Delete color is success";
        String DELETE_COLOR_FALSE = "Delete color is false";
        String SEARCH_COLOR_SUCCESS = "Search color is success";
        String FIND_COLOR_BY_STATUS_SUCCESS = "Find color by status is success";
        String FIND_COLOR_EXISTS = "Find color exists is success";
        interface Validate_Color {
            String VALIDATE_ID = "Invalid id color";
            String VALIDATE_NAME = "Invalid name color";
            String VALIDATE_STATUS = "Invalid status color";
        }
    }
    interface Sale {
        String GET_LIST_SALE_SUCCESS = "Get list sale success";
        String GET_SALE_BY_ID_SUCCESS = "Get sale by id success";
        String GET_SALE_BY_ID_FALSE = "Sale does not exists";
        String GET_SALE_AND_SORT_SUCESS = "Get sale and sort is success";
        String GET_SALE_AND_SORT_FALSE = "Invalid sort";
        String SAVE_SALE_SUCCESS = "Save sale is success";
        String SAVE_SALE_FALSE = "Save sale is false";
        String DELETE_SALE_SUCCESS = "Delete sale is success";
        String DELETE_SALE_FALSE = "Delete sale is false";
        String SEARCH_SALE_SUCCESS = "Search sale is success";
        String FIND_SALE_BY_STATUS_SUCCESS = "Find sale by status is success";
        interface Validate_Sale {
            String VALIDATE_ID = "Invalid id sale";
            String VALIDATE_NAME = "Invalid name sale";
            String VALIDATE_DISCOUNT = "Invalid discount sale";
            String VALIDATE_STATUS = "Invalid status sale";
        }
    }
    interface Voucher {
        String GET_LIST_VOUCHER_SUCCESS = "Get list voucher success";
        String GET_VOUCHER_BY_ID_SUCCESS = "Get voucher by id success";
        String GET_VOUCHER_BY_ID_FALSE = "Voucher does not exists";
        String GET_VOUCHER_AND_SORT_SUCESS = "Get voucher and sort is success";
        String GET_VOUCHER_AND_SORT_FALSE = "Invalid sort";
        String SAVE_VOUCHER_SUCCESS = "Save voucher is success";
        String SAVE_VOUCHER_FALSE = "Save voucher is false";
        String DELETE_VOUCHER_SUCCESS = "Delete voucher is success";
        String DELETE_VOUCHER_FALSE = "Delete voucher is false";
        String SEARCH_VOUCHER_SUCCESS = "Search voucher is success";
        String FIND_VOUCHER_BY_STATUS_SUCCESS = "Find voucher by status is success";
        interface Validate_Voucher {
            String VALIDATE_ID = "Invalid id voucher";
            String VALIDATE_NAME = "Invalid name voucher";
            String VALIDATE_DISCOUNT = "Invalid discount voucher";
            String VALIDATE_DATE_END = "Invalid date end";
            String VALIDATE_STATUS = "Invalid status voucher";
        }
    }
    interface Category {
        String GET_LIST_CATEGORY_PARENT_SUCCESS = "Get list category parent success";
        String GET_CATEGORY_PARENT_BY_ID_SUCCESS = "Get category parent by id success";
        String GET_CATEGORY_PARENT_BY_ID_FALSE = "Category parent does not exists";
        String GET_CATEGORY_PARENT_AND_SORT_SUCESS = "Get category and sort is success";
        String GET_CATEGORY_PARENT_AND_SORT_FALSE = "Invalid sort";
        String SAVE_CATEGORY_PARENT_SUCCESS = "Save category parent is success";
        String SAVE_CATEGORY_PARENT_FALSE = "Save category parent is false";
        String DELETE_CATEGORY_PARENT_SUCCESS = "Delete category parent is success";
        String DELETE_CATEGORY_PARENT_FALSE = "Delete category parent is false";
        String SEARCH_CATEGORY_PARENT_SUCCESS = "Search category is success";
        String FIND_CATEGORY_PARENT_BY_STATUS_SUCCESS = "Find category by status is success";
        String GET_LIST_PARENT_TO_SIZE = "Get list category to size";


        String GET_LIST_CATEGORY_CHILD_SUCCESS = "Get list category child success";
        String GET_CATEGORY_CHILD_BY_ID_SUCCESS = "Get category child by id success";
        String GET_CATEGORY_CHILD_BY_ID_FALSE = "Category does not exists";
        String GET_CATEGORY_CHILD_AND_SORT_SUCESS = "Get category and sort is success";
        String GET_CATEGORY_CHILD_AND_SORT_FALSE = "Invalid sort";
        String SAVE_CATEGORY_CHILD_SUCCESS = "Save category parent is success";
        String SAVE_CATEGORY_CHILD_FALSE = "Save category parent is false";
        String DELETE_CATEGORY_CHILD_SUCCESS = "Delete category parent is success";
        String DELETE_CATEGORY_CHILD_FALSE = "Delete category parent is false";
        String SEARCH_CATEGORY_CHILD_SUCCESS = "Search category is success";
        String FIND_CATEGORY_CHILD_BY_STATUS_SUCCESS = "Find category by status is success";

        String GET_CATEGORY_CHILD_BY_PARENT_SUCCESS = "Get list category child success";
        interface Validate_Category {
            String VALIDATE_ID = "Invalid id category";
            String VALIDATE_ID_PARENT = "Invalid id parent category";
            String VALIDATE_NAME = "Invalid name category";
            String VALIDATE_STATUS = "Invalid status category";
        }
    }

    interface Product {
        interface Validate_Product_Request {
            String VALIDATE_ID_PRODUCT = "Invalid id product";
            String VALIDATE_NAME_PRODUCT = "Invalid name product";
            String VALIDATE_STATUS_PRODUCT = "Invalid status product";
            String VALIDATE_PRODUCT_DETAIL = "Invalid product detail";
            String VALIDATE_FRONT_PHOTO = "Invalid front photo";
            String VALIDATE_BACK_PHOTO = "Invalid back photo";
            String VALIDATE_COVER_PHOTO = "Invalid cover photo";

            String VALIDATE_ID_PRODUCT_DETAIL = "Invalid id product detail";
            String VALIDATE_ID_GENDER = "Invalid id gender";
            String VALIDATE_ID_CATEGORY = "Invalid id category";
            String VALIDATE_LIST_COLOR = "Invalid list color product";
            String VALIDATE_ID_COLOR = "Invalid id color";
            String VALIDATE_LIST_SIZE_IN_COLOR = "Invalid list size product";
            String VALIDATE_ID_SIZE = "Invalid id size";
            String VALIDATE_QUANTITY = "Invalid quantity product detail";
            String VALIDATE_PRICE = "Invalid price product detail";
            String VALIDATE_ID_SALE = "Invalid id sale";
            String VALIDATE_DETAIL_PHOTO = "Invalid detail photo";
        }

        String SAVE_PRODUCT_SUCCESS = "Save product success";
        String GET_LIST_PRODUCT_PARENT = "Get list product parent success";
        String GET_LIST_PRODUCT_CHILD = "Get list product child success";
        String SHOW_PRODUCT_IS_FALSE = "Product is not found";
        String SHOW_PRODUCT_IS_SUCCESS = "Show product is success";
        String DELETE_PRODUCT_IS_FALSE = "Delete product is false";
        String DELETE_PRODUCT_IS_SUCCESS = "Delete product is success";
        String DELETE_PRODUCT_DETAIL_IS_FALSE = "Delete product detail is false";
        String DELETE_PRODUCT_DETAIL_IS_SUCCESS = "Delete product detail is success";
        String SEARCH_PRODUCT_BY_NAME_SUCCESS = "Search product is success";
        String GET_LIST_PRODUCT_ON_USER_SUCCESS = "Get list product on user success";
        String SEARCH_PRODUCT_ON_USER_SUCCESS = "Search product on user success";
    }

    interface Cart{
        interface Validate_Cart{
            String VALIDATE_ID_CART = "Invalid id cart";
            String VALIDATE_ID_USER = "Invalid id user";
            String VALIDATE_ID_STATUS = "Invalid id status";
        }
        interface Validate_Cart_Product{
            String VALIDATE_ID_CART_PRODUCT = "Invalid id cart product";

        }
    }
}
