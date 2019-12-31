package global.ututaxfree.taxfreeandroidui;

public class SelectedFriend {

    public SelectedFriend(String name, int position) {
        this.name = name;
        this.position = position;
    }

    private String name;
    private int position;

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }
}
