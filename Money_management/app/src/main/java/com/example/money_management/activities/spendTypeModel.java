package com.example.money_management.activities;

public class spendTypeModel {
    public String TypeName;
    public String Icon;

    public spendTypeModel(String typeName, String icon) {
        this.TypeName = typeName;
        this.Icon = icon;
    }

    public void setTypeName(String typeName) {
        this.TypeName = typeName;
    }

    public void setIcon(String icon) {
        this.Icon = icon;
    }



    public String getTypeName() {
        return this.TypeName;
    }

    public String getIcon() {
        return this.Icon;
    }
}
