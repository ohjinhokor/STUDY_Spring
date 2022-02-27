package upload_file.demo.repository;

import org.springframework.stereotype.Repository;
import upload_file.demo.dtos.Item;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ItemRepository {
    private final Map<Long, Item> store = new HashMap<>();
    private long sequence =0L;

    public Item save(Item item){
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id){
        return store.get(id);
    }
}
