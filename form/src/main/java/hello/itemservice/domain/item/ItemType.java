package hello.itemservice.domain.item;

public enum ItemType {

    BOOK("도서"), FOOD("음식"), ETC("기타");

    private final String description;

    ItemType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    //type.description은 프로퍼티 접근법이기 떄문에 게터로 설정해줘야 함.
}
