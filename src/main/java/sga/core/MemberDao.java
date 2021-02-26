package sga.core;

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
 *  para o record Member e faz conexão com o MongoDB.
 **/
public class MemberDao implements Dao<Member> {
    private final MongoCollection<Document> members;

    public MemberDao(MongoCollection<Document> collection) {
        this.members = collection;
        System.out.println(members.toString());
    }

    @Override
    public Optional<Member> read(String id) {
        Member member = null;
        Document doc = members.find(eq("id", id)).first();
        if (doc != null) {
            String name = doc.get("name").toString();
            String surname = doc.get("surname").toString();
            member = new Member(id, name, surname);
        }

        return Optional.ofNullable(member);
    }

    @Override
    public List<Member> readAll() {
        MongoCursor<Document> cursor = members.find().iterator();
        ArrayList<Member> all = new ArrayList<>();

        try {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                String id = doc.get("id").toString();
                String name = doc.get("name").toString();
                String surname = doc.get("surname").toString();
                all.add(new Member(id, name, surname));
            }
        } finally {
            cursor.close();
        }

        return all;
    }

    @Override
    public void create(Member member) {
        Document doc = Document.parse(member.toJson());
        members.insertOne(doc);
    }

    @Override
    public void update(Member member) {
        Bson filter = eq("id", member.id());
        Bson updateName = set("name", member.name());
        Bson updateSurname = set("surname", member.surname());
        Bson updateOperation = combine(updateName, updateSurname);
        UpdateResult result = members.updateOne(filter, updateOperation);
    }

    @Override
    public void delete(Member member) {
        DeleteResult result = members.deleteOne(eq("id", member.id()));
    }

    /**
     * Esse metódo não é seguro e normalmente não existiria na aplicação final.
     * Utilizamos ele aqui para facilitar o teste da aplicação.
     **/
    public void deleteAll() {
        Bson filter = new Document();
        members.deleteMany(filter);
    }

    public long count() {
        return members.countDocuments();
    }
}
