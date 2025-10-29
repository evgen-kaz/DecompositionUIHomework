package models;
import lombok.Data;
import java.util.List;

@Data
public class AddBookRequestModel {
    List<AddIsbnRequestModel> collectionOfIsbns;
    String userId;
}
