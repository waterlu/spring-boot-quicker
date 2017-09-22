package cn.lu.boot.quicker.entity;

/**
 * Created by lutiehua on 2017/9/22.
 */
public class DBField {

    /**
     * 列名
     */
    private String columnName;

    /**
     * 类型
     */
    private String typeName;

    /**
     * 长度
     */
    private int columnSize;

    /**
     * 小数点
     */
    private int decimalDigits;

    public String getIsNullable() {
        return isNullable;
    }

    public void setIsNullable(String isNullable) {
        this.isNullable = isNullable;
    }

    /**
     * 是否可空
     */
    private String isNullable;

    public String getIsPrimaryKey() {
        return isPrimaryKey;
    }

    public void setIsPrimaryKey(String isPrimaryKey) {
        this.isPrimaryKey = isPrimaryKey;
    }

    /**
     * 是否主键
     */
//    private boolean isPrimaryKey = false;
    private String isPrimaryKey;

    /**
     * 注释
     */
    private String remarks;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(int columnSize) {
        this.columnSize = columnSize;
    }

    public int getDecimalDigits() {
        return decimalDigits;
    }

    public void setDecimalDigits(int decimalDigits) {
        this.decimalDigits = decimalDigits;
    }

//    public boolean isNullable() {
//        return isNullable;
//    }
//
//    public void setNullable(boolean nullable) {
//        isNullable = nullable;
//    }

//    public boolean isPrimaryKey() {
//        return isPrimaryKey;
//    }
//
//    public void setPrimaryKey(boolean primaryKey) {
//        isPrimaryKey = primaryKey;
//    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
