package lk.ijse.factory_management_system_te.dto.tm;

import javafx.scene.control.Button;
import lombok.*;



@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

public class OrderTm {
    private String order_id;
    private String cus_id;
    private String cus_name;
    private String date;
    private String status;
    private Button preview;
    private Button remove;

}
