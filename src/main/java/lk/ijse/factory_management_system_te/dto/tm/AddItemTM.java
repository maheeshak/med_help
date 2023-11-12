package lk.ijse.factory_management_system_te.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class AddItemTM {
    private  String raw_id;
    private String desc;
    private Integer qty;
    private Button remove;

    public AddItemTM(String rawId, String raw_desc, Integer qty) {
        this.setRaw_id(rawId);
        this.setDesc(raw_desc);
        this.setQty(qty);
    }
}
