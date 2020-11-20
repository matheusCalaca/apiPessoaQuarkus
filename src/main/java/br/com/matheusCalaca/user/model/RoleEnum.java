package br.com.matheusCalaca.user.model;

public enum RoleEnum {
    ADMIN("ADMIN"),
    USER("USER");


    private String name;

    RoleEnum(String name) {

        this.name = name;
    }

    RoleEnum() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
