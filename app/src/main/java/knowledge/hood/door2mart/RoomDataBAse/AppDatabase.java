package knowledge.hood.door2mart.RoomDataBAse;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Cartdb.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CartdbDao cartdbDao();
}

