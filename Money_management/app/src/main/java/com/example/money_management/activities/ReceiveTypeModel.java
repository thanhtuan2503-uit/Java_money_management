package com.example.money_management.activities;

public class ReceiveTypeModel {
    public String TypeName;
    public String Icon;

    public ReceiveTypeModel(String typeName, String icon) {
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
