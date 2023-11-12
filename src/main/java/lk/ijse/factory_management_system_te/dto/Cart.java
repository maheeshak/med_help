package lk.ijse.factory_management_system_te.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Cart {

    String code;
    Integer qty;
    Double unitPrice;
}
