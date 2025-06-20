package knowledge.hood.door2mart.RoomDataBAse;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CartdbDao {
    @Insert
    void  insertcart(Cartdb cartdb);

    @Query("SELECT * FROM cartdb WHERE id =:id")
    List<Cartdb> getSelected(int id);

    @Update
    public void update(Cartdb cartdb);

    @Delete
    public  void delete(Cartdb cartdb);


    @Query("SELECT * FROM cartdb")
    List<Cartdb> getAll();
}