package knowledge.hood.door2mart.RoomDataBAse;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Cartdb {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "item_qn")
    public String item_qn;

    public Cartdb(int id, String item_qn) {
        this.id = id;
        this.item_qn = item_qn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItem_qn() {
        return item_qn;
    }

    public void setItem_qn(String item_qn) {
        this.item_qn = item_qn;
    }
}
