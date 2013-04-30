/**
 * NOT IN USE !!
 */
package com.gourmet6;

import android.provider.BaseColumns;

/**
 * @author Lena
 *
 */
public class DataBaseContract {
	
	// Prevents the DataBesContract class from being instantiated.
    private DataBaseContract() {}
    
    private static final String CREATE_TABLE = "CREATE TABLE ";
    
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String REAL_TYPE = " REAL";
    
    private static final String NOT_NULL = " NOT NULL";
    private static final String UNIQUE = " UNIQUE";
    private static final String PRIMARY_KEY = " PRIMARY KEY";
    private static final String FOREIGN_KEY = " FOREIGN KEY";
    
    private static final String REFERENCES = " REFERENCES ";
    private static final String ON_UPDATE_CASCADE = " ON UPDATE CASCADE";
    private static final String ON_DELETE_CASCADE = " ON DELETE CASCADE";
    private static final String ON_DELETE_SET_NULL = " ON DELETE SET NULL";
    
    private static final String CHECK = " CHECK";
    private static final String IN = " IN";
    
	
    /* Table structure : name and columns */
    
    /* Allergen database table */
	public static abstract class AllergenDB implements BaseColumns {
		public static final String TABLE_NAME = "allergen";
		public static final String COLUMN_NAME_dishName = "dishName";
	    public static final String COLUMN_NAME_resName = "resName";
	    public static final String COLUMN_NAME_alleName = "alleName";
	    
	    public static final String ALLERGEN_COMPLETE_LIST = "'oeuf','arachide','poisson','lait','drupacées'," +
	    		"'apiacées','noix de coco','crustacés','blé','boeuf','mangue','noisette','banane','avocat'," +
	    		"'soja','lentilles','pois','moutarde','kiwi','moules','pommes de terre','tournesol'";
	    
	    public static final String SQL_CREATE_ENTRIES = CREATE_TABLE + TABLE_NAME + " (" +
	    _ID + INTEGER_TYPE + ", " +
	    COLUMN_NAME_dishName + TEXT_TYPE + NOT_NULL + ", " +
	    COLUMN_NAME_resName + TEXT_TYPE + NOT_NULL + ", " +
	    COLUMN_NAME_alleName + TEXT_TYPE + NOT_NULL + CHECK + " (" + COLUMN_NAME_alleName + IN + " ("+ ALLERGEN_COMPLETE_LIST + ")), " +
	    PRIMARY_KEY + " (" + COLUMN_NAME_dishName + "," + COLUMN_NAME_resName + "," + COLUMN_NAME_alleName + ")," +
	    FOREIGN_KEY + " (" + COLUMN_NAME_dishName + "," + COLUMN_NAME_resName + ")" + REFERENCES + DishDB.TABLE_NAME + ON_UPDATE_CASCADE + ON_DELETE_CASCADE + ")";
	}
	
	/* Client database table */
	public static abstract class ClientDB implements BaseColumns {
		public static final String TABLE_NAME = "client";
		public static final String COLUMN_NAME_mail = "mail";
	    public static final String COLUMN_NAME_cliName = "cliName";
	    public static final String COLUMN_NAME_tel = "tel";
	    
	    public static final String SQL_CREATE_ENTRIES = CREATE_TABLE + TABLE_NAME + " (" +
	    _ID + INTEGER_TYPE + ", " +
	    COLUMN_NAME_mail + TEXT_TYPE + NOT_NULL + PRIMARY_KEY + ", " +
	    COLUMN_NAME_cliName + TEXT_TYPE + NOT_NULL + ", " +
	    COLUMN_NAME_tel + TEXT_TYPE + ")";    
	}
	
	/* Cuisine database table */
	public static abstract class CuisineDB implements BaseColumns {
		public static final String TABLE_NAME = "cuisine";
		public static final String COLUMN_NAME_resName = "resName";
	    public static final String COLUMN_NAME_type = "type";
	    
	    public static final String SQL_CREATE_ENTRIES = CREATE_TABLE + TABLE_NAME + " (" +
	    _ID + INTEGER_TYPE + ", " +
	    COLUMN_NAME_resName + TEXT_TYPE + NOT_NULL + REFERENCES + RestaurantDB.TABLE_NAME + ON_UPDATE_CASCADE + ON_DELETE_CASCADE + ", " +
	    COLUMN_NAME_type + TEXT_TYPE + NOT_NULL + ", " +
	    PRIMARY_KEY + " (" + COLUMN_NAME_resName + "," + COLUMN_NAME_type + ")" + ")";    		
	}
	
	/* Dish database table */
	public static abstract class DishDB implements BaseColumns {
		public static final String TABLE_NAME = "dish";
		public static final String COLUMN_NAME_dishName = "dishName";
	    public static final String COLUMN_NAME_resName = "resName";
	    public static final String COLUMN_NAME_type = "type";
	    public static final String COLUMN_NAME_description = "description";
	    public static final String COLUMN_NAME_stock = "stock";
	    public static final String COLUMN_NAME_price = "price";	
	    
	    public static final String DISH_TYPE_COMPLETE_LIST = "'entrée','plat','dessert','boisson'";
	    
	    public static final String SQL_CREATE_ENTRIES = CREATE_TABLE + TABLE_NAME + " (" +
	    _ID + INTEGER_TYPE + ", " +
	    COLUMN_NAME_dishName + TEXT_TYPE + NOT_NULL + ", " +
	    COLUMN_NAME_resName + TEXT_TYPE + NOT_NULL + REFERENCES + RestaurantDB.TABLE_NAME + ON_UPDATE_CASCADE + ON_DELETE_CASCADE + ", " +
	    COLUMN_NAME_type + TEXT_TYPE + NOT_NULL + CHECK + " (" + COLUMN_NAME_type + IN + " (" + DISH_TYPE_COMPLETE_LIST + ")), " +
	    COLUMN_NAME_description + TEXT_TYPE + ", " +
	    COLUMN_NAME_stock + INTEGER_TYPE + ", " +
	    COLUMN_NAME_price + REAL_TYPE + NOT_NULL + ", " +
	    PRIMARY_KEY + " (" + COLUMN_NAME_dishName + "," + COLUMN_NAME_resName + ")" + ")";
	}
	
	/* Order database table */
	public static abstract class OrderDB implements BaseColumns {
		public static final String TABLE_NAME = "order";
		public static final String COLUMN_NAME_orderNr = "orderNr";
	    public static final String COLUMN_NAME_resName = "resName";
	    public static final String COLUMN_NAME_mail = "mail";
	    
	    public static final String SQL_CREATE_ENTRIES = CREATE_TABLE + TABLE_NAME + " (" +
	    _ID + INTEGER_TYPE + ", " +
	    COLUMN_NAME_orderNr + TEXT_TYPE + NOT_NULL + ", " +
	    COLUMN_NAME_resName + TEXT_TYPE + NOT_NULL + REFERENCES + RestaurantDB.TABLE_NAME + ON_UPDATE_CASCADE + ON_DELETE_CASCADE + ", " +
	    COLUMN_NAME_mail + TEXT_TYPE + NOT_NULL + REFERENCES + ClientDB.TABLE_NAME + ON_UPDATE_CASCADE + ON_DELETE_CASCADE + ", " +
	    PRIMARY_KEY + " (" + COLUMN_NAME_orderNr + "," + COLUMN_NAME_resName + ")" + ")";
	}
	
	/* Order detail database table */
	public static abstract class OrderDetailDB implements BaseColumns {
		public static final String TABLE_NAME = "order_detail";
		public static final String COLUMN_NAME_dishName = "dishName";
		public static final String COLUMN_NAME_resName = "resName";
		public static final String COLUMN_NAME_orderNr = "orderNr";
		public static final String COLUMN_NAME_quantity = "quantity";
		
	    public static final String SQL_CREATE_ENTRIES = CREATE_TABLE + TABLE_NAME + " (" +
	    _ID + INTEGER_TYPE + ", " +
	    COLUMN_NAME_dishName + TEXT_TYPE + NOT_NULL + ", " +
	    COLUMN_NAME_resName + TEXT_TYPE + NOT_NULL + ", " +
	    COLUMN_NAME_orderNr + TEXT_TYPE + NOT_NULL + ", " +
	    COLUMN_NAME_quantity + INTEGER_TYPE + NOT_NULL + ", " +
	    PRIMARY_KEY + " (" + COLUMN_NAME_dishName + "," + COLUMN_NAME_resName + "," + COLUMN_NAME_orderNr + ")" +
	    FOREIGN_KEY + " (" + COLUMN_NAME_dishName + "," + COLUMN_NAME_resName + ")" + REFERENCES + DishDB.TABLE_NAME + ON_UPDATE_CASCADE + ON_DELETE_CASCADE + ", " +
	    FOREIGN_KEY + " (" + COLUMN_NAME_orderNr + ")" + REFERENCES + OrderDB.TABLE_NAME + "(" + OrderDB.COLUMN_NAME_orderNr + ")" + ON_UPDATE_CASCADE + ON_DELETE_CASCADE + ")";
	}
	
	/* Picture database table */
	public static abstract class PictureDB implements BaseColumns {
		public static final String TABLE_NAME = "picture";
	    public static final String COLUMN_NAME_picName = "picName";
	    public static final String COLUMN_NAME_folder = "folder";
	    public static final String COLUMN_NAME_bitmap = "bitmap";
	    public static final String COLUMN_NAME_resName = "resName";
	    public static final String COLUMN_NAME_dishName = "dishName";
	    
	    public static final String SQL_CREATE_ENTRIES = CREATE_TABLE + TABLE_NAME + " (" +
	    _ID + INTEGER_TYPE + ", " +
	    COLUMN_NAME_picName + TEXT_TYPE + NOT_NULL + ", " +
	    COLUMN_NAME_folder + TEXT_TYPE + NOT_NULL + ", " +
	    COLUMN_NAME_bitmap + TEXT_TYPE + NOT_NULL + ", " +
        COLUMN_NAME_resName + TEXT_TYPE + NOT_NULL + REFERENCES + RestaurantDB.TABLE_NAME + ON_UPDATE_CASCADE + ON_DELETE_CASCADE + ", " +
        COLUMN_NAME_dishName + TEXT_TYPE + REFERENCES + DishDB.TABLE_NAME + "(" + DishDB.COLUMN_NAME_dishName + ")" + ON_UPDATE_CASCADE + ON_DELETE_CASCADE + ", " +
        PRIMARY_KEY + " (" + COLUMN_NAME_picName + "," + COLUMN_NAME_folder + ")" + ")";
	}
	
	/* Reservation database table */
	public static abstract class ReservationDB implements BaseColumns {
		public static final String TABLE_NAME = "reservation";
		public static final String COLUMN_NAME_reservNr = "reservNr";
	    public static final String COLUMN_NAME_resName = "resName";
	    public static final String COLUMN_NAME_orderNr = "orderNr";
	    public static final String COLUMN_NAME_date = "date";
	    public static final String COLUMN_NAME_time = "time";
	    public static final String COLUMN_NAME_seats = "seats";
	    public static final String COLUMN_NAME_mail = "mail";
	    
	    public static final String SQL_CREATE_ENTRIES = CREATE_TABLE + TABLE_NAME + " (" +
	    _ID + INTEGER_TYPE + ", " +
	    COLUMN_NAME_reservNr + TEXT_TYPE + NOT_NULL + ", " +
	    COLUMN_NAME_resName + TEXT_TYPE + NOT_NULL + REFERENCES + RestaurantDB.TABLE_NAME + ON_UPDATE_CASCADE + ON_DELETE_CASCADE + ", " +
	    COLUMN_NAME_orderNr + TEXT_TYPE + REFERENCES + OrderDB.TABLE_NAME + "(" + OrderDB.COLUMN_NAME_orderNr + ")" + ON_UPDATE_CASCADE + ON_DELETE_SET_NULL + ", " +
	    COLUMN_NAME_date + TEXT_TYPE + NOT_NULL + ", " +
	    COLUMN_NAME_time + TEXT_TYPE + NOT_NULL + ", " +
	    COLUMN_NAME_seats + INTEGER_TYPE + NOT_NULL + ", " +
	    COLUMN_NAME_mail + TEXT_TYPE + NOT_NULL + REFERENCES + ClientDB.TABLE_NAME + ON_UPDATE_CASCADE + ON_DELETE_CASCADE + ", " +
	    PRIMARY_KEY + " (" + COLUMN_NAME_reservNr + "," + COLUMN_NAME_resName + ")" + ")";
	}
	
	/* Restaurant database table */
	public static abstract class RestaurantDB implements BaseColumns {
		public static final String TABLE_NAME = "restaurant";
	    public static final String COLUMN_NAME_resName = "resName";
	    public static final String COLUMN_NAME_chainName = "chainName";
	    public static final String COLUMN_NAME_description = "description";
	    public static final String COLUMN_NAME_lat = "lat";
	    public static final String COLUMN_NAME_long = "long";
	    public static final String COLUMN_NAME_street = "street";
	    public static final String COLUMN_NAME_zip = "zip";
	    public static final String COLUMN_NAME_town = "town";
	    public static final String COLUMN_NAME_tel = "tel";
	    public static final String COLUMN_NAME_rating = "rating";
	    public static final String COLUMN_NAME_priceCat = "priceCat";
	    public static final String COLUMN_NAME_avail = "avail";
	    
	    public static final String RES_RATING_COMPLETE_LIST = "1,2,3,4,5";
	    public static final String RES_PRICE_CAT_COMPLETE_LIST = "1,2,3";
	    
	    public static final String SQL_CREATE_ENTRIES = CREATE_TABLE + TABLE_NAME + " (" +
	    _ID + INTEGER_TYPE + ", " +
	    COLUMN_NAME_resName + TEXT_TYPE + NOT_NULL + PRIMARY_KEY + ", " +
	    COLUMN_NAME_chainName + TEXT_TYPE + ", " +
	    COLUMN_NAME_description + TEXT_TYPE + NOT_NULL + ", " +
	    COLUMN_NAME_lat + REAL_TYPE + NOT_NULL + ", " + 
	    COLUMN_NAME_long + REAL_TYPE + NOT_NULL + ", " + 
	    COLUMN_NAME_street + TEXT_TYPE + NOT_NULL + ", " + 
	    COLUMN_NAME_zip + INTEGER_TYPE + NOT_NULL + ", " + 
	    COLUMN_NAME_town + TEXT_TYPE + NOT_NULL + ", " + 
	    COLUMN_NAME_tel + TEXT_TYPE + UNIQUE + ", " + 
	    COLUMN_NAME_rating + INTEGER_TYPE + CHECK + " (" + COLUMN_NAME_rating + IN + " (" + RES_RATING_COMPLETE_LIST + ")" + ")" + ", " + 
	    COLUMN_NAME_priceCat + INTEGER_TYPE + CHECK + " (" + COLUMN_NAME_priceCat + IN + " (" + RES_PRICE_CAT_COMPLETE_LIST + ")" + ")" + ", " +
	    COLUMN_NAME_avail + INTEGER_TYPE + ")";
	}
}
