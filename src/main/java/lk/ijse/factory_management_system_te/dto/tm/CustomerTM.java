package lk.ijse.factory_management_system_te.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CustomerTM {
    String cus_id;
    String name;
    String address;
    String contact;
    String email;
    Button preview;
    Button remove;
}
