package com.localbrand.common;

public interface Interface_API {
    String MAIN = "/api/webtpf";

    String UPLOAD_IMAGE = "/upload/image";
    interface Cors {
        String CORS = "*";
    }
    interface API {
        interface Color {
            String COLOR_FIND_ALL = "/admin/colors/getall";
            String COLOR_FIND_SORT = "/admin/colors/getallsort";
            String COLOR_FIND_BY_ID = "/admin/colors/findbyid/{idColor}";
            String COLOR_SAVE = "/admin/colors/save";
            String COLOR_DELETE = "/admin/colors/delete";
            String COLOR_SEARCH = "/admin/colors/search";
            String COLOR_FIND_BY_STATUS = "/admin/colors/findbystatus";
            String COLOR_FIND_EXISTS = "/admin/colors/findexists";
        }

        interface Size {
            String SIZE_FIND_ALL = "/admin/sizes/getall";
            String SIZE_FIND_SORT = "/admin/sizes/getallsort";
            String SIZE_FIND_BY_ID = "/admin/sizes/findbyid/{idSize}";
            String SIZE_SAVE = "/admin/sizes/save";
            String SIZE_DELETE = "/admin/sizes/delete";
            String SIZE_SEARCH = "/admin/sizes/search";
            String SIZE_FIND_BY_STATUS = "/admin/sizes/findbystatus";
            String SIZE_FIND_EXISTS = "/admin/sizes/findbyidcategory/{idCategory}";

        }

        interface Sale {
            String SALE_FIND_ALL = "/admin/sales/getall";
            String SALE_FIND_SORT = "/admin/sales/getallsort";
            String SALE_FIND_BY_ID = "/admin/sales/findbyid/{idSale}";
            String SALE_SAVE = "/admin/sales/save";
            String SALE_DELETE = "/admin/sales/delete";
            String SALE_SEARCH = "/admin/sales/search";
            String SALE_FIND_BY_STATUS = "/admin/sales/findbystatus";

        }

        interface Voucher {
            String VOUCHER_FIND_ALL = "/admin/vouchers/getall";
            String VOUCHER_FIND_SORT = "/admin/vouchers/getallsort";
            String VOUCHER_FIND_BY_ID = "/admin/vouchers/findbyid/{idVoucher}";
            String VOUCHER_SAVE = "/admin/vouchers/save";
            String VOUCHER_DELETE = "/admin/vouchers/delete";
            String VOUCHER_SEARCH = "/admin/vouchers/search";
            String VOUCHER_FIND_BY_STATUS = "/admin/vouchers/findbystatus";

        }

        interface Category {
            String CATEGORY_TO_SIZE = "/admin/categories/getexists";
            interface Category_Parent {
                String CATEGORY_PARENT_FIND_ALL = "/admin/categories/parent/getall";
                String CATEGORY_PARENT_FIND_SORT = "/admin/categories/parent/getallsort";
                String CATEGORY_PARENT_FIND_BY_ID = "/admin/categories/parent/findbyid/{idCategory}";
                String CATEGORY_PARENT_SAVE = "/admin/categories/parent/save";
                String CATEGORY_PARENT_DELETE = "/admin/categories/parent/delete";
                String CATEGORY_PARENT_SEARCH = "/admin/categories/parent/search";
                String CATEGORY_PARENT_FIND_BY_STATUS = "/admin/categories/parent/findbystatus";
            }
            interface Category_Child {
                String CATEGORY_CHILD_FIND_ALL = "/admin/categories/child/getall";
                String CATEGORY_CHILD_FIND_SORT = "/admin/categories/child/getallsort";
                String CATEGORY_CHILD_FIND_BY_ID = "/admin/categories/child/findbyid/{idCategory}";
                String CATEGORY_CHILD_SAVE = "/admin/categories/child/save";
                String CATEGORY_CHILD_DELETE = "/admin/categories/child/delete";
                String CATEGORY_CHILD_SEARCH = "/admin/categories/child/search";
                String CATEGORY_CHILD_FIND_BY_STATUS = "/admin/categories/child/findbystatus";
                String CATEGORY_CHILD_FIND_BY_PARENT_ID = "/admin/categories/child/findbyparent/{parentId}";
            }
        }

        interface Product {
            String PRODUCT_ADD = "/admin/products/save";
            String PRODUCT_GET_LIST_PARENT = "/admin/products/getlistparent";
            String PRODUCT_GET_LIST_CHILD = "/admin/products/getlistchild";
            String PRODUCT_SHOW = "/admin/products/show";
            String PRODUCT_DELETE_PARENT = "/admin/products/deleteparent";
            String PRODUCT_DELETE_CHILD = "/admin/products/deletechild";
            String PRODUCT_SEARCH = "/admin/products/search";
            String PRODUCT_GET_ALL_USER = "/user/products/getlistproduct";
            String PRODUCT_SEARCH_USER = "/user/products/search";
            String PRODUCT_SHOW_USER = "/user/products/showdetail";
            String PRODUCT_SHOW_USER_BY_COLOR = "/user/products/showdetailbycolor";
            String PRODUCT_SHOW_USER_BY_COLOR_AND_SIZE = "/user/products/showdetailbycolorandsize";
            String PRODUCT_SHOW_ALL = "/user/products/showall";
            String PRODUCT_NEW = "/user/products/findproductnew";
            String PRODUCT_HOT = "/user/products/findproducthot";
            String PRODUCT_RELATED = "/user/products/findproductrelated";
        }

        interface Cart {
            String DELETE_ALL = "/carts/delete-all/{idUser}";
            interface Cart_Product {
                String SAVE = "/carts/products/save";
                String DELETE = "/carts/products/delete/{idCartProduct}";
                String FIND_BY_NAME = "/carts/products/search";
            }
            interface CartCombo {
                String SEARCH = "/carts/combos/search-name";
                String SAVE = "/carts/combos/save";
                String DELETE = "/carts/combos/delete/{idCartProduct}";
            }
        }
    }
}
