package com.localbrand.common;

public interface Interface_API {
    String MAIN = "/api/webtpf";

    String UPLOAD_IMAGE = "/upload/image";
    interface Cors {
        String CORS = "*";
    }

    interface API {
        interface Auth {
            String LOGIN = "/login";
            String SIGN_UP = "/signup";
            String LOG_OUT = "/logout";
            String UPDATE_PROFILE = "/updateprofile";
            String REFRESH_TOKEN = "/refreshtoken";
            String SIGN_UP_ACCOUNT_EMPLOYEE = "/signupaccountemployee";
            String GET_LIST_ROLE_DETAIL = "/getroles";
            String GET_ALL_ROLE_DETAIL = "/admin/roles/findAllrole";
            String ACCEPT_ROLE = "/admin/roles/acceptrole";
            String CHANGE_PASSWORD = "/profile/changepassword";
            String GET_NEW_PASSWORD = "/profile/getnewpassword";
        }
        interface Color {
            String COLOR_FIND_ALL = "/admin/colors/getall";
            String COLOR_GET_ALL = "/admin/colors/findall";
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
            String SIZE_FIND_BY_CATEGORY = "/admin/sizes/findbycategory/{idCategory}";

        }

        interface Sale {
            String SALE_FIND_ALL = "/admin/sales/getall";
            String SALE_FIND_SORT = "/admin/sales/getallsort";
            String SALE_FIND_BY_ID = "/admin/sales/findbyid/{idSale}";
            String SALE_SAVE = "/admin/sales/save";
            String SALE_DELETE = "/admin/sales/delete";
            String SALE_SEARCH = "/admin/sales/search";
            String SALE_FIND_BY_STATUS = "/admin/sales/findbystatus";
            String SALE_ADD_TO_LIST_PRODUCT_DETAIL = "/admin/sales/addtoproduct";
            String SALE_CANCEL_TO_LIST_PRODUCT_DETAIL = "/admin/sales/canceltoproduct";
            String SALE_GET_LIST_PRODUCT_SALE = "/admin/sales/getlistproductsale";

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
                String CATEGORY_PARENT_FIND_ALL_PARENT = "/admin/categories/parent/getallcategoryparent";
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
                String CATEGORY_CHILD_FIND_ALL_BY_PARENT_ID = "/admin/categories/child/findallbyparent/{parentId}";
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
            String PRODUCT_LIKE = "/user/products/findproductlike";
        }

        interface Cart {
            String CART_SAVE = "/user/carts/save";
            String CART_GET_BY_USER = "/user/carts/getcart";
            interface Cart_Product{
                String CART_PRODUCT_GET_LIST = "/user/carts/getlistcartproduct";
                String CART_PRODUCT_DELETE = "/user/carts/deletecartproduct";
                String CART_PRODUCT_UPDATE_QUANTITY = "/user/carts/updatecartproduct";
                String CART_PRODUCT_ADD = "/user/carts/addcartproduct";
                String CART_COUNT_PRODUCT_ADD = "/user/carts/counttotal";
            }
            interface Cart_Combo{
                String CART_COMBO_DELETE = "/user/carts/deletecartcombo";
                String CART_COMBO_UPDATE_QUANTITY = "/user/carts/updatecartcombo";
                String CART_COMBO_ADD = "/user/carts/addcartcombo";
            }
        }

        interface Bill {
            String BILL_SAVE_USER = "/user/bills/save";
            String BILL_SAVE_ADMIN = "/admin/bills/save";
            String BILL_CANCEL_USER = "/user/bills/cancel";
            String BILL_CANCEL_ADMIN = "/admin/bills/cancel";
            String BILL_GET_ALL_LIST_USER = "/user/bills/getalllist";
            String BILL_GET_ALL_LIST_USER_OTHER = "/user/bills/getalllistuser";
            String BILL_GET_ALL_LIST_ADMIN = "/admin/bills/getalllist";
            String BILL_GET_ALL_LIST_AND_FILTER_USER = "/user/bills/getalllistandfilter";
            String BILL_GET_ALL_LIST_AND_FILTER_USER_OTHER = "/user/bills/getalllistandfilteruser";
            String BILL_GET_ALL_LIST_AND_FILTER_ADMIN = "/admin/bills/getalllistandfilter";
            String BILL_GET_BILL_DETAIL_USER = "/user/bills/getbilldetail";
            String BILL_GET_BILL_DETAIL_ADMIN = "/admin/bills/getbilldetail";

            String BILL_GET_ALL_LIST_COMBO_BILL_ADMIN = "/admin/bills/getalllistbillcombo";
            String BILL_GET_ALL_LIST_PRODUCT_BILL_ADMIN = "/admin/bills/getalllistbillproduct";

        }

        interface Address{
            String ADDRESS_FIND_ALL_PROVINCE = "/getallprovince";
            String ADDRESS_FIND_ALL_DISTRICT = "/getalldistrict";
            String ADDRESS_FIND_ALL_COMMUNE = "/getallcommune";
        }

        interface Like{
            String LIKE_SAVE = "/user/likeordislike";
        }

        interface Combo {
            String COMBO_FIND_ALL = "/admin/combos/getall";
            String COMBO_SEARCH = "/admin/combos/search";
            String COMBO_FIND_BY_ID = "/admin/combos/findbyid/{idCombo}";
            String COMBO_SAVE = "/admin/combos/save";
            String COMBO_DELETE = "/admin/combos/delete";
            String COMBO_FIND_ALL_USER = "/user/combos/getall";
            String COMBO_FIND_BY_ID_USER = "/user/combos/findbyid/{idCombo}";
            String COMBO_SEARCH_USER = "/user/combos/search";

        }

        interface ComboDetail {
            String COMBO_DETAIL_FIND_ALL = "/admin/combo-details/getall";
            String COMBO_DETAIL_FIND_BY_ID_COMBO = "/admin/combo-details/find-by-id-combo";
            String COMBO_DETAIL_FIND_BY_ID = "/admin/combo-details/findbyid/{idComboDetail}";

        }

        interface News {
            String NEWS_FIND_ALL = "/admin/news/getall";
            String NEWS_FIND_BY_ID = "/admin/news/findbyid/{idNews}";
            String NEWS_SAVE = "/admin/news/save";
            String NEWS_DELETE = "/admin/news/delete";
            String NEWS_SEARCH = "/admin/news/search";
            String NEWS_FIND_ALL_USER = "/user/news/getall";
            String NEWS_FIND_BY_ID_USER = "/user/news/findbyid/{idNews}";
            String NEWS_SEARCH_USER = "/user/news/search";
        }

        interface ProductSale{
            String PRODUCT_SALE_ADD = "/admin/productsale/addsaletoproduct";
            String PRODUCT_SALE_CANCEL = "/admin/productsale/cancelsaletoproduct";
            String PRODUCT_SALE_GET_LIST = "/admin/productsale/getlistsaleproduct";
            String PRODUCT_SALE_GET_LIST_USER = "/user/productsale/getlistsaleproduct";
        }

        interface VoucherUser{
            String VOUCHER_USER_GET_LIST = "/user/voucheruser/getlist";
            String VOUCHER_USER_GET_DONATE = "/user/voucheruser/getdonate";
        }

        interface Statistical{
            String STATISTICAL_GET_ALL = "/admin/statistical/getall";
        }
    }
}
