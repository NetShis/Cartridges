package ru.komiufps.cartridges.utils;

public enum StateCartridge {
    EmptyInStock("Пустой на складе"),
    FullInStock("Заправленный на складе"),
    DefectiveInStock("Бракованный на складе"),
    IssueToConsumer("Выдан пользователю"),
    RefillCartridge("На заправке"),
    Liquidate("Списан"),
    NotDefine("Не определенно");

    private final String descriptionInRussian;

    StateCartridge(String descriptionInRussian) {
        this.descriptionInRussian = descriptionInRussian;
    }

    public String getDescriptionInRussian() {
        return descriptionInRussian;
    }
}
