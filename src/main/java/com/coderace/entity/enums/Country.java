package com.coderace.entity.enums;

public enum Country {
    ARG("arg"),
    BRA("bra"),
    USA("usa"),
    UNDEFINED("undefined");

    private final String code;

    Country(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static Country fromCode(String code) {
        Country result = null;
        int index = 0;

        while (result == null && index < Country.values().length) {
            final Country candidate = Country.values()[index];

            if (candidate.code.equalsIgnoreCase(code)) {
                result = candidate;
            }

            index ++;
        }

        return result != null ? result : UNDEFINED;
    }
}
