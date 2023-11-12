package lk.ijse.factory_management_system_te.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

public class ItemTM {
    private String item_id;
    private String item_name;
    private String item_type;
    private String qty;
    private Double unit_price;
    private Button preview;
    private Button remove;
}
