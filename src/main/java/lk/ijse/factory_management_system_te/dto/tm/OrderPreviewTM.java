package lk.ijse.factory_management_system_te.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class OrderPreviewTM {

    private String item_id;
    private String desc;
    private String qty;
    private Double unit_price;
    private Double total;


}
