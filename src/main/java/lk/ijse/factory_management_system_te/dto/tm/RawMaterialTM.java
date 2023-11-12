package lk.ijse.factory_management_system_te.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class RawMaterialTM {
    private String raw_id;
    private String raw_name;
    private Double raw_price;
    private String raw_qty;
    private Button preview;
    private Button remove;
}
