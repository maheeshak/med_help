package lk.ijse.factory_management_system_te.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class EmployeeTM {
    private String emp_id;
    private String emp_name;
    private String emp_address;
    private String contact;
    private String designation;
    private Button preview;
    private Button remove;
}
