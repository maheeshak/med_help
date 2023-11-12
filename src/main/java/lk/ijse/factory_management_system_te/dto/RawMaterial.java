package lk.ijse.factory_management_system_te.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class RawMaterial {
    String raw_id;
    String raw_desc;
    Double unit_price;
    String qty;
}
