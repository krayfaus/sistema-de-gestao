package sga.core;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

/**
 * Esse classe representa um DAO (Objeto de acesso a dados)
 *  para o record Room e faz conexão com o MongoDB.
 **/
public class RoomDao implements Dao<Room> {
    final Gson gson = new Gson();
    private final MongoCollection<Document> rooms;

    public RoomDao(MongoCollection<Document> collection) {
        this.rooms = collection;
    }

    @Override
    public Optional<Room> read(String id) {
        Room room = null;
        Document doc = rooms.find(eq("id", id)).first();
        if (doc != null) {
            String name = doc.get("name").toString();
            Integer capacity = doc.getInteger("capacity");
            List<List<String>> member_ids = gson.fromJson(
                    doc.get("member_ids").toString(),
                    new TypeToken<List<List<String>>>(){}.getType());
            room = new Room(id, name, capacity, member_ids);
        }

        return Optional.ofNullable(room);
    }

    @Override
    public List<Room> readAll() {
        MongoCursor<Document> cursor = rooms.find().iterator();
        ArrayList<Room> all = new ArrayList<>();

        try {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                String id = doc.get("id").toString();
                String name = doc.get("name").toString();
                Integer capacity = doc.getInteger("capacity");
                List<List<String>> member_ids = gson.fromJson(
                        doc.get("member_ids").toString(),
                        new TypeToken<List<List<String>>>(){}.getType());
                all.add(new Room(id, name, capacity, member_ids));
            }
        } finally {
            cursor.close();
        }

        return all;
    }

    @Override
    public void create(Room room) {
        // TODO: Verificar se já existe uma sala com mesmo nome.
        Document doc = Document.parse(room.toJson());
        rooms.insertOne(doc);
    }

    @Override
    public void update(Room room) {
        Bson filter = eq("id", room.id());
        Bson updateName = set("name", room.name());
        Bson updateCapacity = set("capacity", room.capacity());
        Bson updateMemberIds = set("member_ids", room.member_ids());
        Bson updateOperation = combine(updateName, updateCapacity, updateMemberIds);
        UpdateResult result = rooms.updateOne(filter, updateOperation);
    }

    @Override
    public void delete(Room room) {
        DeleteResult result = rooms.deleteOne(eq("id", room.id()));
    }

    /**
     * Esse metódo não é seguro e normalmente não existiria na aplicação final.
     * Utilizamos ele aqui para facilitar o teste da aplicação.
     **/
    public void deleteAll() {
        Bson filter = new Document();
        rooms.deleteMany(filter);
    }

    public long count() {
        return rooms.countDocuments();
    }
}
