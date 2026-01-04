package models;
import lombok.Data;

@Data
public class DeleteBookRequestModel {
    String isbn, userId;
}
