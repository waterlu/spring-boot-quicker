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
     * 类型（INT值）
     */
    private int dataType;

    /**
     * 长度
     */
    private int columnSize;

    /**
     * 小数点
     */
    private int decimalDigits;

    /**
     * 是否可空
     */
    private boolean isNullable = true;

    /**
     * 是否主键
     */
    private boolean isPrimaryKey = false;

    /**
     * 注释
     */
    private String remarks;

    /**
     * 默认值
     */
    private String columnDef;

    /**
     * 字符最大字节数
     */
    private int charOctetLength;

    private String nullableShow;

    public String getNullableShow() {
        return nullableShow;
    }

    public void setNullableShow(String nullableShow) {
        this.nullableShow = nullableShow;
    }

    public String getPrimaryKeyShow() {
        return primaryKeyShow;
    }

    public void setPrimaryKeyShow(String primaryKeyShow) {
        this.primaryKeyShow = primaryKeyShow;
    }

    private String primaryKeyShow;

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

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
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

    public boolean isNullable() {
        return isNullable;
    }

    public void setNullable(boolean nullable) {
        isNullable = nullable;
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        isPrimaryKey = primaryKey;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getColumnDef() {
        return columnDef;
    }

    public void setColumnDef(String columnDef) {
        this.columnDef = columnDef;
    }

    public int getCharOctetLength() {
        return charOctetLength;
    }

    public void setCharOctetLength(int charOctetLength) {
        this.charOctetLength = charOctetLength;
    }
}
