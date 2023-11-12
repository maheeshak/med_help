package lk.ijse.factory_management_system_te.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

public class SupplierTM {
    private String sup_id;
    private String sup_name;
    private String sup_address;
    private String contact;
    private String email;
    private Button preview;
    private Button remove;
}
