package com.mikolajczyk.frontend.redudo.source.service;

public enum ListType {
    TO_READ,
    DURING,
    DONE;

    public String getStringType(ListType listType) {
        String type;
        if (listType.equals(TO_READ))
            type = "toRead";
        else
            type = listType.toString().toLowerCase();
        return type;
    }
}
