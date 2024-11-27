package br.com.alura.screenmatch.model;

public enum Category {
    ACTION("Action"),
    ROMANCE("Romance"),
    DRAMA("Drama"),
    CRIME("Crime"),
    ADVENTURE("Adventure"),
    HISTORY("History"),
    THRILLER("Thriller"),
    COMEDY("Comedy"),
    HORROR("Horror"),
    MYSTERY("Mystery"),
    FAMILY("Family"),
    ANIMATION("Animation"),
    SHORT("Short"),
    FANTASY("Fantasy"),
    SPORT("Sport"),
    WAR("War");

    private String omdbCategory;

    Category(String omdbCategory) {
        this.omdbCategory = omdbCategory;
    }

    public static Category fromString(String text) {
        for (Category category : Category.values()) {
            if(category.omdbCategory.equalsIgnoreCase(text))
                return category;
        }

        throw new IllegalArgumentException("No categories found series.");
    }
}
