package wetal.bibliotheque.object_holders;

import wetal.bibliotheque.models.Member;

public final class MemberHolder {

    private Member member;
    private final static MemberHolder INSTANCE = new MemberHolder();

    MemberHolder() {}

    public static MemberHolder getInstance() {
        return INSTANCE;
    }

    public void setMember(Member m) {
        this.member = m;
    }

    public Member getMember() {
        return this.member;
    }
}